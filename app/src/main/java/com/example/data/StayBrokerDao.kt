package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StayBrokerDao {

    // --- USER QUERIES ---
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsersFlow(): Flow<List<UserEntity>>

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)


    // --- PROPERTY QUERIES ---
    @Query("SELECT * FROM properties WHERE status = 'approved' ORDER BY timestamp DESC")
    fun getApprovedPropertiesFlow(): Flow<List<PropertyEntity>>

    @Query("SELECT * FROM properties ORDER BY timestamp DESC")
    fun getAllPropertiesFlow(): Flow<List<PropertyEntity>>

    @Query("SELECT * FROM properties WHERE creatorEmail = :email ORDER BY timestamp DESC")
    fun getPropertiesByCreatorFlow(email: String): Flow<List<PropertyEntity>>

    @Query("SELECT * FROM properties WHERE id = :id LIMIT 1")
    suspend fun getPropertyById(id: Int): PropertyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: PropertyEntity)

    @Update
    suspend fun updateProperty(property: PropertyEntity)

    @Query("UPDATE properties SET status = :status WHERE id = :id")
    suspend fun updatePropertyStatus(id: Int, status: String)

    @Query("DELETE FROM properties WHERE id = :id")
    suspend fun deletePropertyById(id: Int)


    // --- LEAD QUERIES ---
    @Query("SELECT * FROM leads ORDER BY timestamp DESC")
    fun getAllLeadsFlow(): Flow<List<LeadEntity>>

    @Query("SELECT * FROM leads WHERE brokerEmail = :brokerEmail ORDER BY timestamp DESC")
    fun getLeadsByBrokerFlow(brokerEmail: String): Flow<List<LeadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: LeadEntity)
}
