package com.example.data.repository

import com.example.data.model.CoinCatalogItem

object CoinCatalogRepository {

    val catalog: List<CoinCatalogItem> = listOf(
        CoinCatalogItem(
            id = "coin_athens_owl_tetradrachm",
            canonicalName = "Athenian Owl Tetradrachm",
            alternativeNames = "Glaux, Attica Owl, Archaic/Classical Tetradrachm",
            civilizationOrCountry = "Ancient Greece (Attica, Athens)",
            issuingAuthority = "Athenian Democratic Polis",
            periodOrDynasty = "Classical Greek Era (c. 454–404 BC)",
            mintLocation = "Athens Mint (near Agora)",
            yearRange = "c. 454–404 BC",
            denomination = "Tetradrachm (4 Drachms)",
            metal = "Silver (AR)",
            fineness = ".980 Fine Silver (Laurion Mines)",
            standardWeightGrams = 17.18,
            standardDiameterMm = 24.5,
            obverseDesc = "Head of Athena right, wearing crested Attic helmet decorated with three olive leaves and spiral palmette, earring, and necklace.",
            reverseDesc = "Owl standing right, head facing; olive sprig and crescent moon behind; ethnic AΘE in right field; all within deep incuse square.",
            obverseInscription = "None (Symbolic iconographic design)",
            reverseInscription = "ΑΘΕ (ATHE)",
            inscriptionTranslation = "Of the Athenians [ΑΘΗΝΑΙΩΝ]",
            rarityIndex = "Common to Scarce (Varies by Strike & Centering)",
            estimatedSurviving = "Over 100,000 documented hoarded specimens",
            catalogReference = "Kroll 8; SNG Copenhagen 31-40; Dewing 1591-1598",
            historicalContext = "The undisputed international trade currency of the Classical Mediterranean. Funded the construction of the Parthenon and the Delian League's trireme navy. Struck from high-purity silver extracted by slaves from the rich Laurion argentiferous lead mines.",
            marketPriceRangeUsd = "$800 - $6,500 (Depending on strike and centering)",
            preciousMetalMeltValueUsd = 16.50,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_caesar_elephant_denarius",
            canonicalName = "Julius Caesar Elephant Denarius",
            alternativeNames = "Caesar Military Mint Denarius, Crossing the Rubicon Issue",
            civilizationOrCountry = "Roman Republic",
            issuingAuthority = "Gaius Julius Caesar (Dictator in Perpetuity)",
            periodOrDynasty = "Late Roman Republic (Civil War)",
            mintLocation = "Traveling Military Mint (Cisalpine Gaul / Northern Italy)",
            yearRange = "49–48 BC",
            denomination = "Denarius",
            metal = "Silver (AR)",
            fineness = ".950 Fine Silver",
            standardWeightGrams = 3.90,
            standardDiameterMm = 18.0,
            obverseDesc = "Elephant right, trampling horned serpent (or Gallic carnyx); CAESAR in exergue.",
            reverseDesc = "Priestly implements of the Pontifex Maximus: culullus (ladle), aspergillum (sprinkler), axe (securis) crested with animal head, and apex (priest's cap).",
            obverseInscription = "CAESAR",
            reverseInscription = "None (Pontifical attributes)",
            inscriptionTranslation = "Caesar (Family cognomen, also interpreted as Punic for Elephant)",
            rarityIndex = "Scarce (R1)",
            estimatedSurviving = "15,000–25,000 specimens known",
            catalogReference = "Crawford 443/1; CRI 9; Sydenham 1006; RSC 49",
            historicalContext = "Struck immediately after Caesar crossed the Rubicon in January 49 BC to pay his veteran Legio XIII Gemina. Struck from bullion seized directly from the public treasury of Rome (Aerarium Saturni) left behind by Pompey.",
            marketPriceRangeUsd = "$1,200 - $14,000 (Choice Mint State)",
            preciousMetalMeltValueUsd = 3.75,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_gupta_samudragupta_archer",
            canonicalName = "Gupta Empire Gold Dinar of Samudragupta",
            alternativeNames = "Archer Type Suvarna, Napoleon of India Issue",
            civilizationOrCountry = "Ancient India (Gupta Empire)",
            issuingAuthority = "Maharajadhiraja Samudragupta",
            periodOrDynasty = "Classical Golden Age of India (c. 335–375 AD)",
            mintLocation = "Pataliputra (Patna) or Vidisha Mint",
            yearRange = "c. 335–375 AD",
            denomination = "Dinar / Suvarna",
            metal = "Gold (AV)",
            fineness = ".880–.920 Fine Gold",
            standardWeightGrams = 7.75,
            standardDiameterMm = 20.0,
            obverseDesc = "King nimbate standing facing left, holding bow in left hand and arrow in right hand; Garuda standard (Garudadhvaja) on left; Brahmi legend under left arm: Samudra.",
            reverseDesc = "Goddess Lakshmi seated facing on lotus throne, holding pasha (noose) and cornucopia or lotus; tamgha monogram in upper left field; Brahmi legend: Apratirathah.",
            obverseInscription = "Samudra (under arm) / Samarasata-vitata-vijayo jitaripur ajito divam jayati",
            reverseInscription = "अप्रतिरथः (Apratirathah)",
            inscriptionTranslation = "Having conquered enemies in hundreds of battles, he conquers heaven / The Unopposed Charioteer",
            rarityIndex = "Rare (R2)",
            estimatedSurviving = "Fewer than 1,200 documented specimens",
            catalogReference = "Altekar Class I, Var. A; BMC Guptas 1-5; Mitchiner 4771",
            historicalContext = "Masterpiece of Classical Indian numismatic art from the golden age of Sanskrit culture. Synthesized Hellenistic portraiture weight standards inherited through the Kushans with indigenous Gupta aesthetic spirituality and royal emblems.",
            marketPriceRangeUsd = "$3,500 - $18,000",
            preciousMetalMeltValueUsd = 620.0,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_alexander_great_tetradrachm",
            canonicalName = "Alexander III 'The Great' Silver Tetradrachm",
            alternativeNames = "Macedonian Imperial Tetradrachm, Herakles / Zeus Issue",
            civilizationOrCountry = "Kingdom of Macedonia",
            issuingAuthority = "Alexander the Great & Successor Diadochi",
            periodOrDynasty = "Hellenistic Imperial Period (336–323 BC & Posthumous)",
            mintLocation = "Babylon, Amphipolis, Damascus, or Alexandria Mint",
            yearRange = "c. 336–300 BC",
            denomination = "Tetradrachm",
            metal = "Silver (AR)",
            fineness = ".970 Fine Silver",
            standardWeightGrams = 17.20,
            standardDiameterMm = 26.0,
            obverseDesc = "Head of Herakles right, wearing lion skin headdress with paws tied beneath chin.",
            reverseDesc = "Zeus Aëtophoros seated left on high-backed throne, holding eagle on outstretched right hand and long lotus-tipped scepter in left; monogram in left field; legend right: ΑΛΕΞΑΝΔΡΟΥ.",
            obverseInscription = "None (Iconic Herakles portrait)",
            reverseInscription = "ΑΛΕΞΑΝΔΡΟΥ (ALEXANDROU)",
            inscriptionTranslation = "Of Alexander",
            rarityIndex = "Scarce to Common (depending on lifetime vs posthumous mint)",
            estimatedSurviving = "Over 150,000 documented hoarded coins",
            catalogReference = "Price 3599; Müller 1272; SNG Saroglos 245",
            historicalContext = "The currency that unified commerce from Greece to Egypt, Persia, and the Indus Valley. Herakles' features subtly mirrored Alexander's own likeness, establishing the precedent for Hellenistic ruler portraiture.",
            marketPriceRangeUsd = "$650 - $8,500 (Lifetime Babylon issues command peak premiums)",
            preciousMetalMeltValueUsd = 16.50,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_mughal_akbar_gold_mohur",
            canonicalName = "Mughal Empire Gold Mohur of Jalal-ud-din Akbar",
            alternativeNames = "Agra Kalima Round Mohur, Din-i Ilahi Period",
            civilizationOrCountry = "Mughal Empire (India)",
            issuingAuthority = "Emperor Jalal-ud-din Muhammad Akbar",
            periodOrDynasty = "Mughal Imperial Dynasty (1556–1605)",
            mintLocation = "Dar al-Khilafat Agra Mint",
            yearRange = "AH 980–1000 (c. 1572–1592 AD)",
            denomination = "Mohur (16 Silver Rupees)",
            metal = "Gold (AV)",
            fineness = ".995 Fine Gold (Pure Imperial Refinement)",
            standardWeightGrams = 10.90,
            standardDiameterMm = 21.0,
            obverseDesc = "Islamic Kalima (La ilaha illallah Muhammadur Rasulullah) within diamond quatrefoil border, with names of the four Rashidun Caliphs in margins.",
            reverseDesc = "Persian calligraphic inscription in nastaliq: Jalal al-Din Muhammad Akbar Badshah Ghazi with regnal year and mint name.",
            obverseInscription = "لا إله إلا الله محمد رسول الله (La Ilaha Illa Allah Muhammadur Rasulullah)",
            reverseInscription = "جلال الدين محمد اكبر پادشاه غازى (Jalal al-Din Muhammad Akbar Padshah Ghazi)",
            inscriptionTranslation = "There is no God but Allah, Muhammad is His Messenger / Splendor of the Faith, Muhammad Akbar, Emperor and Warrior",
            rarityIndex = "Extremely Rare (R3)",
            estimatedSurviving = "Fewer than 450 specimens known in private collections",
            catalogReference = "KM#106.1; BMC Mughal 65; Whitehead 120",
            historicalContext = "Akbar's monetary reforms created one of the purest and most standardized currencies in the pre-industrial world, establishing the Indian Rupee and Mohur as international standards that lasted centuries.",
            marketPriceRangeUsd = "$6,000 - $32,000",
            preciousMetalMeltValueUsd = 870.0,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_british_sovereign_1817_pistrucci",
            canonicalName = "1817 George III Gold Sovereign (Benedetto Pistrucci)",
            alternativeNames = "The First Modern Sovereign, Great Recoinage Benchmark",
            civilizationOrCountry = "United Kingdom",
            issuingAuthority = "King George III / Royal Mint London",
            periodOrDynasty = "Hanoverian / Regency Era",
            mintLocation = "Tower Hill Royal Mint, London",
            yearRange = "1817",
            denomination = "One Sovereign (£1)",
            metal = "Gold (AV - 22 Carat)",
            fineness = ".9167 (Crown Gold)",
            standardWeightGrams = 7.988,
            standardDiameterMm = 22.05,
            obverseDesc = "Laureate head of King George III right; date below neck; legend around: GEORGIUS III D: G: BRITANNIAR: REX F: D:.",
            reverseDesc = "Masterwork engraving of St George on horseback slaying the Dragon right, within the ribbon of the Order of the Garter inscribed with motto: HONI SOIT QUI MAL Y PENSE; Pistrucci in exergue.",
            obverseInscription = "GEORGIUS III D: G: BRITANNIAR: REX F: D:",
            reverseInscription = "HONI SOIT QUI MAL Y PENSE",
            inscriptionTranslation = "George III by the Grace of God King of the Britains, Defender of the Faith / Evil to him who evil thinks",
            rarityIndex = "Very Rare (R2 in high grade)",
            estimatedSurviving = "Around 3,500 survivors from an original 3.2M mintage (most melted)",
            catalogReference = "Spink 3785; Marsh 1; KM#674",
            historicalContext = "Instituted following the Napoleonic Wars under the Great Recoinage Act of 1816, re-establishing Britain on the Gold Standard. Master engraver Benedetto Pistrucci's classical St George design remains on British sovereigns to this day.",
            marketPriceRangeUsd = "$2,200 - $28,000 (Mint State 64+)",
            preciousMetalMeltValueUsd = 585.0,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_us_1907_saint_gaudens_high_relief",
            canonicalName = "1907 Saint-Gaudens Double Eagle (High Relief, Wire Rim)",
            alternativeNames = "MCMVII High Relief Twenty Dollar, Teddy Roosevelt Masterpiece",
            civilizationOrCountry = "United States of America",
            issuingAuthority = "United States Mint / President Theodore Roosevelt",
            periodOrDynasty = "American Numismatic Renaissance",
            mintLocation = "Philadelphia Mint",
            yearRange = "1907",
            denomination = "Twenty Dollars ($20)",
            metal = "Gold (AV - 90% Gold, 10% Copper)",
            fineness = ".900 Fine Gold",
            standardWeightGrams = 33.436,
            standardDiameterMm = 34.0,
            obverseDesc = "Full-length figure of Liberty holding a torch in right hand and an olive branch in left, stepping forward with Capitol dome visible in background; 46 stars around; MCMVII in Roman numerals.",
            reverseDesc = "Eagle soaring left above the rays of a rising sun; UNITED STATES OF AMERICA TWENTY DOLLARS above.",
            obverseInscription = "LIBERTY / MCMVII",
            reverseInscription = "UNITED STATES OF AMERICA / TWENTY DOLLARS",
            inscriptionTranslation = "Liberty / 1907 / United States of America / Twenty Dollars",
            rarityIndex = "Rare (R3)",
            estimatedSurviving = "8,000–10,000 of 11,250 originally struck on medal hydraulic presses",
            catalogReference = "Breen 6701; Akers 80; PCGS 9135",
            historicalContext = "Commissioned personally by President Theodore Roosevelt, who admired ancient Greek high-relief coins and wanted American coinage to possess monumental artistic nobility. Required up to 5 strikes on a medal press.",
            marketPriceRangeUsd = "$14,000 - $95,000 (Gem MS65+)",
            preciousMetalMeltValueUsd = 2400.0,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        ),
        CoinCatalogItem(
            id = "coin_byzantine_justinian_solidus",
            canonicalName = "Byzantine Gold Solidus of Justinian I",
            alternativeNames = "Nomisma, Emperor of the Hagia Sophia",
            civilizationOrCountry = "Byzantine (Eastern Roman) Empire",
            issuingAuthority = "Flavius Petrus Sabbatius Iustinianus (Justinian the Great)",
            periodOrDynasty = "Justinianic Dynasty (527–565 AD)",
            mintLocation = "Constantinople Mint (Officina E)",
            yearRange = "527–565 AD",
            denomination = "Solidus (Nomisma)",
            metal = "Gold (AV)",
            fineness = ".980 Fine Gold",
            standardWeightGrams = 4.48,
            standardDiameterMm = 21.0,
            obverseDesc = "Helmeted and cuirassed bust of Justinian facing, holding globus cruciger and shield.",
            reverseDesc = "Angel (Victory) standing facing, holding long cross and globus cruciger; star in right field; CONOB in exergue.",
            obverseInscription = "D N IVSTINIANVS P P AVG",
            reverseInscription = "VICTORIA AVGGG E / CONOB",
            inscriptionTranslation = "Our Lord Justinian Perpetual Augustus / Victory of the Augusti / Constantinople Standard Gold",
            rarityIndex = "Scarce (R1)",
            estimatedSurviving = "Approximately 8,000 known specimens",
            catalogReference = "DOC 9e; MIBE 7; Sear 140",
            historicalContext = "Struck during the reconquest of Italy, North Africa, and the codification of Roman Civil Law (Corpus Juris Civilis), as well as the construction of the Hagia Sophia in Constantinople.",
            marketPriceRangeUsd = "$750 - $3,800",
            preciousMetalMeltValueUsd = 350.0,
            imageUrl = "file:///public/assets/aistudio/Paste_November_03__2026_-_1_08PM_bb4e43a5"
        )
    )

    fun search(query: String, metal: String? = null, era: String? = null): List<CoinCatalogItem> {
        val q = query.trim().lowercase()
        return catalog.filter { item ->
            val matchesQuery = q.isEmpty() ||
                item.canonicalName.lowercase().contains(q) ||
                item.civilizationOrCountry.lowercase().contains(q) ||
                item.issuingAuthority.lowercase().contains(q) ||
                item.catalogReference.lowercase().contains(q) ||
                item.metal.lowercase().contains(q) ||
                item.obverseInscription.lowercase().contains(q) ||
                item.reverseInscription.lowercase().contains(q)

            val matchesMetal = metal == null || metal.isEmpty() || metal == "All" || item.metal.contains(metal, ignoreCase = true)
            val matchesEra = era == null || era.isEmpty() || era == "All" || item.periodOrDynasty.contains(era, ignoreCase = true) || item.civilizationOrCountry.contains(era, ignoreCase = true)

            matchesQuery && matchesMetal && matchesEra
        }
    }

    fun getById(id: String): CoinCatalogItem? {
        return catalog.find { it.id == id }
    }
}
