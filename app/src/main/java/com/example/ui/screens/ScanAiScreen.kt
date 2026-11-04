package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.NumismaAiEngine
import com.example.data.model.AiIdentificationResult
import com.example.data.model.Specimen
import com.example.ui.theme.BronzeAccent
import com.example.ui.theme.CardSurface
import com.example.ui.theme.CardSurfaceElevated
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber
import kotlinx.coroutines.launch

@Composable
fun ScanAiScreen(
    onSaveToVault: (Specimen) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var userNotes by remember { mutableStateOf("High-relief archaic strike with full crest on helmet and complete owl tail feathers.") }
    var weightInput by remember { mutableStateOf("17.18") }
    var diameterInput by remember { mutableStateOf("24.5") }
    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisResult by remember { mutableStateOf<AiIdentificationResult?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var savedSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 90.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "AI COIN INTELLIGENCE & AUTHENTICATION",
                    color = GoldPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Powered by Gemini 4 Argon with High Thinking enabled",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Camera / Specimen Inspection Viewfinder
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(1.dp, GoldBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(GoldLight, GoldPrimary, BronzeAccent)
                                    )
                                )
                                .border(2.dp, GoldLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Camera Viewfinder",
                                tint = Color(0xFF1F1504),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Specimen Imaging & Optical Boundary Active",
                            color = GoldLight,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Analyzing surface luster, die flow lines & flan metallurgy",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Measured Metrics Inputs
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF232D42), RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = CardSurfaceElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PHYSICAL MEASUREMENT CALIBRATION",
                        color = GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = weightInput,
                            onValueChange = { weightInput = it },
                            label = { Text("Weight (g)", fontSize = 11.sp) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("scan_weight_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = GoldBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = diameterInput,
                            onValueChange = { diameterInput = it },
                            label = { Text("Diameter (mm)", fontSize = 11.sp) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("scan_diameter_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldPrimary,
                                unfocusedBorderColor = GoldBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = userNotes,
                        onValueChange = { userNotes = it },
                        label = { Text("Visual Inscriptions & Observed Mint Marks", fontSize = 11.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("scan_notes_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = GoldBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 2
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Action Button: Run Gemini 4 Argon Analysis
        item {
            Button(
                onClick = {
                    isAnalyzing = true
                    errorMessage = null
                    savedSuccess = false
                    coroutineScope.launch {
                        val w = weightInput.toDoubleOrNull()
                        val d = diameterInput.toDoubleOrNull()
                        val result = NumismaAiEngine.analyzeCoinMultimodal(
                            bitmap = null,
                            userNotes = userNotes,
                            measuredWeight = w,
                            measuredDiameter = d
                        )
                        isAnalyzing = false
                        if (result.isSuccess) {
                            analysisResult = result.getOrNull()
                        } else {
                            errorMessage = result.exceptionOrNull()?.localizedMessage ?: "Analysis encountered an error."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("run_ai_analysis_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = Color(0xFF1E1504)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = !isAnalyzing
            ) {
                if (isAnalyzing) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color(0xFF1E1504),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Gemini 4 Argon High-Thinking In Progress...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Execute Deep AI Identification & Screening",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // AI Results Section
        analysisResult?.let { res ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, GoldPrimary, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ATTRIBUTION & AUTHENTICITY REPORT",
                                color = GoldPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            val riskColor = when (res.authenticityRisk) {
                                "LOW_AUTHENTIC_INDICATORS" -> SuccessGreen
                                "VERIFICATION_RECOMMENDED" -> WarningAmber
                                else -> DangerRed
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(riskColor.copy(alpha = 0.2f))
                                    .border(1.dp, riskColor, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = res.authenticityRisk.replace("_", " "),
                                    color = riskColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = res.identifiedCoinName,
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )

                        Text(
                            text = "${res.civilization} • ${res.authority} • ${res.approximateDate}",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Text(
                            text = "Assessed Condition: ${res.estimatedGrade} • Metal: ${res.metal}",
                            color = GoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        HorizontalDivider(color = Color(0xFF26334B), modifier = Modifier.padding(vertical = 12.dp))

                        // Visual Observations list
                        Text(
                            text = "High-Thinking Forensic Observations:",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        res.visualObservations.forEach { obs ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(text = "• ", color = GoldPrimary, fontSize = 12.sp)
                                Text(text = obs, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
                            }
                        }

                        HorizontalDivider(color = Color(0xFF26334B), modifier = Modifier.padding(vertical = 12.dp))

                        // Valuation Grid
                        Text(
                            text = "Numisma 4-Tier Valuation Intelligence:",
                            color = GoldPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            ValuationBox(
                                label = "Realized Sales",
                                value = "$${res.valuation.realizedComparablePriceUsd.toInt()}",
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ValuationBox(
                                label = "Market Estimate",
                                value = "$${res.valuation.currentMarketEstimateUsd.toInt()}",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ValuationBox(
                                label = "Retail Asking",
                                value = "$${res.valuation.retailAskingPriceUsd.toInt()}",
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ValuationBox(
                                label = "Precious Metal",
                                value = "$${String.format("%.2f", res.valuation.preciousMetalMeltValueUsd)}",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = res.valuation.methodologyNote,
                            color = TextMuted,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Save to Vault Button
                        Button(
                            onClick = {
                                val newSpecimen = Specimen(
                                    coinName = res.identifiedCoinName,
                                    issuingAuthority = res.authority,
                                    era = res.civilization,
                                    year = res.approximateDate,
                                    metal = res.metal,
                                    grade = res.estimatedGrade,
                                    gradingService = "AI Preliminary Review",
                                    measuredWeightGrams = weightInput.toDoubleOrNull(),
                                    measuredDiameterMm = diameterInput.toDoubleOrNull(),
                                    currentEstimatedValue = res.valuation.currentMarketEstimateUsd,
                                    notes = "AI Review: ${res.visualObservations.firstOrNull().orEmpty()}"
                                )
                                onSaveToVault(newSpecimen)
                                savedSuccess = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("scan_save_to_vault_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (savedSuccess) DeepEmerald else GoldPrimary,
                                contentColor = if (savedSuccess) TextPrimary else Color(0xFF1E1504)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = if (savedSuccess) Icons.Default.CheckCircle else Icons.Default.BookmarkAdd,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (savedSuccess) "Saved to Personal Vault!" else "Record Specimen in Personal Vault",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ValuationBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0F1726))
            .border(1.dp, Color(0xFF222C3F), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Column {
            Text(text = label, color = TextMuted, fontSize = 10.sp)
            Text(
                text = value,
                color = GoldPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
