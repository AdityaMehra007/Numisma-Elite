package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MarketplaceListing
import com.example.ui.theme.CardSurface
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun MarketplaceScreen(
    listings: List<MarketplaceListing>,
    onCreateListing: (MarketplaceListing) -> Unit,
    onPlaceBid: (String, Double) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }
    var bidTargetListing by remember { mutableStateOf<MarketplaceListing?>(null) }
    var bidAmountInput by remember { mutableStateOf("") }

    val filters = listOf("All", "FIXED_PRICE", "AUCTION", "BEST_OFFER")

    val filteredListings = if (selectedFilter == "All") {
        listings
    } else {
        listings.filter { it.listingType == selectedFilter }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground),
        containerColor = ObsidianBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = GoldPrimary,
                contentColor = Color(0xFF1E1504),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .testTag("marketplace_fab_create_listing")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Listing")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 120.dp)
        ) {
            // Header
            item {
                Column {
                    Text(
                        text = "NUMISMA GLOBAL MARKETPLACE",
                        color = GoldPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Direct peer-to-peer and dealer offerings with authenticated certification",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Buyer Protection Guarantee Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, GoldBorder, RoundedCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceElevated),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF261D09)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Numisma Escrow & Authenticity Assurance",
                                color = GoldLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Funds are securely protected in escrow until physical slab inspection is satisfied.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Format Filter Chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filters) { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = { Text(text = filter.replace("_", " "), fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = GoldPrimary,
                                selectedLabelColor = Color(0xFF1E1504),
                                containerColor = CardSurface,
                                labelColor = TextSecondary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedFilter == filter,
                                borderColor = if (selectedFilter == filter) GoldPrimary else GoldBorder
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            if (filteredListings.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFF222C3F), RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No Listings in this category",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Be the first collector to publish a coin listing or auction offering.",
                                color = TextMuted,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            } else {
                items(filteredListings) { listing ->
                    ListingCard(
                        listing = listing,
                        onBidClick = {
                            bidTargetListing = listing
                            bidAmountInput = ((listing.currentBid ?: listing.price) + 50.0).toString()
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    // Create Listing Modal
    if (showCreateDialog) {
        CreateListingDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { newListing ->
                onCreateListing(newListing)
                showCreateDialog = false
            }
        )
    }

    // Place Bid Dialog
    bidTargetListing?.let { target ->
        AlertDialog(
            onDismissRequest = { bidTargetListing = null },
            title = {
                Text(
                    text = "Place Bid: ${target.coinTitle}",
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Current Bid: ${target.currency} ${target.currentBid ?: target.price}",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = bidAmountInput,
                        onValueChange = { bidAmountInput = it },
                        label = { Text("Your Bid (${target.currency})") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("bid_amount_input"),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = bidAmountInput.toDoubleOrNull()
                        if (amount != null) {
                            onPlaceBid(target.id, amount)
                            bidTargetListing = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1504)),
                    modifier = Modifier.testTag("confirm_bid_button")
                ) {
                    Text("Submit Bid", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { bidTargetListing = null }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = CardSurfaceElevated
        )
    }
}

@Composable
private fun ListingCard(
    listing: MarketplaceListing,
    onBidClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder, RoundedCornerShape(14.dp))
            .testTag("marketplace_listing_card_${listing.id}"),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Seller: ${listing.sellerName}",
                    color = GoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF261D09))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = listing.listingType.replace("_", " "),
                        color = GoldPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = listing.coinTitle,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "${listing.country} • ${listing.metal} • Grade: ${listing.grade} ${listing.certification.orEmpty()}",
                color = TextSecondary,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            Text(
                text = listing.description,
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                maxLines = 2,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (listing.listingType == "AUCTION") "Current Bid (${listing.bidCount ?: 0} bids)" else "Asking Price",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "${listing.currency} ${listing.currentBid ?: listing.price}",
                        color = GoldPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onBidClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = Color(0xFF1E1504)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("listing_action_button_${listing.id}")
                ) {
                    Icon(
                        imageVector = if (listing.listingType == "AUCTION") Icons.Default.Gavel else Icons.Default.Storefront,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (listing.listingType == "AUCTION") "Place Bid" else "Make Offer",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateListingDialog(
    onDismiss: () -> Unit,
    onConfirm: (MarketplaceListing) -> Unit
) {
    var coinTitle by remember { mutableStateOf("") }
    var sellerName by remember { mutableStateOf("Verified Collector") }
    var country by remember { mutableStateOf("Ancient Greece") }
    var era by remember { mutableStateOf("Classical") }
    var metal by remember { mutableStateOf("Silver") }
    var grade by remember { mutableStateOf("Choice VF") }
    var priceInput by remember { mutableStateOf("1200") }
    var listingType by remember { mutableStateOf("FIXED_PRICE") }
    var desc by remember { mutableStateOf("Well-centered flan with full inscriptions and sharp details.") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Create Marketplace Offering",
                color = GoldPrimary,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = coinTitle,
                    onValueChange = { coinTitle = it },
                    label = { Text("Coin Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dialog_listing_title"),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("Country/Empire") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = metal,
                        onValueChange = { metal = it },
                        label = { Text("Metal (Gold/Silver)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = grade,
                        onValueChange = { grade = it },
                        label = { Text("Grade (e.g. MS63, VF)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { priceInput = it },
                        label = { Text("Price (USD)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description & Provenance") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (coinTitle.isNotBlank()) {
                        val newListing = MarketplaceListing(
                            sellerName = sellerName.trim(),
                            coinTitle = coinTitle.trim(),
                            country = country.trim(),
                            era = era.trim(),
                            metal = metal.trim(),
                            grade = grade.trim(),
                            price = priceInput.toDoubleOrNull() ?: 100.0,
                            currency = "USD",
                            listingType = listingType,
                            status = "ACTIVE",
                            description = desc.trim()
                        )
                        onConfirm(newListing)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = Color(0xFF1E1504)),
                modifier = Modifier.testTag("dialog_confirm_create_listing")
            ) {
                Text("Publish Listing", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        },
        containerColor = CardSurfaceElevated
    )
}
