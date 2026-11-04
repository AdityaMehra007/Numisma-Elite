package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.DiscussionThread
import com.example.data.model.MarketplaceListing
import com.example.data.model.Specimen
import com.example.data.model.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class NumismaRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be authenticated before accessing Numisma Vault.")
    }

    // USER PROFILE
    fun observeUserProfile(userId: String): Flow<UserProfile?> {
        val path = "users/$userId"
        return db.collection("users").document(userId)
            .snapshots()
            .map { it.toObject(UserProfile::class.java) }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.GET, path)
                throw e
            }
    }

    suspend fun saveUserProfile(profile: UserProfile): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        return try {
            val payload = profile.copy(userId = uid, updatedAt = null)
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    // SPECIMENS (PERSONAL DIGITAL COIN VAULT)
    fun observeSpecimens(): Flow<List<Specimen>> {
        val uid = requireUserId()
        val path = "specimens"
        return db.collection("specimens")
            .whereEqualTo("userId", uid)
            .snapshots()
            .map { it.toObjects(Specimen::class.java) }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.LIST, path)
                throw e
            }
    }

    suspend fun addSpecimen(specimen: Specimen): Result<String> {
        val uid = requireUserId()
        val collection = db.collection("specimens")
        return try {
            val docRef = collection.document()
            val payload = specimen.copy(
                id = docRef.id,
                userId = uid,
                createdAt = null,
                updatedAt = null
            )
            docRef.set(payload).await()
            Result.success(docRef.id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, collection.path)
            Result.failure(e)
        }
    }

    suspend fun updateSpecimen(specimen: Specimen): Result<Unit> {
        val uid = requireUserId()
        require(specimen.id.isNotBlank()) { "Specimen ID cannot be blank" }
        val docRef = db.collection("specimens").document(specimen.id)
        return try {
            val payload = specimen.copy(userId = uid, updatedAt = null)
            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun deleteSpecimen(specimenId: String): Result<Unit> {
        require(specimenId.isNotBlank())
        val docRef = db.collection("specimens").document(specimenId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // MARKETPLACE LISTINGS
    fun observeListings(): Flow<List<MarketplaceListing>> {
        val path = "listings"
        return db.collection("listings")
            .snapshots()
            .map { it.toObjects(MarketplaceListing::class.java) }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.LIST, path)
                throw e
            }
    }

    suspend fun createListing(listing: MarketplaceListing): Result<String> {
        val uid = requireUserId()
        val collection = db.collection("listings")
        return try {
            val docRef = collection.document()
            val payload = listing.copy(
                id = docRef.id,
                userId = uid,
                createdAt = null,
                updatedAt = null
            )
            docRef.set(payload).await()
            Result.success(docRef.id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, collection.path)
            Result.failure(e)
        }
    }

    suspend fun placeBid(listingId: String, newBid: Double): Result<Unit> {
        requireUserId()
        val docRef = db.collection("listings").document(listingId)
        return try {
            val snapshot = docRef.get().await()
            val existingListing = snapshot.toObject(MarketplaceListing::class.java)
                ?: return Result.failure(IllegalStateException("Listing not found"))

            val currentBidCount = existingListing.bidCount ?: 0
            docRef.update(
                mapOf(
                    "currentBid" to newBid,
                    "bidCount" to currentBidCount + 1,
                    "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    // DISCUSSIONS
    fun observeDiscussions(): Flow<List<DiscussionThread>> {
        val path = "discussions"
        return db.collection("discussions")
            .snapshots()
            .map { it.toObjects(DiscussionThread::class.java) }
            .catch { e ->
                if (e is Exception) handleFirestoreError(e, OperationType.LIST, path)
                throw e
            }
    }

    suspend fun createDiscussion(thread: DiscussionThread): Result<String> {
        val uid = requireUserId()
        val collection = db.collection("discussions")
        return try {
            val docRef = collection.document()
            val payload = thread.copy(
                id = docRef.id,
                userId = uid,
                createdAt = null,
                updatedAt = null
            )
            docRef.set(payload).await()
            Result.success(docRef.id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, collection.path)
            Result.failure(e)
        }
    }
}
