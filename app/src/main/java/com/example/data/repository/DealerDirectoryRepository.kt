package com.example.data.repository

import com.example.data.model.DealerDirectoryItem

object DealerDirectoryRepository {

    val directory: List<DealerDirectoryItem> = listOf(
        DealerDirectoryItem(
            id = "dealer_spink_london",
            name = "Spink & Son Ltd",
            type = "AUCTION_HOUSE",
            city = "London",
            country = "United Kingdom",
            address = "67-69 Southampton Row, Bloomsbury, London WC1B 4ET",
            latitude = 51.5194,
            longitude = -0.1219,
            website = "https://www.spink.com",
            phone = "+44 20 7563 4000",
            specialization = "Ancient Greek & Roman, British Imperial, World Gold Coins, Banknotes & Medals",
            services = listOf("Auction Consignment", "Direct Purchase", "Expert Appraisals", "Private Treaty Sales"),
            verifiedBadge = true,
            rating = 4.9,
            reviewCount = 1420,
            openingHours = "Mon–Fri 09:30–17:30 (By Appointment Recommended)"
        ),
        DealerDirectoryItem(
            id = "dealer_heritage_nyc",
            name = "Heritage Auctions New York",
            type = "AUCTION_HOUSE",
            city = "New York",
            country = "United States",
            address = "445 Park Avenue (at 57th Street), New York, NY 10022",
            latitude = 40.7618,
            longitude = -73.9712,
            website = "https://www.ha.com",
            phone = "+1 212-486-3500",
            specialization = "US Rare Coins, World & Ancient Gold, Currency, Modern Bullion",
            services = listOf("Public Auctions", "Consignment", "Insurance Appraisals", "NGC/PCGS Submission Center"),
            verifiedBadge = true,
            rating = 4.8,
            reviewCount = 3850,
            openingHours = "Mon–Fri 09:00–17:00"
        ),
        DealerDirectoryItem(
            id = "dealer_cng_lancaster",
            name = "Classical Numismatic Group (CNG)",
            type = "SPECIALIST_DEALER",
            city = "Lancaster",
            country = "United States / London",
            address = "P.O. Box 479, Lancaster, PA 17608 / London Office: 20 Bloomsbury St",
            latitude = 40.0379,
            longitude = -76.3055,
            website = "https://www.cngcoins.com",
            phone = "+1 717-390-9194",
            specialization = "Archaic & Classical Greek, Roman Republic & Imperial, Byzantine, Celtic",
            services = listOf("Electronic Auctions", "Triton Premier Auctions", "Scholarly Attribution", "Direct Sales"),
            verifiedBadge = true,
            rating = 4.95,
            reviewCount = 980,
            openingHours = "Mon–Fri 09:00–17:00"
        ),
        DealerDirectoryItem(
            id = "dealer_kuenker_munich",
            name = "Fritz Rudolf Künker GmbH & Co. KG",
            type = "AUCTION_HOUSE",
            city = "Munich",
            country = "Germany",
            address = "Maximilianstraße 34, 80539 München",
            latitude = 48.1391,
            longitude = 11.5833,
            website = "https://www.kuenker.de",
            phone = "+49 89 24216990",
            specialization = "Holy Roman Empire, German States, European Gold Rarities, Classical Ancients",
            services = listOf("Premier Floor Auctions", "Direct Purchase", "Collection Consulting"),
            verifiedBadge = true,
            rating = 4.9,
            reviewCount = 1100,
            openingHours = "Mon–Fri 09:00–18:00"
        ),
        DealerDirectoryItem(
            id = "dealer_todywalla_mumbai",
            name = "Todywalla Auctions",
            type = "AUCTION_HOUSE",
            city = "Mumbai",
            country = "India",
            address = "608, Khatau Mansion, Alibhai Premji Marg, Grant Road East, Mumbai 400007",
            latitude = 18.9625,
            longitude = 72.8166,
            website = "https://www.todywallaauctions.com",
            phone = "+91 22 2381 2222",
            specialization = "Ancient Indian Punch-Marked, Kushan, Gupta, Mughal, Princely States, British India",
            services = listOf("Licensed Numismatic Auctions", "Cataloging", "Numismatic Valuation"),
            verifiedBadge = true,
            rating = 4.85,
            reviewCount = 890,
            openingHours = "Mon–Sat 10:30–18:30"
        ),
        DealerDirectoryItem(
            id = "dealer_british_museum",
            name = "British Museum Department of Coins and Medals",
            type = "MUSEUM_COLLECTION",
            city = "London",
            country = "United Kingdom",
            address = "Great Russell Street, London WC1B 3DG",
            latitude = 51.5194,
            longitude = -0.1269,
            website = "https://www.britishmuseum.org/collection/coins-and-medals",
            phone = "+44 20 7323 8000",
            specialization = "One of the world's greatest numismatic collections (over 1,000,000 objects from 7th century BC to modern)",
            services = listOf("Public Exhibition (Room 68)", "Scholarly Study Room Visits", "Research Publications"),
            verifiedBadge = true,
            rating = 5.0,
            reviewCount = 6500,
            openingHours = "Daily 10:00–17:00 (Fridays until 20:30)"
        ),
        DealerDirectoryItem(
            id = "dealer_royal_mint_llantrisant",
            name = "The Royal Mint",
            type = "OFFICIAL_MINT",
            city = "Llantrisant / London",
            country = "United Kingdom",
            address = "Llantrisant, Pontyclun CF72 8YT",
            latitude = 51.5472,
            longitude = -3.3764,
            website = "https://www.royalmint.com",
            phone = "+44 345 608 8300",
            specialization = "Official British Sovereign, Britannia, Commemorative Proof Sets, Bullion Services",
            services = listOf("Direct Mint Sales", "The Royal Mint Experience", "Bullion Storage / Vault"),
            verifiedBadge = true,
            rating = 4.8,
            reviewCount = 4200,
            openingHours = "Mon–Sun 09:30–16:30"
        ),
        DealerDirectoryItem(
            id = "dealer_ans_newyork",
            name = "American Numismatic Society (ANS)",
            type = "MUSEUM_COLLECTION",
            city = "New York",
            country = "United States",
            address = "75 Varick Street, 11th Floor, New York, NY 10013",
            latitude = 40.7208,
            longitude = -74.0055,
            website = "https://numismatics.org",
            phone = "+1 212-571-4470",
            specialization = "Numismatic Research, MANTIS Global Coin Database, Over 800,000 specimens",
            services = listOf("Research Library Access", "Scholarly Fellowships", "Digital Numismatic Seminars"),
            verifiedBadge = true,
            rating = 4.95,
            reviewCount = 620,
            openingHours = "Tue–Fri 10:00–16:00 (Appointment Required)"
        ),
        DealerDirectoryItem(
            id = "dealer_tokyo_ginza_coins",
            name = "Ginza Coins (銀座コイン)",
            type = "SPECIALIST_DEALER",
            city = "Tokyo",
            country = "Japan",
            address = "5-1 Ginza Five B1F, Chuo City, Tokyo 104-0061",
            latitude = 35.6720,
            longitude = 139.7640,
            website = "https://ginzacoin.co.jp",
            phone = "+81 3-3573-1960",
            specialization = "Japanese Ancient Koban, Meiji Modern, East Asian Rarities, World Coins",
            services = listOf("JNDA Certified Dealer", "Appraisal", "Buying & Consignment", "Ginza Coin Auction"),
            verifiedBadge = true,
            rating = 4.8,
            reviewCount = 740,
            openingHours = "Mon–Sat 10:00–18:30"
        ),
        DealerDirectoryItem(
            id = "dealer_singapore_mint",
            name = "The Singapore Mint",
            type = "OFFICIAL_MINT",
            city = "Singapore",
            country = "Singapore",
            address = "20 Teban Gardens Crescent, Singapore 608928",
            latitude = 1.3200,
            longitude = 103.7420,
            website = "https://www.singaporemint.com",
            phone = "+65 6566 2626",
            specialization = "ASEAN Commemoratives, Lunar Zodiac Issues, Gold Bullion Coins",
            services = listOf("Official Mint Distributor", "Custom Commemoratives", "Bullion Trading"),
            verifiedBadge = true,
            rating = 4.75,
            reviewCount = 920,
            openingHours = "Mon–Fri 08:30–17:30"
        )
    )

    fun search(query: String, city: String? = null, type: String? = null): List<DealerDirectoryItem> {
        val q = query.trim().lowercase()
        return directory.filter { item ->
            val matchesQuery = q.isEmpty() ||
                item.name.lowercase().contains(q) ||
                item.city.lowercase().contains(q) ||
                item.country.lowercase().contains(q) ||
                item.specialization.lowercase().contains(q)

            val matchesCity = city == null || city == "All" || item.city.equals(city, ignoreCase = true)
            val matchesType = type == null || type == "All" || item.type.equals(type, ignoreCase = true)

            matchesQuery && matchesCity && matchesType
        }
    }
}
