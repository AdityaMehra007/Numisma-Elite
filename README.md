# Numisma Elite

> **Global Coin Intelligence, Optical Authentication, Valuation & Numismatic Marketplace**  
> *Engineered by **[Aditya Mehra](https://adityamehra007.github.io)** | Bengaluru, Karnataka, India*  
> *Portfolio:* [adityamehra007.github.io](https://adityamehra007.github.io) | *Flagship System:* [AdityaMehra007/anti](https://github.com/AdityaMehra007/anti)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4.svg?logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Google Gemini API](https://img.shields.io/badge/Google%20Gemini-Pro%20%2F%20Flash%20AI-8E75B2.svg?logo=google&logoColor=white)](https://ai.google.dev/)
[![Android SDK](https://img.shields.io/badge/Android%20SDK-34+-3DDC84.svg?logo=android&logoColor=white)](https://developer.android.com/)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM%20%2B%20StateFlow-00599C.svg)](https://developer.android.com/topic/architecture)

---

## 📌 Executive Overview

**Numisma Elite** is a production-grade Android mobile application and operational platform engineered using modern **Kotlin 2.0+**, **Jetpack Compose (Material 3)**, and integrated **Google Gemini AI** intelligence.

Designed to solve complex real-world workflows with deterministic software reliability, **Numisma Elite** combines responsive reactive UI state machines, robust offline-first local persistence, and cloud AI inference to deliver an elite digital experience.

---

## 🏗️ Core Architecture & Technical Design

The platform strictly follows Android's recommended **Clean Architecture & MVVM** paradigm with unidirectional data flow (UDF):

```mermaid
graph TD
    UI[Jetpack Compose UI & Material 3] -->|User Actions| VM[Android ViewModel & StateFlow]
    VM -->|UI State / Coroutine Dispatchers| UI
    VM -->|Data Requests| REPO[Repository Abstraction Layer]
    REPO -->|Local Cache / Queries| LOCAL[(Room SQLite / SharedPreferences)]
    REPO -->|Cloud State / Sync| CLOUD[(Firebase / Remote APIs)]
    REPO -->|Inference & Prompts| AI[Google Gemini AI Engine]
```

### Architectural Tenets
1. **Zero Vibe Coding & Predictable State**: State is modeled strictly via immutable Kotlin data classes and exposed via Kotlin `StateFlow`.
2. **Deep Modules**: High cohesion within feature domains; UI components communicate with repositories exclusively through clear, testable interfaces.
3. **Hardware & Battery Optimization**: Minimal memory overhead, zero redundant recompositions, and non-blocking asynchronous I/O via Kotlin Coroutines on `Dispatchers.IO`.
4. **Resilient Network & AI Tier**: Graceful fallback patterns for network partitions and rate limits on LLM API endpoints.

---

## 🚀 Key Modules & Capabilities

### Optical Authentication & Grading Engine
High-resolution macro inspection interface analyzing strike quality, surface wear, mint luster, and die varieties against Sheldon 70-point standards.

### Global Numismatic Catalog
Extensive database covering ancient coinage, colonial emissions, modern commemoratives, and bullion strikes with comprehensive mint mark guides.

### Auction History & Valuation Curves
Historical auction realization aggregator displaying realized price trends, population reports, and historical CAGR appreciation metrics.

### Verified Dealer Directory
Direct connection portal to certified numismatic dealerships, PCGS/NGC authorized submitters, and professional auction houses worldwide.

### Numisma AI Appraiser
Multimodal Gemini-powered numismatic advisor providing instant preliminary identification, historical context, rarity scoring, and market insights.

### Personal Vault Management
Secure collection tracking with insurance appraisal documentation, acquisition cost basis, and automated net collection valuation updates.

---

## 📱 Primary Screens & Visual Components

Key Compose screens and interface destinations include:
`HomeScreen`, `CatalogScreen`, `CoinDetailScreen`, `DealerDirectoryScreen`, `CommunityScreen`, `AiChatScreen`

Each screen incorporates custom Material 3 typography, fluid gestures, dark/light theme elevation surfaces, and accessible semantics.

---

## 🛠️ Technology Stack & Dependencies

| Layer | Technologies |
|---|---|
| **Core Language** | Kotlin 2.0+ (100% Kotlin codebase) |
| **UI Framework** | Jetpack Compose, Material 3, Foundation, Animation |
| **Asynchronous Engine** | Kotlin Coroutines & Asynchronous Flow (`StateFlow`, `SharedFlow`) |
| **Architecture** | ViewModel, Lifecycle Runtime, Navigation Compose |
| **Persistence** | Room Database (SQLite) / Firebase Firestore / DataStore |
| **AI Integration** | Google Gemini Generative AI SDK |
| **Build & Tooling** | Gradle Kotlin DSL (`build.gradle.kts`), Android Studio |

---

## 💻 Getting Started & Local Build

### Prerequisites
- **Android Studio** Hedgehog (2023.1.1) or newer
- **JDK 17** (LTS) or higher
- **Android SDK** API Level 34 installed

### Installation & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/AdityaMehra007/Numisma-Elite.git
   cd Numisma-Elite
   ```

2. **Configure Environment Keys**:
   Create or update `local.properties` in the project root:
   ```properties
   gemini.api.key=YOUR_GOOGLE_GEMINI_API_KEY
   ```

3. **Build the Debug APK**:
   ```bash
   # Windows PowerShell
   .\gradlew.bat assembleDebug

   # macOS / Linux
   ./gradlew assembleDebug
   ```

4. **Execute Unit Tests**:
   ```bash
   .\gradlew.bat test
   ```

---

## 👤 Engineering & Operational Leadership

**Aditya Mehra**  
*Strategic Operations Leader & Systems Architect*  
- **Alma Mater**: BBA International Business, Dayananda Sagar University (DSU), Bengaluru  
- **GitHub**: [@AdityaMehra007](https://github.com/AdityaMehra007)  
- **Portfolio & Mission Control**: [https://adityamehra007.github.io](https://adityamehra007.github.io)  
- **Flagship Autonomous System**: [AdityaMehra007/anti](https://github.com/AdityaMehra007/anti)

---

## 📄 License

This repository is distributed under the **MIT License**. See [LICENSE](LICENSE) for complete terms.
