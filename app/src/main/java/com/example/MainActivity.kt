package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.configureAppCheck
import com.example.data.model.CoinCatalogItem
import com.example.data.model.MarketplaceListing
import com.example.data.model.Specimen
import com.example.data.repository.CoinCatalogRepository
import com.example.data.repository.NumismaRepository
import com.example.ui.navigation.Screen
import com.example.ui.screens.AiChatScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.CoinDetailScreen
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.DealerDirectoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MarketplaceScreen
import com.example.ui.screens.ScanAiScreen
import com.example.ui.screens.ValuationMarketScreen
import com.example.ui.screens.VaultScreen
import com.example.ui.theme.CardSurface
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.NumismaTheme
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repository: NumismaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // App Check must be initialized immediately as required by system architecture
        configureAppCheck(this, intent)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth
        repository = NumismaRepository(this)

        setContent {
            NumismaTheme {
                var currentUser by remember { mutableStateOf<FirebaseUser?>(auth.currentUser) }

                DisposableEffect(auth) {
                    val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                        currentUser = firebaseAuth.currentUser
                    }
                    auth.addAuthStateListener(listener)
                    onDispose {
                        auth.removeAuthStateListener(listener)
                    }
                }

                if (currentUser == null) {
                    AuthScreen(
                        onAuthSuccess = {
                            currentUser = auth.currentUser
                        }
                    )
                } else {
                    NumismaMainPortal(
                        user = currentUser!!,
                        repository = repository,
                        onSignOut = {
                            auth.signOut()
                            currentUser = null
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumismaMainPortal(
    user: FirebaseUser,
    repository: NumismaRepository,
    onSignOut: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedCoinId by remember { mutableStateOf<String?>(null) }

    // Intercept back presses to return to HOME when in sub-screens
    BackHandler(enabled = currentScreen != Screen.HOME) {
        if (currentScreen == Screen.COIN_DETAIL) {
            currentScreen = Screen.CATALOG
        } else {
            currentScreen = Screen.HOME
        }
    }

    // Vault specimens stream
    val specimens by repository.observeSpecimens().collectAsState(initial = emptyList())
    // Marketplace listings stream
    val listings by repository.observeListings().collectAsState(initial = emptyList())

    val defaultSampleSpecimens = remember {
        listOf(
            Specimen(
                id = "specimen-demo-1",
                coinName = "Athens AR Tetradrachm (Full Crest)",
                catalogId = "athenian-owl-tetradrachm",
                issuingAuthority = "Athenian Democratic Polis",
                era = "Ancient Greece",
                year = "c. 454–404 BC",
                denomination = "Tetradrachm",
                metal = "Silver (.980 AR)",
                gradingService = "NGC Ancients",
                grade = "Ch XF",
                certificationNumber = "4938210-004",
                acquisitionCost = 2750.0,
                currentEstimatedValue = 3400.0,
                provenance = "Ex-Gorny & Mosch 2018; Numisma Vault Certified."
            ),
            Specimen(
                id = "specimen-demo-2",
                coinName = "Greco-Bactrian Eukratides I Tetradrachm",
                catalogId = "eukratides-megas-tetradrachm",
                issuingAuthority = "King Eukratides I The Great",
                era = "Greco-Bactrian Kingdom",
                year = "c. 171–145 BC",
                denomination = "Tetradrachm",
                metal = "Silver",
                gradingService = "NGC Ancients",
                grade = "Choice VF",
                certificationNumber = "6190284-012",
                acquisitionCost = 3900.0,
                currentEstimatedValue = 4600.0,
                provenance = "Acquired at CNG Triton XXV (Lot 314)."
            )
        )
    }

    val activeSpecimens = if (specimens.isNotEmpty()) specimens else defaultSampleSpecimens

    val defaultSampleListings = remember {
        listOf(
            MarketplaceListing(
                id = "list-sample-1",
                coinTitle = "Alexander III The Great Lifetime Tetradrachm",
                description = "Price 3561. Lifetime issue with Babylon mint symbols.",
                country = "Macedon",
                era = "Ancient Greece (Macedon)",
                metal = "Silver",
                grade = "NGC Ch AU 5/5, 4/5",
                price = 3600.0,
                currentBid = 3100.0,
                bidCount = 7,
                listingType = "AUCTION"
            ),
            MarketplaceListing(
                id = "list-sample-2",
                coinTitle = "Roman Imperial Nero AV Aureus (Salus Reverse)",
                description = "RIC I (2nd ed.) 59. Rome mint. Salus seated left.",
                country = "Roman Empire",
                era = "Roman Empire",
                metal = "Gold",
                grade = "NGC Ch XF 5/5, 3/5",
                price = 8500.0,
                currentBid = 0.0,
                bidCount = 0,
                listingType = "FIXED_PRICE"
            ),
            MarketplaceListing(
                id = "list-sample-3",
                coinTitle = "Gupta Empire Chandragupta II Archer Type Dinar",
                description = "Altekar Class II. King standing left holding bow and arrow.",
                country = "India",
                era = "Gupta Empire",
                metal = "Gold",
                grade = "PCGS MS62",
                price = 4200.0,
                currentBid = 3850.0,
                bidCount = 12,
                listingType = "AUCTION"
            )
        )
    }

    val activeListings = if (listings.isNotEmpty()) listings else defaultSampleListings

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF141310),
                modifier = Modifier.padding(end = 64.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(CardSurface)
                        .padding(20.dp)
                ) {
                    androidx.compose.foundation.layout.Column {
                        Text(
                            text = "NUMISMA WORLD ELITE",
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 2.sp,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = user.displayName ?: user.email ?: "Distinguished Collector",
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            color = GoldLight,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Executive Sovereign Tier",
                            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                    }
                }

                HorizontalDivider(color = Color(0xFF2E2614))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Executive Portal", color = TextPrimary) },
                    selected = currentScreen == Screen.HOME,
                    onClick = {
                        currentScreen = Screen.HOME
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_home"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Coin Encyclopedia & Corpus", color = TextPrimary) },
                    selected = currentScreen == Screen.CATALOG,
                    onClick = {
                        currentScreen = Screen.CATALOG
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_catalog"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Multimodal AI Authenticator", color = TextPrimary) },
                    selected = currentScreen == Screen.SCAN_AI,
                    onClick = {
                        currentScreen = Screen.SCAN_AI
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_scan_ai"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Numisma AI Scholar Chat", color = TextPrimary) },
                    selected = currentScreen == Screen.AI_CHAT,
                    onClick = {
                        currentScreen = Screen.AI_CHAT
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_ai_chat"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Security, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Personal Digital Vault", color = TextPrimary) },
                    selected = currentScreen == Screen.VAULT,
                    onClick = {
                        currentScreen = Screen.VAULT
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_vault"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.TrendingUp, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("4-Tier Price Intelligence", color = TextPrimary) },
                    selected = currentScreen == Screen.VALUATION,
                    onClick = {
                        currentScreen = Screen.VALUATION
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_valuation"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Storefront, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Marketplace & Live Bidding", color = TextPrimary) },
                    selected = currentScreen == Screen.MARKETPLACE,
                    onClick = {
                        currentScreen = Screen.MARKETPLACE
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_marketplace"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.AccountBalance, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Global Dealer Directory", color = TextPrimary) },
                    selected = currentScreen == Screen.DEALERS,
                    onClick = {
                        currentScreen = Screen.DEALERS
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_dealers"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Forum, contentDescription = null, tint = GoldPrimary) },
                    label = { Text("Symposium & Discussions", color = TextPrimary) },
                    selected = currentScreen == Screen.COMMUNITY,
                    onClick = {
                        currentScreen = Screen.COMMUNITY
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_community"),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFF261D09)
                    )
                )

                HorizontalDivider(color = Color(0xFF2E2614), modifier = Modifier.padding(vertical = 8.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = TextMuted) },
                    label = { Text("Sign Out", color = TextMuted) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                            onSignOut()
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("drawer_item_sign_out")
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF141310),
                    contentColor = TextPrimary,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .testTag("main_bottom_nav")
                ) {
                    NavigationBarItem(
                        selected = currentScreen == Screen.HOME,
                        onClick = { currentScreen = Screen.HOME },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(22.dp)) },
                        label = { Text("Home", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1F1604),
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_home")
                    )

                    NavigationBarItem(
                        selected = currentScreen == Screen.CATALOG,
                        onClick = { currentScreen = Screen.CATALOG },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Catalog", modifier = Modifier.size(22.dp)) },
                        label = { Text("Catalog", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1F1604),
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_catalog")
                    )

                    NavigationBarItem(
                        selected = currentScreen == Screen.SCAN_AI,
                        onClick = { currentScreen = Screen.SCAN_AI },
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Scan AI", modifier = Modifier.size(22.dp)) },
                        label = { Text("AI Scan", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1F1604),
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_scan_ai")
                    )

                    NavigationBarItem(
                        selected = currentScreen == Screen.VAULT,
                        onClick = { currentScreen = Screen.VAULT },
                        icon = { Icon(Icons.Default.Security, contentDescription = "Vault", modifier = Modifier.size(22.dp)) },
                        label = { Text("Vault", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1F1604),
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_vault")
                    )

                    NavigationBarItem(
                        selected = currentScreen == Screen.VALUATION,
                        onClick = { currentScreen = Screen.VALUATION },
                        icon = { Icon(Icons.Default.TrendingUp, contentDescription = "Prices", modifier = Modifier.size(22.dp)) },
                        label = { Text("Prices", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1F1604),
                            selectedTextColor = GoldPrimary,
                            indicatorColor = GoldPrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_valuation")
                    )
                }
            },
            containerColor = ObsidianBackground
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    Screen.HOME -> {
                        HomeScreen(
                            userName = user.displayName ?: "Distinguished Collector",
                            onNavigate = { targetScreen ->
                                currentScreen = targetScreen
                            },
                            onSelectCoin = { coinId ->
                                selectedCoinId = coinId
                                currentScreen = Screen.COIN_DETAIL
                            },
                            onSignOut = onSignOut
                        )
                    }

                    Screen.CATALOG -> {
                        CatalogScreen(
                            onSelectCoin = { coinId ->
                                selectedCoinId = coinId
                                currentScreen = Screen.COIN_DETAIL
                            }
                        )
                    }

                    Screen.COIN_DETAIL -> {
                        val coin = CoinCatalogRepository.catalog.firstOrNull { it.id == selectedCoinId }
                            ?: CoinCatalogRepository.catalog.first()
                        CoinDetailScreen(
                            coin = coin,
                            onBack = { currentScreen = Screen.CATALOG },
                            onAddToVault = { coinItem ->
                                coroutineScope.launch {
                                    val newSpecimen = Specimen(
                                        coinName = coinItem.canonicalName,
                                        catalogId = coinItem.id,
                                        issuingAuthority = coinItem.issuingAuthority,
                                        era = coinItem.civilizationOrCountry,
                                        year = coinItem.yearRange,
                                        denomination = coinItem.denomination,
                                        metal = coinItem.metal,
                                        gradingService = "Raw",
                                        grade = "Choice VF",
                                        certificationNumber = "",
                                        acquisitionCost = coinItem.preciousMetalMeltValueUsd,
                                        currentEstimatedValue = coinItem.preciousMetalMeltValueUsd * 20.0,
                                        provenance = "Acquired through Numisma Catalog."
                                    )
                                    val res = repository.addSpecimen(newSpecimen)
                                    if (res.isSuccess) {
                                        snackbarHostState.showSnackbar("Specimen saved to your Personal Digital Vault.")
                                    } else {
                                        snackbarHostState.showSnackbar("Failed to add to vault: ${res.exceptionOrNull()?.localizedMessage}")
                                    }
                                }
                            },
                            onInspectWithAi = {
                                currentScreen = Screen.SCAN_AI
                            }
                        )
                    }

                    Screen.SCAN_AI -> {
                        ScanAiScreen(
                            onSaveToVault = { specimen ->
                                coroutineScope.launch {
                                    val res = repository.addSpecimen(specimen)
                                    if (res.isSuccess) {
                                        snackbarHostState.showSnackbar("AI Authenticated Specimen added to Personal Vault.")
                                    } else {
                                        snackbarHostState.showSnackbar("Vault save error: ${res.exceptionOrNull()?.localizedMessage}")
                                    }
                                }
                            }
                        )
                    }

                    Screen.VAULT -> {
                        VaultScreen(
                            specimens = activeSpecimens,
                            isLoading = false,
                            onAddSpecimen = { specimen ->
                                coroutineScope.launch {
                                    val res = repository.addSpecimen(specimen)
                                    if (res.isSuccess) {
                                        snackbarHostState.showSnackbar("Specimen securely registered in cloud vault.")
                                    } else {
                                        snackbarHostState.showSnackbar("Error saving specimen: ${res.exceptionOrNull()?.localizedMessage}")
                                    }
                                }
                            },
                            onDeleteSpecimen = { specimenId ->
                                coroutineScope.launch {
                                    val res = repository.deleteSpecimen(specimenId)
                                    if (res.isSuccess) {
                                        snackbarHostState.showSnackbar("Specimen removed from vault.")
                                    } else {
                                        snackbarHostState.showSnackbar("Failed to remove: ${res.exceptionOrNull()?.localizedMessage}")
                                    }
                                }
                            }
                        )
                    }

                    Screen.VALUATION -> {
                        ValuationMarketScreen()
                    }

                    Screen.DEALERS -> {
                        DealerDirectoryScreen()
                    }

                    Screen.MARKETPLACE -> {
                        MarketplaceScreen(
                            listings = activeListings,
                            onCreateListing = { newListing ->
                                coroutineScope.launch {
                                    val res = repository.createListing(newListing)
                                    if (res.isSuccess) {
                                        snackbarHostState.showSnackbar("Listing posted to Numisma Marketplace.")
                                    } else {
                                        snackbarHostState.showSnackbar("Listing failed: ${res.exceptionOrNull()?.localizedMessage}")
                                    }
                                }
                            },
                            onPlaceBid = { listingId, amount ->
                                coroutineScope.launch {
                                    val res = repository.placeBid(listingId, amount)
                                    if (res.isSuccess) {
                                        snackbarHostState.showSnackbar("Bid of $$amount placed successfully.")
                                    } else {
                                        snackbarHostState.showSnackbar("Bid error: ${res.exceptionOrNull()?.localizedMessage}")
                                    }
                                }
                            }
                        )
                    }

                    Screen.AI_CHAT -> {
                        AiChatScreen(
                            onNavigateBack = { currentScreen = Screen.HOME }
                        )
                    }

                    Screen.COMMUNITY -> {
                        CommunityScreen(
                            repository = repository,
                            onNavigateBack = { currentScreen = Screen.HOME }
                        )
                    }
                }
            }
        }
    }
}
