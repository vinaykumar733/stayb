package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class LeadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val propertyId: Int,
    val propertyTitle: String,
    val buyerName: String,
    val buyerEmail: String,
    val buyerPhone: String,
    val message: String,
    val brokerEmail: String, // Assigned broker email
    val timestamp: Long = System.currentTimeMillis()
)
