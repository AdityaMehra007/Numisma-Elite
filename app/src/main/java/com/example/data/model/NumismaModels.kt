package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val tier: String = "COLLECTOR_ELITE", // EXPLORER, COLLECTOR_ELITE, PROFESSIONAL, INSTITUTIONAL
    val avatarUrl: String? = null,
    val bio: String? = null,
    val country: String? = null,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class Specimen(
    @DocumentId val id: String = "",
    val userId: String = "",
    val coinName: String = "",
    val catalogId: String? = null,
    val issuingAuthority: String? = null,
    val era: String? = null,
    val year: String? = null,
    val denomination: String? = null,
    val metal: String? = null,
    val grade: String = "Raw",
    val gradingService: String = "Raw",
    val certificationNumber: String? = null,
    val measuredWeightGrams: Double? = null,
    val measuredDiameterMm: Double? = null,
    val acquisitionCost: Double? = null,
    val acquisitionCurrency: String? = "USD",
    val acquisitionDate: String? = null,
    val currentEstimatedValue: Double? = null,
    val provenance: String? = null,
    val notes: String? = null,
    val imageUrl: String? = null,
    val isForSale: Boolean? = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class MarketplaceListing(
    @DocumentId val id: String = "",
    val userId: String = "",
    val sellerName: String = "",
    val specimenId: String? = null,
    val coinTitle: String = "",
    val country: String = "",
    val era: String = "",
    val metal: String = "",
    val grade: String = "",
    val certification: String? = null,
    val price: Double = 0.0,
    val currency: String = "USD",
    val listingType: String = "FIXED_PRICE", // FIXED_PRICE, AUCTION, BEST_OFFER
    val status: String = "ACTIVE", // ACTIVE, PENDING, SOLD, CANCELLED
    val currentBid: Double? = null,
    val bidCount: Int? = 0,
    val imageUrl: String? = null,
    val description: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class DiscussionThread(
    @DocumentId val id: String = "",
    val userId: String = "",
    val authorName: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "General",
    val replyCount: Int? = 0,
    val imageUrl: String? = null,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

data class CoinCatalogItem(
    val id: String,
    val canonicalName: String,
    val alternativeNames: String,
    val civilizationOrCountry: String,
    val issuingAuthority: String,
    val periodOrDynasty: String,
    val mintLocation: String,
    val yearRange: String,
    val denomination: String,
    val metal: String,
    val fineness: String,
    val standardWeightGrams: Double,
    val standardDiameterMm: Double,
    val obverseDesc: String,
    val reverseDesc: String,
    val obverseInscription: String,
    val reverseInscription: String,
    val inscriptionTranslation: String,
    val rarityIndex: String, // Common, Scarce, Rare (R1), Extremely Rare (R3), Legendary (R5)
    val estimatedSurviving: String,
    val catalogReference: String,
    val historicalContext: String,
    val marketPriceRangeUsd: String,
    val preciousMetalMeltValueUsd: Double,
    val imageUrl: String
)

data class AuctionRecord(
    val id: String,
    val coinName: String,
    val catalogReference: String,
    val auctionHouse: String,
    val saleName: String,
    val saleDate: String,
    val lotNumber: String,
    val grade: String,
    val gradingService: String,
    val hammerPrice: Double,
    val realizedPrice: Double,
    val currency: String,
    val provenance: String,
    val sourceUrl: String
)

data class DealerDirectoryItem(
    val id: String,
    val name: String,
    val type: String, // SPECIALIST_DEALER, AUCTION_HOUSE, OFFICIAL_MINT, MUSEUM_COLLECTION
    val city: String,
    val country: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val website: String,
    val phone: String,
    val specialization: String,
    val services: List<String>,
    val verifiedBadge: Boolean,
    val rating: Double,
    val reviewCount: Int,
    val openingHours: String
)

data class FutureCoinRelease(
    val id: String,
    val title: String,
    val issuingMint: String,
    val country: String,
    val releaseDate: String,
    val denomination: String,
    val metal: String,
    val mintageLimit: String,
    val status: String, // OFFICIALLY_ANNOUNCED, SCHEDULE_PUBLISHED, PLANNED
    val description: String,
    val sourceReference: String
)

data class AiValuationBreakdown(
    val realizedComparablePriceUsd: Double,
    val currentMarketEstimateUsd: Double,
    val retailAskingPriceUsd: Double,
    val preciousMetalMeltValueUsd: Double,
    val confidenceScore: Double,
    val methodologyNote: String
)

data class AiIdentificationResult(
    val identifiedCoinName: String,
    val civilization: String,
    val authority: String,
    val approximateDate: String,
    val metal: String,
    val estimatedGrade: String,
    val authenticityRisk: String, // LOW_AUTHENTIC_INDICATORS, VERIFICATION_RECOMMENDED, HIGH_ANOMALY
    val visualObservations: List<String>,
    val valuation: AiValuationBreakdown,
    val catalogMatches: List<String>
)
