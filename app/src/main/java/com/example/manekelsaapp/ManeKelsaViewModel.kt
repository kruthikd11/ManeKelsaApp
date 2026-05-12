package com.example.manekelsaapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.manekelsaapp.model.Worker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.*

class ManeKelsaViewModel : ViewModel() {
    private val databaseUrl = "https://manekelsa-d66da-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val db = FirebaseDatabase.getInstance(databaseUrl).getReference("worker")

    private val _workers = mutableStateOf<List<Worker>>(emptyList())
    val workers: State<List<Worker>> = _workers

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val userLat = 12.9767
    private val userLon = 77.5713

    init { fetchFromRealtimeDatabase() }

    fun registerWorker(
        name: String, phone: String, age: String, experience: String,
        rate: String, description: String, imageUrl: String, skill: String,
        location: String, availability: String, // Corrected parameter
        onSuccess: () -> Unit
    ) {
        val workerId = db.push().key ?: return
        val newWorker = Worker(
            id = workerId,
            name = name,
            phone = phone,
            age = age.trim(),
            experience = experience.trim(),
            dailyRate = rate.trim(),
            description = description,
            skill = skill,
            location = location,
            availability = availability,
            imageUrl = imageUrl.ifBlank { "https://cdn-icons-png.flaticon.com/512/149/149071.png" },
            available = true,
            rating = 5.0,
            ratingCount = 1
        )
        db.child(workerId).setValue(newWorker).addOnSuccessListener { onSuccess() }
    }

    private fun fetchFromRealtimeDatabase() {
        _isLoading.value = true
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Worker>()
                for (workerSnapshot in snapshot.children) {
                    try {
                        val worker = parseWorkerSafely(workerSnapshot)
                        worker?.let {
                            val finalDistance = if (it.latitude != 0.0) {
                                (calculateDistance(it.latitude, it.longitude) * 10).roundToInt() / 10.0
                            } else 0.0
                            list.add(it.copy(id = workerSnapshot.key ?: "", distance = finalDistance))
                        }
                    } catch (err: Exception) {
                        Log.e("FETCH", "Error: ${err.message}")
                    }
                }
                _workers.value = list
                _isLoading.value = false
            }
            override fun onCancelled(error: DatabaseError) { _isLoading.value = false }
        })
    }

    private fun parseWorkerSafely(snapshot: DataSnapshot): Worker? {
        return try {
            Worker(
                id = snapshot.key ?: "",
                name = snapshot.child("name").value?.toString() ?: "",
                phone = snapshot.child("phone").value?.toString() ?: "",
                age = snapshot.child("age").value?.toString() ?: "",
                experience = snapshot.child("experience").value?.toString() ?: "",
                dailyRate = snapshot.child("dailyRate").value?.toString() ?: "",
                skill = snapshot.child("skill").value?.toString() ?: "",
                description = snapshot.child("description").value?.toString() ?: "",
                imageUrl = snapshot.child("imageUrl").value?.toString() ?: "",
                location = snapshot.child("location").value?.toString() ?: "",
                availability = snapshot.child("availability").value?.toString() ?: "Full Time",
                available = snapshot.child("available").getValue(Boolean::class.java) ?: false,
                rating = snapshot.child("rating").value?.toString()?.toDoubleOrNull() ?: 0.0,
                ratingCount = snapshot.child("ratingCount").value?.toString()?.toIntOrNull() ?: 0,
                latitude = snapshot.child("latitude").value?.toString()?.toDoubleOrNull() ?: 0.0,
                longitude = snapshot.child("longitude").value?.toString()?.toDoubleOrNull() ?: 0.0
            )
        } catch (err: Exception) {
            Log.e("PARSE", "Error: ${err.message}")
            null
        }
    }

    private fun calculateDistance(lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - userLat)
        val dLon = Math.toRadians(lon2 - userLon)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(userLat)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        return r * (2 * atan2(sqrt(a), sqrt(1 - a)))
    }
}