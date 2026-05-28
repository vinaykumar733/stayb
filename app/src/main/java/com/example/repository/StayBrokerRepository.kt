package com.example.repository

import com.example.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StayBrokerRepository(private val dao: StayBrokerDao) {

    // Users
    val allUsers: Flow<List<UserEntity>> = dao.getAllUsersFlow()

    suspend fun getUserByEmail(email: String): UserEntity? {
        return dao.getUserByEmail(email)
    }

    suspend fun registerUser(user: UserEntity): Boolean {
        val existing = dao.getUserByEmail(user.email)
        if (existing != null) {
            return false // Already exists
        }
        dao.insertUser(user)
        return true
    }

    suspend fun deleteUser(email: String) {
        dao.deleteUserByEmail(email)
    }

    // Properties
    val approvedProperties: Flow<List<PropertyEntity>> = dao.getApprovedPropertiesFlow()
    val allProperties: Flow<List<PropertyEntity>> = dao.getAllPropertiesFlow()

    fun getPropertiesByCreator(email: String): Flow<List<PropertyEntity>> {
        return dao.getPropertiesByCreatorFlow(email)
    }

    suspend fun getPropertyById(id: Int): PropertyEntity? {
        return dao.getPropertyById(id)
    }

    suspend fun createProperty(property: PropertyEntity) {
        // When user publishes, status automatically becomes "verifying"
        dao.insertProperty(property.copy(status = "verifying"))
    }

    suspend fun updateProperty(property: PropertyEntity) {
        dao.updateProperty(property)
    }

    suspend fun updatePropertyStatus(id: Int, status: String) {
        dao.updatePropertyStatus(id, status)
    }

    suspend fun deleteProperty(id: Int) {
        dao.deletePropertyById(id)
    }

    // Leads
    val allLeads: Flow<List<LeadEntity>> = dao.getAllLeadsFlow()

    fun getLeadsForBroker(email: String): Flow<List<LeadEntity>> {
        return dao.getLeadsByBrokerFlow(email)
    }

    suspend fun submitLead(lead: LeadEntity) {
        dao.insertLead(lead)
    }
}
