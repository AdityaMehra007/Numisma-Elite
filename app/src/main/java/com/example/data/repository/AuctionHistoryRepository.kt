package com.example.data.repository

import com.example.data.model.AuctionRecord

object AuctionHistoryRepository {

    val realizedRecords: List<AuctionRecord> = listOf(
        AuctionRecord(
            id = "auc_cng_121_athens_owl",
            coinName = "Athenian Owl Tetradrachm (Choice AU)",
            catalogReference = "Kroll 8; Dewing 1591",
            auctionHouse = "Classical Numismatic Group (CNG)",
            saleName = "Triton XXVI Annual Premier Auction",
            saleDate = "January 10, 2024",
            lotNumber = "Lot 142",
            grade = "Choice AU, Strike: 5/5, Surface: 4/5",
            gradingService = "NGC Ancients",
            hammerPrice = 2800.0,
            realizedPrice = 3360.0,
            currency = "USD",
            provenance = "Ex Dr. Patrick Tan Collection, acquired Spink London 1988",
            sourceUrl = "https://www.cngcoins.com/Coin.aspx?CoinID=142"
        ),
        AuctionRecord(
            id = "auc_heritage_caesar_elephant",
            coinName = "Julius Caesar Elephant Denarius (Mint State)",
            catalogReference = "Crawford 443/1; CRI 9",
            auctionHouse = "Heritage Auctions",
            saleName = "NYINC World & Ancient Platinum Night",
            saleDate = "January 14, 2024",
            lotNumber = "Lot 30045",
            grade = "MS, Strike: 5/5, Surface: 5/5",
            gradingService = "NGC Ancients",
            hammerPrice = 11000.0,
            realizedPrice = 13200.0,
            currency = "USD",
            provenance = "Ex Hunt Collection (Sotheby's 1990); Numismatic Fine Arts XXV (1990)",
            sourceUrl = "https://coins.ha.com/itm/ancients/roman-republic/lot-30045"
        ),
        AuctionRecord(
            id = "auc_spink_samudragupta_archer",
            coinName = "Gupta Empire Samudragupta Gold Dinar (Archer Type)",
            catalogReference = "Altekar Class I; BMC 1",
            auctionHouse = "Spink & Son London",
            saleName = "Ancient & Islamic Coinage",
            saleDate = "March 22, 2024",
            lotNumber = "Lot 88",
            grade = "Good Very Fine / About Extremely Fine",
            gradingService = "Raw (Spink Authenticated)",
            hammerPrice = 7500.0,
            realizedPrice = 9000.0,
            currency = "GBP",
            provenance = "Ex Sir Richard Burn Collection, prior to 1945",
            sourceUrl = "https://www.spink.com/lot/88"
        ),
        AuctionRecord(
            id = "auc_stacks_1907_saint_gaudens",
            coinName = "1907 Saint-Gaudens Double Eagle (High Relief, Wire Rim)",
            catalogReference = "PCGS 9135; Breen 6701",
            auctionHouse = "Stack's Bowers Galleries",
            saleName = "The Rarities Night Auction",
            saleDate = "August 15, 2024",
            lotNumber = "Lot 4180",
            grade = "MS65+",
            gradingService = "PCGS with CAC Green Bean",
            hammerPrice = 42500.0,
            realizedPrice = 51000.0,
            currency = "USD",
            provenance = "The D. Brent Pogue Collection; Heritage 2012",
            sourceUrl = "https://stacksbowers.com/lot-4180"
        ),
        AuctionRecord(
            id = "auc_kuenker_akbar_mohur",
            coinName = "Mughal Empire Akbar Gold Mohur (Agra Mint)",
            catalogReference = "KM#106.1; Whitehead 120",
            auctionHouse = "Fritz Rudolf Künker GmbH",
            saleName = "Auction 395 - World Gold Rarities",
            saleDate = "October 24, 2024",
            lotNumber = "Lot 1120",
            grade = "Extremely Fine (Vorzüglich)",
            gradingService = "NGC MS62",
            hammerPrice = 14500.0,
            realizedPrice = 17400.0,
            currency = "EUR",
            provenance = "Private Swiss Collection, gathered between 1960 and 1980",
            sourceUrl = "https://www.kuenker.de/en/auktionen/stueck/1120"
        )
    )

    fun getRecordsForCoin(coinNameOrRef: String): List<AuctionRecord> {
        val query = coinNameOrRef.lowercase()
        return realizedRecords.filter {
            it.coinName.lowercase().contains(query) ||
            it.catalogReference.lowercase().contains(query) ||
            query.contains(it.catalogReference.lowercase())
        }
    }
}
