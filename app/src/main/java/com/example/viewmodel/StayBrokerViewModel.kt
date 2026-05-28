package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.repository.StayBrokerRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.security.MessageDigest

sealed class Screen {
    object Splash : Screen()
    object GuideRoleSelector : Screen()
    object AuthLogin : Screen()
    object AuthRegister : Screen()
    object MainDashboard : Screen()
    data class PropertyDetails(val propertyId: Int) : Screen()
    object AddPropertyForm : Screen()
}

class StayBrokerViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    val repository = StayBrokerRepository(db.stayBrokerDao())

    // Navigation State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val backStack = mutableListOf<Screen>()

    // Current Session State
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    // UI/Filter States
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("All")
    val selectedLocationFilter = MutableStateFlow("All")

    // Database Flows
    val allUsers: StateFlow<List<UserEntity>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allLeads: StateFlow<List<LeadEntity>> = repository.allLeads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val publicApprovedProperties: StateFlow<List<PropertyEntity>> = repository.approvedProperties
        .map { list -> list.sortedWith(compareByDescending<PropertyEntity> { it.isFeatured }.thenByDescending { it.timestamp }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val adminAllProperties: StateFlow<List<PropertyEntity>> = repository.allProperties
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Lazy load or derived verifying list
    val verifyingProperties: StateFlow<List<PropertyEntity>> = repository.allProperties
        .map { list -> list.filter { it.status == "verifying" } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Seller's properties (re-calculated when currentUser changes)
    private val _myProperties = MutableStateFlow<List<PropertyEntity>>(emptyList())
    val myProperties: StateFlow<List<PropertyEntity>> = _myProperties.asStateFlow()

    // Alert and Loading Feedback
    val toastMessage = MutableSharedFlow<String>()

    init {
        // Automatically check/seed DB with default users & properties
        viewModelScope.launch {
            seedDatabase()
            // Observe current user's properties helper
            currentUser.collect { user ->
                if (user != null) {
                    repository.getPropertiesByCreator(user.email).collect { list ->
                        _myProperties.value = list
                    }
                } else {
                    _myProperties.value = emptyList()
                }
            }
        }
        
        // Auto-skip splash to entry selector
        viewModelScope.launch {
            kotlinx.coroutines.delay(1200)
            navigateTo(Screen.GuideRoleSelector)
        }
    }

    // Navigation Helpers
    fun navigateTo(screen: Screen) {
        backStack.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        if (backStack.isNotEmpty()) {
            _currentScreen.value = backStack.removeAt(backStack.size - 1)
            return true
        }
        return false
    }

    // Pre-hash helper for security
    private fun hashPassword(password: String): String {
        return try {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            digest.fold("") { str, it -> str + "%02x".format(it) }
        } catch (e: Exception) {
            password // Fallback if SHA-256 is unavailable
        }
    }

    // Seeding logic
    private suspend fun seedDatabase() {
        // Quick seed of users if empty
        val existingUsers = db.stayBrokerDao().getAllUsersFlow().firstOrNull() ?: emptyList()
        if (existingUsers.isEmpty()) {
            val seedUsers = listOf(
                UserEntity(email = "admin@staybroker.com", passwordHash = hashPassword("admin"), role = "ADMIN", fullName = "Devendra Admin", phone = "9876543210"),
                UserEntity(email = "seller@staybroker.com", passwordHash = hashPassword("seller"), role = "SELLER", fullName = "Rajesh Sharma", phone = "9821234567"),
                UserEntity(email = "broker@staybroker.com", passwordHash = hashPassword("broker"), role = "BROKER", fullName = "Vikram Broker", phone = "9988112233"),
                UserEntity(email = "buyer@staybroker.com", passwordHash = hashPassword("buyer"), role = "BUYER", fullName = "Rahul Khanna", phone = "9112233445")
            )
            for (u in seedUsers) {
                db.stayBrokerDao().insertUser(u)
            }

            // Seed sample PG business & hostel properties
            val seedProps = listOf(
                PropertyEntity(
                    title = "Stanza Living Co-Living Dublin House PG",
                    type = "PG",
                    description = "Fully furnished 48-room active PG business for immediate sale/transfer. Monthly recurring EBITDA margin of 35%. Excellent location next to Tech Park, fully managed under corporate lease. Continuous high occupancy exceeding 94%. Comes with standard kitchen setup, DG backup, commercial water supply, and complete assets list.",
                    rentOrPrice = 240000.0,
                    location = "DLF Phase 3, Gurgaon",
                    features = "WiFi, AC, Laundry, Food Court, Power Backup, Gym",
                    roomsCount = 48,
                    creatorEmail = "seller@staybroker.com",
                    status = "approved",
                    imageUrlIndex = 1,
                    isFeatured = true,
                    featuredUntil = System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000L,
                    monthlyRevenue = 85000.0,
                    occupancyPercent = 94
                ),
                PropertyEntity(
                    title = "Zolo Premium Boys & Girls Hostel Business",
                    type = "Hostel",
                    description = "Operating 60-bed modern hostel business. Fully operational under management contract. Property includes double and triple sharing fully-furnished ventilation units. Secure automated entry. Annual gross receipts of ₹24 Lakhs. Seller migrating abroad.",
                    rentOrPrice = 180000.0,
                    location = "Koramangala, Bangalore",
                    features = "High Speed WiFi, Attached Bathrooms, CCTV, Washing Area, Lounge",
                    roomsCount = 30,
                    creatorEmail = "seller@staybroker.com",
                    status = "approved",
                    imageUrlIndex = 2,
                    isFeatured = false,
                    monthlyRevenue = 55000.0,
                    occupancyPercent = 88
                ),
                PropertyEntity(
                    title = "Olive Student Co-Housing PG Business",
                    type = "Hostel",
                    description = "Prime institutional PG for students. Operating contract with local university for 100% committed beds. 5-year lock-in yield. Modern interior matching Housing.com elite listings.",
                    rentOrPrice = 320000.0,
                    location = "Madhapur, Hyderabad",
                    features = "High Speed WiFi, Smart locks, Food Service, Common Gaming Rooms",
                    roomsCount = 55,
                    creatorEmail = "broker@staybroker.com",
                    status = "verifying",
                    imageUrlIndex = 3,
                    isFeatured = true,
                    featuredUntil = System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000L,
                    monthlyRevenue = 110000.0,
                    occupancyPercent = 98
                ),
                PropertyEntity(
                    title = "Prime Sector 12 Landmark PG Plot Space",
                    type = "Plot",
                    description = "Strategic corner plot approved for up to 5-story PG building construction. Layout sketches and structural architectural plans already approved for 64 rooms with bathrooms.",
                    rentOrPrice = 1200000.0,
                    location = "Sector 12, Noida",
                    features = "Corner Plot, Approved Plans, Near Metro Station",
                    roomsCount = 64,
                    creatorEmail = "broker@staybroker.com",
                    status = "approved",
                    imageUrlIndex = 4,
                    isFeatured = false,
                    monthlyRevenue = 0.0,
                    occupancyPercent = 0
                ),
                PropertyEntity(
                    title = "Commercial PG Block Franchise Transfer",
                    type = "Commercial",
                    description = "Acquisition opportunity of an active franchised premium corporate PG block. 20 luxury rooms fully leased to blue-chip professionals.",
                    rentOrPrice = 450000.0,
                    location = "HSR Layout, Bangalore",
                    features = "Lounge, AC Units, Parking, Guard Cabin, 24x7 Support",
                    roomsCount = 20,
                    creatorEmail = "seller@staybroker.com",
                    status = "verifying",
                    imageUrlIndex = 5,
                    isFeatured = false,
                    monthlyRevenue = 150000.0,
                    occupancyPercent = 90
                )
            )
            for (p in seedProps) {
                db.stayBrokerDao().insertProperty(p)
            }

            // Seed a sample lead to keep admin UI interesting on start
            db.stayBrokerDao().insertLead(
                LeadEntity(
                    propertyId = 1,
                    propertyTitle = "Stanza Living Co-Living Dublin House PG",
                    buyerName = "Rahul Khanna",
                    buyerEmail = "buyer@staybroker.com",
                    buyerPhone = "9112233445",
                    message = "Very interested in acquiring this PG. Please share the complete P&L statement and corporate lease details.",
                    brokerEmail = "broker@staybroker.com"
                )
            )
        }
    }

    // Role Bypass / Guide Mode logic
    fun loginAsRole(roleName: String) {
        viewModelScope.launch {
            val email = when (roleName) {
                "ADMIN" -> "admin@staybroker.com"
                "SELLER" -> "seller@staybroker.com"
                "BROKER" -> "broker@staybroker.com"
                else -> "buyer@staybroker.com"
            }
            val user = repository.getUserByEmail(email)
            if (user != null) {
                _currentUser.value = user
                toastMessage.emit("Welcome to StayBroker! Logged in as $roleName")
                navigateTo(Screen.MainDashboard)
            } else {
                toastMessage.emit("User record not found for $roleName. Re-seeding database.")
                seedDatabase()
            }
        }
    }

    fun login(email: String, word: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email.trim().lowercase())
            if (user != null && user.passwordHash == hashPassword(word)) {
                _currentUser.value = user
                toastMessage.emit("Login successful!")
                navigateTo(Screen.MainDashboard)
                onComplete(true)
            } else {
                toastMessage.emit("Invalid credentials")
                onComplete(false)
            }
        }
    }

    fun signup(fullName: String, email: String, word: String, phone: String, role: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = UserEntity(
                email = email.trim().lowercase(),
                passwordHash = hashPassword(word),
                role = role,
                fullName = fullName,
                phone = phone
            )
            val success = repository.registerUser(user)
            if (success) {
                _currentUser.value = user
                toastMessage.emit("Welcome to StayBroker! Account created as $role")
                navigateTo(Screen.MainDashboard)
                onComplete(true)
            } else {
                toastMessage.emit("Email already registered.")
                onComplete(false)
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        navigateTo(Screen.GuideRoleSelector)
    }

    // Listings Logic
    fun addProperty(
        title: String,
        type: String,
        desc: String,
        price: Double,
        location: String,
        features: String,
        rooms: Int,
        monthlyRevenue: Double = 0.0,
        occupancyPercent: Int = 0
    ) {
        val user = _currentUser.value
        if (user == null) {
            viewModelScope.launch { toastMessage.emit("Authentication error. Please login.") }
            return
        }
        
        // Strict role check
        if (user.role == "BUYER") {
            viewModelScope.launch { toastMessage.emit("Permission Denied: Buyers cannot create listings.") }
            return
        }

        val prop = PropertyEntity(
            title = title,
            type = type,
            description = desc,
            rentOrPrice = price,
            location = location,
            features = features,
            roomsCount = rooms,
            creatorEmail = user.email,
            status = "verifying", // Auto verifying status!
            imageUrlIndex = (1..6).random(),
            isFeatured = false,
            featuredUntil = 0L,
            monthlyRevenue = monthlyRevenue,
            occupancyPercent = occupancyPercent
        )

        viewModelScope.launch {
            repository.createProperty(prop)
            toastMessage.emit("Listing submitted successfully! Status set to VERIFYING.")
            // Redirect back to dashboard
            _currentScreen.value = Screen.MainDashboard
        }
    }

    fun upgradePropertyToFeatured(propertyId: Int, durationDays: Int) {
        viewModelScope.launch {
            val property = repository.getPropertyById(propertyId)
            if (property != null) {
                val updated = property.copy(
                    isFeatured = true,
                    featuredUntil = System.currentTimeMillis() + durationDays * 24 * 60 * 60 * 1000L
                )
                repository.updateProperty(updated)
                toastMessage.emit("Successfully upgraded to Featured! Placed at top priority.")
            }
        }
    }

    fun toggleFeaturedByAdmin(propertyId: Int) {
        viewModelScope.launch {
            val property = repository.getPropertyById(propertyId)
            if (property != null) {
                val updated = property.copy(
                    isFeatured = !property.isFeatured,
                    featuredUntil = if (!property.isFeatured) System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L else 0L
                )
                repository.updateProperty(updated)
                toastMessage.emit("Successfully updated featured tier.")
            }
        }
    }

    fun updateListingStatusByAdmin(propertyId: Int, newStatus: String) {
        viewModelScope.launch {
            repository.updatePropertyStatus(propertyId, newStatus)
            toastMessage.emit("Property status updated to $newStatus successfully.")
        }
    }

    fun deletePropertyByAdmin(propertyId: Int) {
        viewModelScope.launch {
            repository.deleteProperty(propertyId)
            toastMessage.emit("Spam listing removed successfully.")
        }
    }

    fun deleteUserByAdmin(email: String) {
        viewModelScope.launch {
            repository.deleteUser(email)
            toastMessage.emit("User account $email removed.")
        }
    }

    fun createLead(propertyId: Int, propertyTitle: String, name: String, email: String, phone: String, message: String, brokerEmail: String = "broker@staybroker.com") {
        val lead = LeadEntity(
            propertyId = propertyId,
            propertyTitle = propertyTitle,
            buyerName = name,
            buyerEmail = email,
            buyerPhone = phone,
            message = message,
            brokerEmail = brokerEmail
        )
        viewModelScope.launch {
            repository.submitLead(lead)
            toastMessage.emit("Inquiry submitted! StayBroker experts will contact you shortly.")
        }
    }
}
