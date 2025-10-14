package com.example.tiktok_flow.data.repository

import com.example.tiktok_flow.data.model.User
import com.example.tiktok_flow.data.model.Video
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class VideoRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getVideos(): List<Video> {
        return try {
            val snapshot = db.collection("videos")
                .orderBy("uploadedAt", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(Video::class.java)
        } catch (e: Exception) {
            // Handle exception
            emptyList()
        }
    }

    suspend fun getUser(userId: String): User? {
        return try {
            val snapshot = db.collection("users").document(userId).get().await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            // Handle exception
            null
        }
    }
}
