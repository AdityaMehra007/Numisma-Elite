package com.example.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.NumismaAiEngine
import com.example.ui.theme.CoinGold
import com.example.ui.theme.DarkBronze
import com.example.ui.theme.LightGold
import com.example.ui.theme.ObsidianBlack
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Locale

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "user" or "scholar"
    val text: String,
    val modelTag: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ScholarModelMode(val label: String, val modelName: String, val subtitle: String) {
    SCHOLAR_FLASH("Scholar 3.8", "gemini-3.8-flash", "Scholarly research & multi-turn dialog"),
    DEEP_ARGON("Argon 4 Deep Thinking", "gemini-4-argon", "Forensic die analysis & high reasoning"),
    LITE_FAST("Lite 3.1 Rapid", "gemini-3.1-flash-lite", "Ultra low-latency quick answers")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var selectedMode by remember { mutableStateOf(ScholarModelMode.SCHOLAR_FLASH) }
    var inputText by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "scholar",
                text = "Welcome to Numisma Scholar Intelligence. I am your specialized numismatic research partner powered by Gemini multimodal and reasoning models.\n\nAsk me about mint attribution (RIC, Crawford, BMC), epigraphy decipherment, die forensic checks, counterfeit indicators, or provenance research.",
                modelTag = "Numisma Scholar"
            )
        )
    }

    val quickPrompts = listOf(
        "Decipher mint mark CONOB on Byzantine Solidus",
        "How to detect transfer-die cast counterfeits?",
        "Explain RIC VII Siscia 140 rarity & varieties",
        "What are radial flow lines on struck coins?",
        "Attribute 1794 Flowing Hair Dollar die state"
    )

    // Speech to text launcher
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            if (!spokenText.isNullOrBlank()) {
                inputText = spokenText
            }
        }
    }

    val context = LocalContext.current

    fun sendMessage(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty() || isThinking) return

        messages.add(ChatMessage(sender = "user", text = trimmed))
        inputText = ""
        isThinking = true

        coroutineScope.launch {
            listState.animateScrollToItem(messages.size)

            try {
                when (selectedMode) {
                    ScholarModelMode.LITE_FAST -> {
                        val result = NumismaAiEngine.quickQuery(trimmed)
                        messages.add(
                            ChatMessage(
                                sender = "scholar",
                                text = result.getOrElse { "Error: ${it.localizedMessage}" },
                                modelTag = "gemini-3.1-flash-lite"
                            )
                        )
                    }
                    ScholarModelMode.DEEP_ARGON -> {
                        // High-thinking prompt
                        val promptWithContext = "Analyze this numismatic query with deep forensic reasoning: $trimmed"
                        val response = NumismaAiEngine.highThinkingArgonModel.generateContent(promptWithContext)
                        val replyText = response.text ?: "No evaluation generated."
                        messages.add(
                            ChatMessage(
                                sender = "scholar",
                                text = replyText,
                                modelTag = "gemini-4-argon (ThinkingLevel.HIGH)"
                            )
                        )
                    }
                    ScholarModelMode.SCHOLAR_FLASH -> {
                        // Stream multi-turn response
                        val assistantMsgId = java.util.UUID.randomUUID().toString()
                        val placeholder = ChatMessage(
                            id = assistantMsgId,
                            sender = "scholar",
                            text = "",
                            modelTag = "gemini-3.8-flash"
                        )
                        messages.add(placeholder)

                        var streamedText = ""
                        NumismaAiEngine.streamScholarResponse(trimmed)
                            .catch { e ->
                                val idx = messages.indexOfFirst { it.id == assistantMsgId }
                                if (idx != -1) {
                                    messages[idx] = messages[idx].copy(text = "Scholar Consultation Error: ${e.localizedMessage}")
                                }
                            }
                            .collect { chunk ->
                                streamedText = chunk
                                val idx = messages.indexOfFirst { it.id == assistantMsgId }
                                if (idx != -1) {
                                    messages[idx] = messages[idx].copy(text = streamedText)
                                }
                                listState.scrollToItem(messages.size - 1)
                            }
                    }
                }
            } catch (e: Exception) {
                messages.add(
                    ChatMessage(
                        sender = "scholar",
                        text = "Consultation failed: ${e.localizedMessage ?: "Unknown network exception"}. Please verify connection.",
                        modelTag = "System Error"
                    )
                )
            } finally {
                isThinking = false
                listState.animateScrollToItem(messages.size)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Numisma AI Scholar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = LightGold
                        )
                        Text(
                            selectedMode.subtitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("ai_chat_back_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CoinGold
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            messages.clear()
                            messages.add(
                                ChatMessage(
                                    sender = "scholar",
                                    text = "Session refreshed. Ask any numismatic inquiry.",
                                    modelTag = "Numisma Scholar"
                                )
                            )
                        },
                        modifier = Modifier.testTag("ai_chat_clear_button")
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Clear Chat",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ObsidianBlack
                )
            )
        },
        containerColor = ObsidianBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Model Selector Segmented Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .background(Color(0xFF1E1B15), RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScholarModelMode.values().forEach { mode ->
                    val isSelected = selectedMode == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CoinGold else Color.Transparent)
                            .clickable { selectedMode = mode }
                            .padding(vertical = 8.dp)
                            .testTag("model_mode_${mode.name.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mode.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ObsidianBlack else MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }

            // Quick Question Prompt Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickPrompts) { prompt ->
                    SuggestionChip(
                        onClick = { sendMessage(prompt) },
                        label = {
                            Text(
                                prompt,
                                style = MaterialTheme.typography.labelSmall,
                                color = LightGold
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = DarkBronze.copy(alpha = 0.35f)
                        ),
                        border = SuggestionChipDefaults.suggestionChipBorder(
                            enabled = true,
                            borderColor = DarkBronze
                        ),
                        modifier = Modifier.testTag("quick_prompt_chip")
                    )
                }
            }

            // Message List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(messages, key = { it.id }) { msg ->
                    ChatBubble(message = msg)
                }

                if (isThinking) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = CoinGold
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                if (selectedMode == ScholarModelMode.DEEP_ARGON) {
                                    "Conducting forensic die & epigraphy analysis (gemini-4-argon)..."
                                } else {
                                    "Consulting Numisma Scholar..."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Bottom Input Bar
            Surface(
                color = Color(0xFF191713),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Voice Dictation Button (gemini-3.5-transcribe / Android Speech Recognizer)
                    IconButton(
                        onClick = {
                            try {
                                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Dictate numismatic notes or query...")
                                }
                                speechLauncher.launch(intent)
                            } catch (e: Exception) {
                                // Fallback speech recognizer prompt
                            }
                        },
                        modifier = Modifier
                            .testTag("ai_chat_mic_button")
                            .background(DarkBronze.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = "Voice Dictation",
                            tint = CoinGold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("ai_chat_input"),
                        placeholder = {
                            Text(
                                "Ask about dies, rarity, RIC, mints...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        },
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF221F19),
                            unfocusedContainerColor = Color(0xFF221F19),
                            focusedBorderColor = CoinGold,
                            unfocusedBorderColor = DarkBronze
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { sendMessage(inputText) },
                        enabled = inputText.isNotBlank() && !isThinking,
                        modifier = Modifier
                            .testTag("ai_chat_send_button")
                            .background(
                                if (inputText.isNotBlank() && !isThinking) CoinGold else Color.Gray.copy(alpha = 0.3f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = if (inputText.isNotBlank() && !isThinking) ObsidianBlack else Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.sender == "user"

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        if (!isUser && message.modelTag != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
            ) {
                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = CoinGold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = message.modelTag,
                    style = MaterialTheme.typography.labelSmall,
                    color = CoinGold,
                    fontSize = 10.sp
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) CoinGold else Color(0xFF24201A),
            tonalElevation = if (isUser) 0.dp else 2.dp,
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isUser) ObsidianBlack else MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
