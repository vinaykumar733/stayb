package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val type: String, // "PG", "Hostel", "Commercial", "Residential", "Plot"
    val description: String,
    val rentOrPrice: Double,
    val location: String,
    val features: String, // Comma-separated features/amenities
    val roomsCount: Int,
    val creatorEmail: String,
    val status: String, // "verifying", "approved", "rejected", "sold"
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrlIndex: Int = 0, // Let us index preset images
    val isFeatured: Boolean = false,
    val featuredUntil: Long = 0L,
    val monthlyRevenue: Double = 0.0,
    val occupancyPercent: Int = 0
)
