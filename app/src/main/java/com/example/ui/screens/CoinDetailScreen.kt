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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CoinCatalogItem
import com.example.data.repository.AuctionHistoryRepository
import com.example.ui.theme.BronzeAccent
import com.example.ui.theme.CardSurface
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CoinDetailScreen(
    coin: CoinCatalogItem,
    onBack: () -> Unit,
    onAddToVault: (CoinCatalogItem) -> Unit,
    onInspectWithAi: (CoinCatalogItem) -> Unit
) {
    val auctionRecords = AuctionHistoryRepository.getRecordsForCoin(coin.canonicalName)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack, modifier = Modifier.testTag("coin_detail_back_button")) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = GoldPrimary
                )
            }
            Text(
                text = "NUMISMATIC RECORD",
                color = GoldPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            IconButton(onClick = { /* Share */ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = TextSecondary
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 90.dp)
        ) {
            // Visual Coin Emblem / Medallion Header
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(GoldLight, GoldPrimary, BronzeAccent)
                                )
                            )
                            .border(3.dp, GoldLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = coin.canonicalName,
                            tint = Color(0xFF1E1504),
                            modifier = Modifier.size(76.dp)
                        )
                    }
                }
            }

            // Title & Classification
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF261D09))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${coin.civilizationOrCountry} • ${coin.rarityIndex}",
                            color = GoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = coin.canonicalName,
                        color = TextPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 0.5.sp
                    )

                    Text(
                        text = "Alt: ${coin.alternativeNames}",
                        color = TextMuted,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Quick Actions Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onAddToVault(coin) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("coin_detail_add_vault_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = Color(0xFF1C1304)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Add to Vault", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    Button(
                        onClick = { onInspectWithAi(coin) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("coin_detail_ai_review_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF26334B),
                            contentColor = GoldLight
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "AI Inspection", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Physical & Production Specifications Card
            item {
                SectionCard(title = "PHYSICAL & METALLURGICAL SPECIFICATIONS") {
                    SpecRow(label = "Issuing Authority", value = coin.issuingAuthority)
                    SpecRow(label = "Period / Dynasty", value = coin.periodOrDynasty)
                    SpecRow(label = "Mint Location", value = coin.mintLocation)
                    SpecRow(label = "Year / Chronology", value = coin.yearRange)
                    SpecRow(label = "Denomination", value = coin.denomination)
                    SpecRow(label = "Metal & Purity", value = "${coin.metal} (${coin.fineness})")
                    SpecRow(label = "Standard Weight", value = "${coin.standardWeightGrams} grams")
                    SpecRow(label = "Diameter", value = "${coin.standardDiameterMm} mm")
                    SpecRow(label = "Estimated Surviving", value = coin.estimatedSurviving)
                    SpecRow(label = "Catalog Reference", value = coin.catalogReference)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Iconography, Epigraphy & Translations
            item {
                SectionCard(title = "EPIGRAPHY, SYMBOLISM & ARTISTIC MOTIFS") {
                    Text(
                        text = "Obverse Description",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = coin.obverseDesc,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    Text(
                        text = "Reverse Description",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = coin.reverseDesc,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    HorizontalDivider(color = Color(0xFF26334B), modifier = Modifier.padding(vertical = 6.dp))

                    Text(
                        text = "Original Inscription (Script)",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${coin.obverseInscription} / ${coin.reverseInscription}",
                        color = GoldPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                    )

                    Text(
                        text = "Translation & Meaning",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = coin.inscriptionTranslation,
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Historical Significance & Monetary Story
            item {
                SectionCard(title = "HISTORICAL CONTEXT & MONETARY SIGNIFICANCE") {
                    Text(
                        text = coin.historicalContext,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 4-Tier Valuation Matrix
            item {
                SectionCard(title = "NUMISMA 4-TIER PRICE INTELLIGENCE") {
                    ValuationTierRow(
                        tierName = "A. Documented Realized Sales",
                        value = coin.marketPriceRangeUsd,
                        desc = "Verified actual completed sales at major auction houses (Heritage, CNG, Spink)."
                    )
                    ValuationTierRow(
                        tierName = "B. Current Market Estimate",
                        value = coin.marketPriceRangeUsd,
                        desc = "Reasoned econometric estimate based on recent grade-matched transaction volume."
                    )
                    ValuationTierRow(
                        tierName = "C. Retail Asking Price",
                        value = "10–25% above realized auction hammer",
                        desc = "Dealer list price subject to negotiation and inventory holding overhead."
                    )
                    ValuationTierRow(
                        tierName = "D. Precious-Metal Melt Value",
                        value = "$${String.format("%.2f", coin.preciousMetalMeltValueUsd)} USD",
                        desc = "Intrinsic bullion baseline based on verified fine metal content at current spot price."
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Comparable Auction Records
            if (auctionRecords.isNotEmpty()) {
                item {
                    SectionCard(title = "COMPARABLE DOCUMENTED AUCTION RESULTS") {
                        auctionRecords.forEach { record ->
                            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${record.auctionHouse} (${record.saleDate})",
                                        color = GoldLight,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${record.currency} ${record.realizedPrice.toInt()}",
                                        color = GoldPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "${record.lotNumber} • Grade: ${record.grade}",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "Pedigree: ${record.provenance}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                                HorizontalDivider(color = Color(0xFF1E283C), modifier = Modifier.padding(top = 6.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                color = GoldPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextMuted, fontSize = 12.sp)
        Text(text = value, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ValuationTierRow(tierName: String, value: String, desc: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = tierName, color = GoldLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = value, color = GoldPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = desc, color = TextMuted, fontSize = 11.sp, lineHeight = 15.sp)
    }
}
