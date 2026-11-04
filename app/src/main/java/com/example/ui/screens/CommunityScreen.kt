package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DiscussionThread
import com.example.data.repository.NumismaRepository
import com.example.ui.theme.CoinGold
import com.example.ui.theme.DarkBronze
import com.example.ui.theme.LightGold
import com.example.ui.theme.ObsidianBlack
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    repository: NumismaRepository,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val rawDiscussions by repository.observeDiscussions().collectAsState(initial = emptyList())

    val defaultSampleDiscussions = remember {
        listOf(
            DiscussionThread(
                id = "disc-1",
                authorName = "Marcus Aurelius (Fellow, ANS)",
                title = "Counterfeit Alert: High-quality transfer die owls emerging from Southeastern Europe",
                content = "Warning to dealers and collectors: recent Athenian tetradrachms exhibiting identical radial test cut marks and crystalline flan anomalies are appearing in uncatalogued online auctions. Inspect edge seams under 40x magnification.",
                category = "Forensics",
                replyCount = 38
            ),
            DiscussionThread(
                id = "disc-2",
                authorName = "Dr. Elena Rostova",
                title = "New Die Link identified for Julius Caesar Elephant Denarius (Crawford 443/1)",
                content = "Review of the obverse die state indicates a previously unrecorded progressive die crack extending from the elephant trunk through the CAESAR legend. Photographic documentation attached.",
                category = "Research",
                replyCount = 19
            ),
            DiscussionThread(
                id = "disc-3",
                authorName = "VaultKeeper_99",
                title = "Gold Stater Conservation: Intercept Shield vs Double Kointain Capsules?",
                content = "Seeking experiences on long-term microclimate stabilization for Macedonian and Celtic gold staters. What is the consensus among museum conservators regarding sulfur vapor mitigation?",
                category = "Preservation",
                replyCount = 24
            ),
            DiscussionThread(
                id = "disc-4",
                authorName = "Heritage_Collector",
                title = "Market Analysis: Premium expansion for Choice XF vs MS Ancients in 2026",
                content = "A quantitative breakdown of hammer prices across 5 major auction houses over the last 18 months shows striking quality is now outpacing nominal Sheldon grades.",
                category = "Valuation",
                replyCount = 42
            )
        )
    }

    val discussions = if (rawDiscussions.isNotEmpty()) rawDiscussions else defaultSampleDiscussions

    val categories = listOf("All", "Forensics", "Research", "Preservation", "Valuation", "Identification")
    var selectedCategory by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val filteredDiscussions = remember(discussions, selectedCategory) {
        if (selectedCategory == "All") discussions
        else discussions.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Numisma Symposium",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = LightGold
                        )
                        Text(
                            "Global Scholar & Collector Discussions",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("community_back_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CoinGold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ObsidianBlack
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = CoinGold,
                contentColor = ObsidianBlack,
                modifier = Modifier.testTag("create_thread_fab")
            ) {
                Icon(Icons.Default.AddComment, contentDescription = "New Discussion Thread")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = ObsidianBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Category Filter Bar
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CoinGold,
                            selectedLabelColor = ObsidianBlack,
                            containerColor = Color(0xFF1E1B15),
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag("category_chip_${cat.lowercase()}")
                    )
                }
            }

            // Discussions list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp)
            ) {
                items(filteredDiscussions, key = { it.id.ifBlank { it.title } }) { thread ->
                    DiscussionCard(thread = thread)
                }
            }
        }
    }

    if (showCreateDialog) {
        var threadTitle by remember { mutableStateOf("") }
        var threadContent by remember { mutableStateOf("") }
        var threadCat by remember { mutableStateOf("Forensics") }
        var isSubmitting by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { if (!isSubmitting) showCreateDialog = false },
            title = {
                Text("Start Research Thread", color = LightGold, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = threadTitle,
                        onValueChange = { threadTitle = it },
                        label = { Text("Topic Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("new_thread_title_input"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = threadContent,
                        onValueChange = { threadContent = it },
                        label = { Text("Details & Evidence") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("new_thread_content_input"),
                        minLines = 3,
                        maxLines = 6
                    )

                    Text(
                        "Category:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Forensics", "Research", "Preservation", "Valuation").forEach { cat ->
                            val isSel = threadCat == cat
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) CoinGold else Color(0xFF24201A))
                                    .clickable { threadCat = cat }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    cat,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSel) ObsidianBlack else Color.LightGray
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (threadTitle.isBlank() || threadContent.isBlank()) return@Button
                        isSubmitting = true
                        coroutineScope.launch {
                            val user = Firebase.auth.currentUser
                            val newThread = DiscussionThread(
                                authorName = user?.displayName ?: "Fellow Numismatist",
                                title = threadTitle.trim(),
                                content = threadContent.trim(),
                                category = threadCat,
                                replyCount = 0
                            )
                            val res = repository.createDiscussion(newThread)
                            isSubmitting = false
                            if (res.isSuccess) {
                                showCreateDialog = false
                                snackbarHostState.showSnackbar("Research thread published to symposium.")
                            } else {
                                snackbarHostState.showSnackbar("Failed to publish: ${res.exceptionOrNull()?.localizedMessage}")
                            }
                        }
                    },
                    enabled = threadTitle.isNotBlank() && threadContent.isNotBlank() && !isSubmitting,
                    colors = ButtonDefaults.buttonColors(containerColor = CoinGold, contentColor = ObsidianBlack),
                    modifier = Modifier.testTag("submit_thread_button")
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = ObsidianBlack)
                    } else {
                        Text("Publish")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showCreateDialog = false },
                    enabled = !isSubmitting
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            containerColor = Color(0xFF1E1B15)
        )
    }
}

@Composable
fun DiscussionCard(thread: DiscussionThread) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("discussion_thread_${thread.id}"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B15)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = DarkBronze.copy(alpha = 0.4f)
                ) {
                    Text(
                        text = thread.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = CoinGold,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.AutoMirrored.Filled.Comment,
                        contentDescription = "Replies",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "${thread.replyCount ?: 0} replies",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = thread.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = thread.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(DarkBronze),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = thread.authorName.take(1).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = LightGold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = thread.authorName,
                    style = MaterialTheme.typography.labelMedium,
                    color = LightGold
                )
            }
        }
    }
}
