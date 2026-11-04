package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.MarketplaceListing
import com.example.data.model.Specimen
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.UUID

class NumismaRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun addSpecimen_authenticated_createsDocument() = runBlocking {
        signInTestUser(ALICE_EMAIL)
        val repository = NumismaRepository(firestore)

        val specimen = Specimen(
            coinName = "Athenian Owl Tetradrachm",
            grade = "Ch XF",
            gradingService = "NGC",
            metal = "Silver",
            era = "Classical Greece",
            measuredWeightGrams = 17.18
        )

        val result = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.addSpecimen(specimen)
        }
        assertTrue("Specimen creation must succeed", result.isSuccess)
        val id = result.getOrThrow()
        assertTrue("Generated ID must not be empty", id.isNotEmpty())
    }

    @Test
    fun observeSpecimens_authenticated_returnsUserSpecimens() = runBlocking {
        signInTestUser(ALICE_EMAIL)
        val repository = NumismaRepository(firestore)

        val coinName = "Aureus Augustus " + UUID.randomUUID().toString().take(6)
        val specimen = Specimen(
            coinName = coinName,
            grade = "MS64",
            gradingService = "NGC",
            metal = "Gold"
        )
        val createResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.addSpecimen(specimen)
        }
        val createdId = createResult.getOrThrow()

        val list = withTimeout(FLOW_TIMEOUT_MS) {
            repository.observeSpecimens().first { items ->
                items.any { it.id == createdId }
            }
        }
        assertTrue(list.any { it.id == createdId && it.coinName == coinName })
    }

    @Test
    fun observeSpecimens_unauthenticated_failsWithPermissionDenied() = runBlocking {
        auth.signOut()
        val repository = NumismaRepository(firestore)

        try {
            withTimeout(FLOW_TIMEOUT_MS) {
                repository.observeSpecimens().first()
            }
            fail("Expected exception when unauthenticated")
        } catch (e: Exception) {
            // Flow will throw either IllegalStateException from requireUserId() or CancellationException wrapping PERMISSION_DENIED
            val isExpected = e is IllegalStateException || e.unwrapFirestoreException()?.code == FirebaseFirestoreException.Code.PERMISSION_DENIED
            assertTrue("Expected auth error or PERMISSION_DENIED, got: $e", isExpected)
        }
    }

    @Test
    fun createListing_authenticated_succeeds() = runBlocking {
        signInTestUser(ALICE_EMAIL)
        val repository = NumismaRepository(firestore)

        val listing = MarketplaceListing(
            sellerName = "Alice Rare Coins",
            coinTitle = "Kushan Kanishka I Gold Dinar",
            country = "Ancient India / Bactria",
            era = "Kushan Dynasty",
            metal = "Gold",
            grade = "Choice VF",
            price = 2400.0,
            currency = "USD",
            listingType = "FIXED_PRICE",
            status = "ACTIVE",
            description = "King standing sacrificing at altar."
        )

        val result = withTimeout(DEFAULT_TIMEOUT_MS) {
            repository.createListing(listing)
        }
        assertTrue("Listing creation must succeed", result.isSuccess)
        assertNotNull(result.getOrNull())
    }

    private companion object {
        const val ALICE_EMAIL = "alice_collector@numisma.com"
        const val BOB_EMAIL = "bob_collector@numisma.com"
        const val DEFAULT_TIMEOUT_MS = 5000L
        const val FLOW_TIMEOUT_MS = 4000L
    }
}
