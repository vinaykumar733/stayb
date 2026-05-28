package com.example.ui

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.LeadEntity
import com.example.data.PropertyEntity
import com.example.viewmodel.Screen
import com.example.viewmodel.StayBrokerViewModel
import kotlinx.coroutines.flow.collectLatest

// StayBroker Elegant Theme Palette
val StayBrokerPrimary = Color(0xFF0D47A1)   // Dark Blue
val StayBrokerSecondary = Color(0xFF1E88E5) // Accent Blue
val StayBrokerLightBackground = Color(0xFFF5F7FA) // Subtle Gray
val StayBrokerDarkBackground = Color(0xFF0A192F)  // Premium Deep Space Blue
val StatusVerifyingColor = Color(0xFFFFB300) // Soft Yellow
val StatusApprovedColor = Color(0xFF43A047)  // Safe Green
val StatusRejectedColor = Color(0xFFE53935)  // Red
val StatusSoldColor = Color(0xFF757575)      // Slate Gray

@Composable
fun StayBrokerLogo(modifier: Modifier = Modifier, tint: Color = Color.White) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val lX = w * 0.22f
        val rX = w * 0.78f
        val tY = h * 0.22f
        val bY = h * 0.78f
        val corner = w * 0.17f

        // Outer shape line path
        val outerPath = Path().apply {
            moveTo(lX + corner, tY)
            lineTo(rX, tY)
            lineTo(rX, bY - corner)
            quadraticTo(rX, bY, rX - corner, bY)
            lineTo(lX, bY)
            lineTo(lX, tY + corner)
            quadraticTo(lX, tY, lX + corner, tY)
            close()
        }

        drawPath(
            path = outerPath,
            color = tint,
            style = Stroke(
                width = w * 0.04f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Chevron 1 (Top)
        // Left part: From (24,42) L (58,32) L (58,41) L (24,51)
        val l1 = Path().apply {
            moveTo(w * 0.222f, h * 0.389f)
            lineTo(w * 0.537f, h * 0.296f)
            lineTo(w * 0.537f, h * 0.38f)
            lineTo(w * 0.222f, h * 0.472f)
            close()
        }
        drawPath(path = l1, color = tint)

        // Right part: From (58,36) L (84,44) L (84,52) L (58,44)
        val r1 = Path().apply {
            moveTo(w * 0.537f, h * 0.333f)
            lineTo(w * 0.778f, h * 0.407f)
            lineTo(w * 0.778f, h * 0.481f)
            lineTo(w * 0.537f, h * 0.407f)
            close()
        }
        drawPath(path = r1, color = tint)

        // Chevron 2 (Middle)
        // Left part: From (24,54) L (58,44) L (58,53) L (24,63)
        val l2 = Path().apply {
            moveTo(w * 0.222f, h * 0.5f)
            lineTo(w * 0.537f, h * 0.407f)
            lineTo(w * 0.537f, h * 0.491f)
            lineTo(w * 0.222f, h * 0.583f)
            close()
        }
        drawPath(path = l2, color = tint)

        // Right part: From (58,48) L (84,56) L (84,64) L (58,56)
        val r2 = Path().apply {
            moveTo(w * 0.537f, h * 0.444f)
            lineTo(w * 0.778f, h * 0.519f)
            lineTo(w * 0.778f, h * 0.593f)
            lineTo(w * 0.537f, h * 0.519f)
            close()
        }
        drawPath(path = r2, color = tint)

        // Chevron 3 (Bottom)
        // Left part: From (24,66) L (58,56) L (58,65) L (24,75)
        val l3 = Path().apply {
            moveTo(w * 0.222f, h * 0.611f)
            lineTo(w * 0.537f, h * 0.519f)
            lineTo(w * 0.537f, h * 0.602f)
            lineTo(w * 0.222f, h * 0.694f)
            close()
        }
        drawPath(path = l3, color = tint)

        // Right part: From (58,60) L (84,68) L (84,76) L (58,68)
        val r3 = Path().apply {
            moveTo(w * 0.537f, h * 0.556f)
            lineTo(w * 0.778f, h * 0.63f)
            lineTo(w * 0.778f, h * 0.704f)
            lineTo(w * 0.537f, h * 0.63f)
            close()
        }
        drawPath(path = r3, color = tint)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StayBrokerApp(viewModel: StayBrokerViewModel) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val toastMessage = viewModel.toastMessage

    // Observe toast messages from viewmodel and show them
    LaunchedEffect(key1 = Unit) {
        toastMessage.collectLatest { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        containerColor = StayBrokerLightBackground,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Splash -> SplashScreen()
                is Screen.GuideRoleSelector -> GuideRoleSelectorScreen(viewModel)
                is Screen.AuthLogin -> LoginScreen(viewModel)
                is Screen.AuthRegister -> RegisterScreen(viewModel)
                is Screen.MainDashboard -> MainDashboardContainer(viewModel)
                is Screen.AddPropertyForm -> AddPropertyFormScreen(viewModel)
                is Screen.PropertyDetails -> {
                    PropertyDetailsScreen(propertyId = screen.propertyId, viewModel = viewModel) {
                        viewModel.navigateBack()
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 1. SPLASH SCREEN
// -------------------------------------------------------------
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(StayBrokerDarkBackground, StayBrokerPrimary)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StayBrokerLogo(
                tint = Color.White,
                modifier = Modifier
                    .size(108.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.15f),
                            radius = size.width / 1.5f
                        )
                    }
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "STAYBROKER",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 4.sp
            )
            Text(
                text = "PG & Hostel Marketplace Platform",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

// -------------------------------------------------------------
// 2. GUIDE / ROLE BYPASS SELECTOR
// -------------------------------------------------------------
@Composable
fun GuideRoleSelectorScreen(viewModel: StayBrokerViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(StayBrokerLightBackground)
    ) {
        // Top Background design accent
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(StayBrokerPrimary, StayBrokerSecondary)
                    ),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    StayBrokerLogo(
                        tint = Color.White,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "StayBroker",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 1.sp,
                            lineHeight = 36.sp
                        )
                        Text(
                            text = "Co-Living & PG Marketplace",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(35.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Interactive Demo Walkthrough",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = StayBrokerPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Switch profiles with 1-tap instantly to test the complete Buyer, Seller, active Broker, and Admin verification workflow loop.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Text(
                    text = "SELECT AN APPLICATION ROLE TO ENTER",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Role items using core robust icons
            val roles: List<Triple<String, String, ImageVector>> = listOf(
                Triple("BUYER", "Browse certified PG & Hostel businesses, search by filters, and request broker leads.", Icons.Default.Search),
                Triple("SELLER", "Create verified PG listings (sent to verifying queue), monitor active status dashboard.", Icons.Default.Add),
                Triple("BROKER", "Submit Listings, monitor and receive assigned Buyer Leads automatically.", Icons.Default.Email),
                Triple("ADMIN", "Access Admin Dashboard. Approve/Reject properties from the queue, delete spam users, and view leads.", Icons.Default.Lock)
            )

            items(roles) { (roleCode, desc, icon) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { viewModel.loginAsRole(roleCode) }
                        .testTag("role_select_${roleCode.lowercase()}"),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = StayBrokerPrimary.copy(alpha = 0.1f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = StayBrokerPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = roleCode,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = StayBrokerPrimary
                            )
                            Text(
                                text = desc,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "OR USE CUSTOM SECURE PROFILES",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(Screen.AuthLogin) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("custom_login_button"),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = StayBrokerPrimary)
                    ) {
                        Text("Log In")
                    }

                    Button(
                        onClick = { viewModel.navigateTo(Screen.AuthRegister) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("custom_register_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary)
                    ) {
                        Text("Create Profile")
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

// -------------------------------------------------------------
// 3. SECURE AUTH LOGIN SCREEN
// -------------------------------------------------------------
@Composable
fun LoginScreen(viewModel: StayBrokerViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loggingIn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.GuideRoleSelector) },
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back to Role Selector", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(30.dp))

        StayBrokerLogo(
            tint = StayBrokerPrimary,
            modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome to StayBroker",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = StayBrokerPrimary
        )
        Text(
            text = "Browse & list profitable PG businesses securely",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            placeholder = { Text("seller@staybroker.com") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_email_input"),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("••••••••") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_password_input"),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    return@Button
                }
                loggingIn = true
                viewModel.login(email, password) { success ->
                    loggingIn = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("login_submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary),
            shape = RoundedCornerShape(8.dp),
            enabled = !loggingIn
        ) {
            if (loggingIn) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Log In Securely", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account? ", fontSize = 13.sp, color = Color.Gray)
            Text(
                text = "Sign Up",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = StayBrokerPrimary,
                modifier = Modifier.clickable { viewModel.navigateTo(Screen.AuthRegister) }
            )
        }
    }
}

// -------------------------------------------------------------
// 4. SECURE AUTH SIGNUP/REGISTER SCREEN
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: StayBrokerViewModel) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("BUYER") }
    var isSigningUp by remember { mutableStateOf(false) }

    var expandedRoleMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(Screen.GuideRoleSelector) },
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Create StayBroker Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = StayBrokerPrimary
        )
        Text(
            text = "Experience seamless verified trading with active RBAC roles",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            placeholder = { Text("John Doe") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("register_name_input"),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            placeholder = { Text("john@corporate.com") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("register_email_input"),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            placeholder = { Text("9876543210") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("register_phone_input"),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("••••••••") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("register_password_input"),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Role Dropdown selection
        ExposedDropdownMenuBox(
            expanded = expandedRoleMenu,
            onExpandedChange = { expandedRoleMenu = !expandedRoleMenu },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedRole,
                onValueChange = {},
                label = { Text("Account Role / Access") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRoleMenu) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .testTag("register_role_dropdown")
            )

            ExposedDropdownMenu(
                expanded = expandedRoleMenu,
                onDismissRequest = { expandedRoleMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("BUYER - Browse & Buy Businesses") },
                    onClick = {
                        selectedRole = "BUYER"
                        expandedRoleMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("SELLER - List & Sell own PG properties") },
                    onClick = {
                        selectedRole = "SELLER"
                        expandedRoleMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("BROKER - Manage assigned buyer leads") },
                    onClick = {
                        selectedRole = "BROKER"
                        expandedRoleMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("ADMIN - Queue verification & moderation control") },
                    onClick = {
                        selectedRole = "ADMIN"
                        expandedRoleMenu = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (fullName.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                    return@Button
                }
                isSigningUp = true
                viewModel.signup(fullName, email, password, phone, selectedRole) { success ->
                    isSigningUp = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("register_submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary),
            shape = RoundedCornerShape(8.dp),
            enabled = !isSigningUp
        ) {
            if (isSigningUp) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Complete Registration", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already registered? ", fontSize = 13.sp, color = Color.Gray)
            Text(
                text = "Log In",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = StayBrokerPrimary,
                modifier = Modifier.clickable { viewModel.navigateTo(Screen.AuthLogin) }
            )
        }
    }
}

// -------------------------------------------------------------
// 5. MAIN CONTAINER PLATFORM (DASHBOARD)
// -------------------------------------------------------------
@Composable
fun MainDashboardContainer(viewModel: StayBrokerViewModel) {
    val user by viewModel.currentUser.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    val userRole = user?.role ?: "BUYER"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StayBrokerLightBackground)
    ) {
        // App Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(StayBrokerPrimary)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StayBrokerLogo(
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "StayBroker",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            }

            // Quick Role indicator pill!
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when (userRole) {
                        "ADMIN" -> Color(0xFFD32F2F)
                        "SELLER" -> Color(0xFFE65100)
                        "BROKER" -> Color(0xFF006064)
                        else -> Color(0xFF1B5E20)
                    }
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = userRole,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Sub Bar showing logged-in name and switch bypass button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = user?.fullName ?: "Guest User",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = user?.email ?: "guest@staybroker.com",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            // High Fidelity Option: Change Role instant bypass button!
            TextButton(
                onClick = { viewModel.navigateTo(Screen.GuideRoleSelector) },
                colors = ButtonDefaults.textButtonColors(contentColor = StayBrokerSecondary),
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Switch Role", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Content Area depending on current state/role and selectedTab
        Box(modifier = Modifier.weight(1f)) {
            when (userRole) {
                "ADMIN" -> {
                    when (selectedTab) {
                        0 -> AdminVerifyQueueTab(viewModel)
                        1 -> AdminManageListingsTab(viewModel)
                        2 -> AdminStatsUsersTab(viewModel)
                        else -> ProfileTab(viewModel)
                    }
                }
                "SELLER", "BROKER" -> {
                    // Sellers and Brokers list own listings
                    when (selectedTab) {
                        0 -> SellerListingsTab(viewModel)
                        1 -> PublicInfoTab()
                        else -> ProfileTab(viewModel)
                    }
                }
                else -> { // BUYER OR PUBLIC GUEST
                    when (selectedTab) {
                        0 -> BuyerHomeTab(viewModel) { tabIndex -> selectedTab = tabIndex }
                        1 -> BuyerSearchTab(viewModel)
                        2 -> PublicInfoTab()
                        else -> ProfileTab(viewModel)
                    }
                }
            }
        }

        // Bottom Navigation Bar
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            when (userRole) {
                "ADMIN" -> {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Default.CheckCircle, "Verify Queue") },
                        label = { Text("Queue", fontSize = 11.sp) },
                        modifier = Modifier.testTag("admin_nav_queue")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Default.List, "All Listings") },
                        label = { Text("Listings", fontSize = 11.sp) },
                        modifier = Modifier.testTag("admin_nav_listings")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Icon(Icons.Default.Star, "Stats / Users") },
                        label = { Text("Admin Panel", fontSize = 11.sp) },
                        modifier = Modifier.testTag("admin_nav_stats")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = { Icon(Icons.Default.Person, "Profile") },
                        label = { Text("Profile", fontSize = 11.sp) },
                        modifier = Modifier.testTag("admin_nav_logout")
                    )
                }
                "SELLER", "BROKER" -> {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Default.Home, "My Listings") },
                        label = { Text("My Portfolio", fontSize = 11.sp) },
                        modifier = Modifier.testTag("seller_nav_listings")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Default.Info, "Company Info") },
                        label = { Text("StayBroker", fontSize = 11.sp) },
                        modifier = Modifier.testTag("seller_nav_info")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Icon(Icons.Default.Person, "Profile") },
                        label = { Text("Profile", fontSize = 11.sp) },
                        modifier = Modifier.testTag("seller_nav_profile")
                    )
                }
                else -> { // BUYER
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Default.Home, "Home") },
                        label = { Text("Home", fontSize = 11.sp) },
                        modifier = Modifier.testTag("buyer_nav_home")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Default.Search, "Browse / Filter") },
                        label = { Text("PG & Hostels", fontSize = 11.sp) },
                        modifier = Modifier.testTag("buyer_nav_search")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Icon(Icons.Default.Info, "Company Website") },
                        label = { Text("StayBroker Info", fontSize = 11.sp) },
                        modifier = Modifier.testTag("buyer_nav_info")
                    )
                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = { Icon(Icons.Default.Person, "Profile") },
                        label = { Text("Profile", fontSize = 11.sp) },
                        modifier = Modifier.testTag("buyer_nav_profile")
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB A: BUYER HOME PAGE (Housing.com UX style)
// -------------------------------------------------------------
@Composable
fun BuyerHomeTab(viewModel: StayBrokerViewModel, navigateToTab: (Int) -> Unit) {
    val publicProperties by viewModel.publicApprovedProperties.collectAsState()

    val featuredProps = remember(publicProperties) {
        publicProperties.filter { it.isFeatured }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            // Housing.com Inspired Hero heading
            Text(
                text = "Buy & Sell Profitable PG Businesses",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = StayBrokerPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Verified properties, serious buyers, faster deals.",
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            // CTA buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        // Switch to search tab
                        viewModel.selectedCategory.value = "PG"
                        navigateToTab(1)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary)
                ) {
                    Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Buy PG Business", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        // Switch role to seller or open form if already seller
                        val isSeller = viewModel.currentUser.value?.role in listOf("SELLER", "BROKER", "ADMIN")
                        if (isSeller) {
                            viewModel.navigateTo(Screen.AddPropertyForm)
                        } else {
                            viewModel.loginAsRole("SELLER")
                            viewModel.navigateTo(Screen.AddPropertyForm)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StayBrokerSecondary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Sell PG Business", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Easy Filter Quick-Access circles (like Housing.com)
            Text(
                text = "What are you looking for?",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val categories: List<Pair<String, ImageVector>> = listOf(
                    Pair("PG", Icons.Default.Home),
                    Pair("Hostel", Icons.Default.Home),
                    Pair("Commercial", Icons.Default.Build),
                    Pair("Plots", Icons.Default.Place)
                )
                categories.forEach { (cat, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                viewModel.selectedCategory.value = if (cat == "Plots") "Plot" else cat
                                navigateToTab(1)
                            }
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(StayBrokerSecondary.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, contentDescription = cat, tint = StayBrokerPrimary, modifier = Modifier.size(26.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(cat, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // HORIZONTAL CAROUSEL FOR FEATURED LISTINGS
            if (featuredProps.isNotEmpty()) {
                Text(
                    text = "High-Yield Featured Opportunities ⭐",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    featuredProps.forEach { prop ->
                        Card(
                            modifier = Modifier
                                .width(250.dp)
                                .clickable { viewModel.navigateTo(Screen.PropertyDetails(prop.id)) },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            border = BorderStroke(1.5.dp, Color(0xFFFFB300))
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(Color(0xFF0F172A), StayBrokerPrimary)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(24.dp))
                                        Text("PREMIUM SPARK • FEATURED", color = Color(0xFFFFD700), fontSize = 9.sp, fontWeight = FontWeight.Black)
                                    }
                                }

                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = prop.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = prop.location,
                                        fontSize = 11.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "₹${prop.rentOrPrice.toInt()}/mo",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 13.sp,
                                            color = StayBrokerPrimary
                                        )
                                        if (prop.occupancyPercent > 0) {
                                            Text(
                                                text = "${prop.occupancyPercent}% Occupant",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF2E7D32)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Verified listings title
            Text(
                text = "Hot verified PG acquisitions near you",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

        if (publicProperties.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Info, null, modifier = Modifier.size(48.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No properties verified yet.", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(publicProperties.take(3)) { property ->
                PropertyListItem(property = property, viewModel = viewModel)
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = StayBrokerPrimary.copy(alpha = 0.05f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Why StayBroker PG Acquisitions?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = StayBrokerPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• 100% Verified commercial P&L spreadsheets.\n• Assistance in corporate franchise lease transfers.\n• Dedicated active broker assigned per inquiry.",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// -------------------------------------------------------------
// TAB B: BUYER RESEARCH / SEARCH / PROPERTY LISTINGS
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerSearchTab(viewModel: StayBrokerViewModel) {
    val searchVal by viewModel.searchQuery.collectAsState()
    val activeCat by viewModel.selectedCategory.collectAsState()
    val activeLoc by viewModel.selectedLocationFilter.collectAsState()

    val publicProperties by viewModel.publicApprovedProperties.collectAsState()

    var showAdvancedFilters by remember { mutableStateOf(false) }
    var maxBudgetStr by remember { mutableStateOf("") }
    var minOccupancyStr by remember { mutableStateOf("") }
    var minRevenueStr by remember { mutableStateOf("") }
    var onlyFeaturedFilter by remember { mutableStateOf(false) }

    // Locally filtered list based on state triggers
    val filteredProperties = remember(
        publicProperties, searchVal, activeCat, activeLoc,
        maxBudgetStr, minOccupancyStr, minRevenueStr, onlyFeaturedFilter
    ) {
        val maxBudget = maxBudgetStr.toDoubleOrNull()
        val minOccupany = minOccupancyStr.toIntOrNull()
        val minRevenue = minRevenueStr.toDoubleOrNull()

        publicProperties.filter { prop ->
            val matchesSearch = prop.title.contains(searchVal, ignoreCase = true) ||
                    prop.location.contains(searchVal, ignoreCase = true) ||
                    prop.description.contains(searchVal, ignoreCase = true)

            val matchesCat = if (activeCat == "All") true else prop.type.equals(activeCat, ignoreCase = true)
            val matchesLoc = if (activeLoc == "All") true else prop.location.contains(activeLoc, ignoreCase = true)
            
            val matchesBudget = if (maxBudget == null) true else prop.rentOrPrice <= maxBudget
            val matchesOccupancy = if (minOccupany == null) true else prop.occupancyPercent >= minOccupany
            val matchesRevenue = if (minRevenue == null) true else prop.monthlyRevenue >= minRevenue
            val matchesFeatured = if (!onlyFeaturedFilter) true else prop.isFeatured

            matchesSearch && matchesCat && matchesLoc && matchesBudget && matchesOccupancy && matchesRevenue && matchesFeatured
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(14.dp))

        // Search Bar Text input
        OutlinedTextField(
            value = searchVal,
            onValueChange = { viewModel.searchQuery.value = it },
            placeholder = { Text("Search Sector, Tech Park, City...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            trailingIcon = {
                if (searchVal.isNotEmpty()) {
                    IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                        Icon(Icons.Default.Clear, "Clear")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("property_search_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontal Category Row Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filterCats = listOf("All", "PG", "Hostel", "Commercial", "Plot")
            filterCats.forEach { cat ->
                val isSelected = activeCat == cat
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectedCategory.value = cat },
                    label = { Text(cat, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = StayBrokerPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Horizontal Location Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filterLocs = listOf("All", "Gurgaon", "Bangalore", "Hyderabad")
            filterLocs.forEach { loc ->
                val isSelected = activeLoc == loc
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectedLocationFilter.value = loc },
                    label = { Text(loc, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Collapsible Advanced Filter Panel Toggler
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showAdvancedFilters = !showAdvancedFilters },
            colors = CardDefaults.cardColors(containerColor = StayBrokerPrimary.copy(alpha = 0.05f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Settings, null, tint = StayBrokerPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Advanced Filters (Budget, Occupancy, Profits)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = StayBrokerPrimary)
                }
                Icon(
                    imageVector = if (showAdvancedFilters) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = StayBrokerPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Advanced filter input fields
        if (showAdvancedFilters) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = maxBudgetStr,
                            onValueChange = { maxBudgetStr = it },
                            label = { Text("Max Budget (₹)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = minOccupancyStr,
                            onValueChange = { minOccupancyStr = it },
                            label = { Text("Min Occupancy %") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = minRevenueStr,
                            onValueChange = { minRevenueStr = it },
                            label = { Text("Min Revenue Margin (₹)") },
                            modifier = Modifier.weight(1.2f),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier
                                .weight(0.8f)
                                .clickable { onlyFeaturedFilter = !onlyFeaturedFilter },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Checkbox(
                                checked = onlyFeaturedFilter,
                                onCheckedChange = { onlyFeaturedFilter = it }
                            )
                            Text("Featured ⭐", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Reset Filters Button
                    TextButton(
                        onClick = {
                            maxBudgetStr = ""
                            minOccupancyStr = ""
                            minRevenueStr = ""
                            onlyFeaturedFilter = false
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Reset Advanced Filters", fontSize = 11.sp, color = Color.Red)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Listings Title
        Text(
            text = "Found ${filteredProperties.size} approved businesses",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (filteredProperties.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Warning, null, modifier = Modifier.size(56.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No matching PG businesses found.", color = Color.Gray, fontWeight = FontWeight.Medium)
                    Text("Try resetting filters or clear search query.", color = Color.Gray, fontSize = 12.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProperties) { prop ->
                    PropertyListItem(property = prop, viewModel = viewModel)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SELLER / BROKER MY LISTINGS TAB
// -------------------------------------------------------------
@Composable
fun SellerListingsTab(viewModel: StayBrokerViewModel) {
    val myProps by viewModel.myProperties.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Premium Subscription Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = StayBrokerPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("PLATINUM PREMIUM SUITE", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF10B981), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("ACTIVE", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Partner ID: SB-9482 • Plan Expires: Nov 2026",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.82f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Benefits: 3x Top Search Placements, 10x Leads notifications, Verified partner check, Priority approval support.",
                        fontSize = 11.sp,
                        color = Color.White,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "My Brokerage Portfolio",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "Listings require admin approval to become visible.",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (myProps.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Add, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("You haven't listed any property yet.", color = Color.Gray, fontWeight = FontWeight.Bold)
                        Text("Tap the floating '+' button to create one.", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(myProps) { prop ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.navigateTo(Screen.PropertyDetails(prop.id)) },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = prop.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "${prop.type} • ${prop.location}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    // Status pill!
                                    val (statusText, statusBg, statusFg) = when (prop.status) {
                                        "verifying" -> Triple("VERIFYING", StatusVerifyingColor.copy(alpha = 0.15f), StatusVerifyingColor)
                                        "approved" -> Triple("APPROVED", StatusApprovedColor.copy(alpha = 0.15f), StatusApprovedColor)
                                        "rejected" -> Triple("REJECTED", StatusRejectedColor.copy(alpha = 0.15f), StatusRejectedColor)
                                        else -> Triple("SOLD", StatusSoldColor.copy(alpha = 0.15f), StatusSoldColor)
                                    }
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = statusBg),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = statusText,
                                            color = statusFg,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "₹${prop.rentOrPrice.toInt()}/mo",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 15.sp,
                                        color = StayBrokerPrimary
                                    )
                                    Text(
                                        text = "${prop.roomsCount} Beds available",
                                        fontSize = 12.sp,
                                        color = Color.DarkGray
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(color = Color.LightGray.copy(alpha = 0.4f))
                                Spacer(modifier = Modifier.height(4.dp))

                                // Featured Promotional Actions on card
                                if (prop.isFeatured) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.CheckCircle, "Promoted", tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("FEATURED ACTIVE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB300))
                                        }
                                        Text("Priority visibility live", fontSize = 10.sp, color = Color.Gray)
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Not promoted yet", fontSize = 11.sp, color = Color.Gray)
                                        TextButton(
                                            onClick = { viewModel.upgradePropertyToFeatured(prop.id, 30) },
                                            modifier = Modifier.height(32.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Feature Promo (30D)", fontSize = 11.sp, color = StayBrokerPrimary, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // FLOATING ACTION BUTTON
        FloatingActionButton(
            onClick = { viewModel.navigateTo(Screen.AddPropertyForm) },
            containerColor = StayBrokerPrimary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .testTag("add_property_fab")
        ) {
            Icon(Icons.Default.Add, "Create Property")
        }
    }
}

// -------------------------------------------------------------
// TAB C: ADMIN CONSOLE - APPROVAL/VERIFY QUEUE
// -------------------------------------------------------------
@Composable
fun AdminVerifyQueueTab(viewModel: StayBrokerViewModel) {
    val verifyingList by viewModel.verifyingProperties.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Verifying Properties Queue",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
        Text(
            text = "Review listings created by sellers/brokers to approve them.",
            fontSize = 11.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (verifyingList.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Done, null, modifier = Modifier.size(56.dp), tint = StatusApprovedColor)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Queue is empty!", color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text("All proposed PG listings are reviewed.", color = Color.Gray, fontSize = 12.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(verifyingList) { prop ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.navigateTo(Screen.PropertyDetails(prop.id)) },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = prop.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "By Seller: ${prop.creatorEmail}",
                                        fontSize = 11.sp,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = "${prop.type} • ${prop.location}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                                Text(
                                    text = "₹${prop.rentOrPrice.toInt()}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = StayBrokerPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = prop.description,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            // Moderator Actions Flow (Never blocks workspace!)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.updateListingStatusByAdmin(prop.id, "approved") },
                                    colors = ButtonDefaults.buttonColors(containerColor = StatusApprovedColor),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .testTag("approve_btn_${prop.id}"),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Approve", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = { viewModel.updateListingStatusByAdmin(prop.id, "rejected") },
                                    colors = ButtonDefaults.buttonColors(containerColor = StatusRejectedColor),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .testTag("reject_btn_${prop.id}"),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Close, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Reject", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB D: ADMIN CONSOLE - MODERATE ALL LISTINGS
// -------------------------------------------------------------
@Composable
fun AdminManageListingsTab(viewModel: StayBrokerViewModel) {
    val allProps by viewModel.adminAllProperties.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Global Moderation Center",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
        Text(
            text = "Total system properties registered: ${allProps.size}",
            fontSize = 11.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (allProps.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Database contains no listings.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(allProps) { prop ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.navigateTo(Screen.PropertyDetails(prop.id)) },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = prop.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${prop.type} • ${prop.location} • Status: ${prop.status.uppercase()}",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                            IconButton(
                                onClick = { viewModel.toggleFeaturedByAdmin(prop.id) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(
                                    imageVector = if (prop.isFeatured) Icons.Default.Star else Icons.Default.Star,
                                    contentDescription = "Toggle Featured Promotion",
                                    tint = if (prop.isFeatured) Color(0xFFFFB300) else Color.LightGray
                                )
                            }

                            IconButton(
                                onClick = { viewModel.deletePropertyByAdmin(prop.id) },
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(Icons.Default.Delete, "Remove Spam", tint = StatusRejectedColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB E: ADMIN STATS & USER MANAGEMENT
// -------------------------------------------------------------
@Composable
fun AdminStatsUsersTab(viewModel: StayBrokerViewModel) {
    val users by viewModel.allUsers.collectAsState()
    val leads by viewModel.allLeads.collectAsState()
    val properties by viewModel.adminAllProperties.collectAsState()

    val pendingNum = properties.count { it.status == "verifying" }
    val approvedNum = properties.count { it.status == "approved" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Live Brokerage Operations Status",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Operation metrics grid cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = StayBrokerPrimary.copy(alpha = 0.05f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Active Approved", fontSize = 11.sp, color = Color.Gray)
                        Text("$approvedNum List", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = StayBrokerPrimary)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = StatusVerifyingColor.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Pending Review", fontSize = 11.sp, color = Color.Gray)
                        Text("$pendingNum Queue", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = StatusVerifyingColor)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = StatusApprovedColor.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Broker Leads", fontSize = 11.sp, color = Color.Gray)
                        Text("${leads.size} Hot", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = StatusApprovedColor)
                    }
                }
            }
        }

        item {
            Text(
                text = "System Leads Console",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        if (leads.isEmpty()) {
            item {
                Text("No inquiry leads yet.", color = Color.Gray, fontSize = 12.sp)
            }
        } else {
            items(leads) { lead ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(lead.buyerName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Phone: ${lead.buyerPhone}", fontSize = 11.sp, color = Color.Gray)
                        }
                        Text("Inquired For: ${lead.propertyTitle}", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = StayBrokerPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(lead.message, fontSize = 12.sp, color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Broker Assigned: ${lead.brokerEmail}", fontSize = 11.sp, color = StayBrokerSecondary)
                    }
                }
            }
        }

        item {
            Text(
                text = "User Accounts Directory",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        items(users) { usr ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(usr.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("${usr.email} • Role: ${usr.role}", fontSize = 12.sp, color = Color.Gray)
                    }
                    if (usr.role != "ADMIN") {
                        IconButton(
                            onClick = { viewModel.deleteUserByAdmin(usr.email) },
                            modifier = Modifier.minimumInteractiveComponentSize()
                        ) {
                            Icon(Icons.Default.Delete, "Ban User", tint = StatusRejectedColor)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// -------------------------------------------------------------
// TAB G: GLOBAL PROFILE VIEW
// -------------------------------------------------------------
@Composable
fun ProfileTab(viewModel: StayBrokerViewModel) {
    val user by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            tint = StayBrokerPrimary,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = user?.fullName ?: "Active Session Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = user?.email ?: "No corporate email linked",
            fontSize = 13.sp,
            color = Color.Gray
        )
        Card(
            modifier = Modifier.padding(vertical = 12.dp),
            colors = CardDefaults.cardColors(containerColor = StayBrokerSecondary.copy(alpha = 0.1f))
        ) {
            Text(
                text = "Account Role: ${user?.role ?: "GUEST"}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                fontWeight = FontWeight.Bold,
                color = StayBrokerPrimary,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("logout_button"),
            colors = ButtonDefaults.buttonColors(containerColor = StatusRejectedColor)
        ) {
            Icon(Icons.Default.ExitToApp, null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Logout & Return to Onboarding", fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------
// TAB H: CORPORATE PUBLIC WEBSITE INFO SCREEN
// -------------------------------------------------------------
@Composable
fun PublicInfoTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "About StayBroker Tech",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = StayBrokerPrimary
            )
            Text(
                text = "A pioneering B2B acquisition platform for PG and Hostels.",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Our Venture Story",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = StayBrokerPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Founded in 2026, StayBroker bridges the gap between active PG operators and serial real-estate acquisition buyers. We streamline asset evaluations, commercial audits, and municipal franchise licensing into a unified B2B platform.",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Our Backing Investors & Funding",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = StayBrokerPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Backed by Peak XV, Tiger Global, and Y-Combinator, raising ₹120 Crores in Series A funding to expand digital commercial brokerage operations across tier-1 student tech clusters.",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Featured Success Stories",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = StayBrokerPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "🏆 Delhi Sector-15 Elite PG Acquisition\n" +
                               "Client: Rohit Capital Group acquired a 180-bed campus hostel generating ₹24L profit/yr. Close-out period: 14 days.\n\n" +
                               "🏆 Bangalore Electronic City PG Portfolio\n" +
                               "Client: Maple Leaves Living acquired 4 premium co-living assets valued at ₹14.5 Crore. Gross rental yield: 11.2% IRR.",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, null, tint = StayBrokerSecondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Careers @ StayBroker",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = StayBrokerPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Join our mission to power digital B2B franchise brokerage. We value transparency, metric audits, and relentless execution.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "🔥 Open Roles:\n" +
                               "• Senior Evaluation Analyst (Gurgaon Office)\n" +
                               "• Regional Operations Lead (Bangalore Sector)\n" +
                               "• Inside Sales Associate (Remote / Hyderabad)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Hiring Process: 1. Application Screen • 2. Commercial Audit Case Assignment • 3. Partner Panel Interview.",
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Contact Corporate Office",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = StayBrokerPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📧 Email: info@staybroker.com\n" +
                               "📞 Call Support: +91 1800 2200 44\n" +
                               "📍 Address: Signature Towers, Sector 30, Gurgaon, Haryana, India.",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 6. ADD PROPERTY FORM SUBMISSION SCREENS
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyFormScreen(viewModel: StayBrokerViewModel) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("PG") }
    var roomsStr by remember { mutableStateOf("") }
    var features by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var monthlyRevenueStr by remember { mutableStateOf("") }
    var occupancyPercentStr by remember { mutableStateOf("") }

    var expandedTypeDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateBack() },
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
            Text("Back to Dashboard", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Create PG Business Listing",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = StayBrokerPrimary
        )
        Text(
            text = "Saves in VERIFYING state. Approved properties become public.",
            fontSize = 11.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("List Business Name / Title") },
                    placeholder = { Text("Stanza Living 64-Bed Premium Co-Living PG") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_title_input"),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expandedTypeDropdown,
                    onExpandedChange = { expandedTypeDropdown = !expandedTypeDropdown },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = type,
                        onValueChange = {},
                        label = { Text("Category Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTypeDropdown) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .testTag("form_type_dropdown")
                    )

                    ExposedDropdownMenu(
                        expanded = expandedTypeDropdown,
                        onDismissRequest = { expandedTypeDropdown = false }
                    ) {
                        listOf("PG", "Hostel", "Commercial", "Residential", "Plot").forEach { t ->
                            DropdownMenuItem(
                                text = { Text(t) },
                                onClick = {
                                    type = t
                                    expandedTypeDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = priceStr,
                        onValueChange = { priceStr = it },
                        label = { Text("Monthly Rent / Value (₹)") },
                        placeholder = { Text("180000") },
                        modifier = Modifier
                            .weight(1.1f)
                            .testTag("form_price_input"),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = roomsStr,
                        onValueChange = { roomsStr = it },
                        label = { Text("Bed/Room Count") },
                        placeholder = { Text("42") },
                        modifier = Modifier
                            .weight(0.9f)
                            .testTag("form_rooms_input"),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = monthlyRevenueStr,
                        onValueChange = { monthlyRevenueStr = it },
                        label = { Text("Margin Profit (₹/mo)") },
                        placeholder = { Text("85000") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("form_revenue_input"),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = occupancyPercentStr,
                        onValueChange = { occupancyPercentStr = it },
                        label = { Text("Active Occupancy %") },
                        placeholder = { Text("92") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("form_occupancy_input"),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location Address Area") },
                    placeholder = { Text("Sector 45, Gurgaon") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_location_input"),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = features,
                    onValueChange = { features = it },
                    label = { Text("Amenities / Features (comma separated)") },
                    placeholder = { Text("WiFi, AC, Laundry, Power Backup") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("form_features_input"),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Detailed Business Description & Margin Specs") },
                    placeholder = { Text("Provide lease terms, expected EBITDA, margins details...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("form_desc_input"),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        val price = priceStr.toDoubleOrNull() ?: 0.0
                        val rooms = roomsStr.toIntOrNull() ?: 0
                        val revenue = monthlyRevenueStr.toDoubleOrNull() ?: 0.0
                        val occupancy = occupancyPercentStr.toIntOrNull() ?: 0
                        if (title.isBlank() || location.isBlank() || price <= 0 || rooms <= 0) {
                            return@Button
                        }
                        viewModel.addProperty(title, type, desc, price, location, features, rooms, revenue, occupancy)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("form_submit_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Share, null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Publish Proposed PG Listing", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 7. COMPREHENSIVE PROPERTY DETAIL VIEW / MODAL (No Dead-Ends)
// -------------------------------------------------------------
@Composable
fun PropertyDetailsScreen(propertyId: Int, viewModel: StayBrokerViewModel, onClose: () -> Unit) {
    var showLeadDialog by remember { mutableStateOf(false) }
    var propDetails by remember { mutableStateOf<PropertyEntity?>(null) }

    LaunchedEffect(propertyId) {
        propDetails = viewModel.repository.getPropertyById(propertyId)
    }

    val prop = propDetails

    if (prop == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = StayBrokerPrimary)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(StayBrokerLightBackground)
        ) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose, modifier = Modifier.minimumInteractiveComponentSize()) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = prop.type,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = StayBrokerPrimary
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Simulated Card Photo banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(StayBrokerPrimary, StayBrokerSecondary)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = prop.title.take(1).uppercase(),
                            color = Color.White.copy(alpha = 0.2f),
                            fontSize = 120.sp,
                            fontWeight = FontWeight.Black
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Home, contentDescription = null, tint = Color.White, modifier = Modifier.size(56.dp))
                            Card(
                                modifier = Modifier.padding(top = 10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                            ) {
                                Text(
                                    text = "STOCK PHOTO PLATFORM VERIFIED",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StayBrokerPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = prop.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                    Text(
                        text = prop.location,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "₹${prop.rentOrPrice.toInt()} /mo",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = StayBrokerPrimary
                        )

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = when (prop.status) {
                                    "verifying" -> StatusVerifyingColor
                                    "approved" -> StatusApprovedColor
                                    "rejected" -> StatusRejectedColor
                                    else -> StatusSoldColor
                                }
                            ),
                            shape = CircleShape
                        ) {
                            Text(
                                text = prop.status.uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    Text(
                        text = "Business Metrics",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Rooms/Beds available", fontSize = 11.sp, color = Color.Gray)
                                Text("${prop.roomsCount} Beds", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Proposed ROI Yield", fontSize = 11.sp, color = Color.Gray)
                                Text("18.5% p.a.", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }

                    if (prop.monthlyRevenue > 0.0 || prop.occupancyPercent > 0) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (prop.monthlyRevenue > 0.0) {
                                Card(
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Assumed Profit Margin", fontSize = 11.sp, color = Color.Gray)
                                        Text("₹${prop.monthlyRevenue.toInt()}/mo", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = StayBrokerPrimary)
                                    }
                                }
                            }

                            if (prop.occupancyPercent > 0) {
                                Card(
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Active Occupancy", fontSize = 11.sp, color = Color.Gray)
                                        Text("${prop.occupancyPercent}% Occupant", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Amenities & Facilities",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val ams = prop.features.split(",")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ams.forEach { am ->
                            val cleanAm = am.trim()
                            if (cleanAm.isNotEmpty()) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = StayBrokerPrimary.copy(alpha = 0.08f)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = cleanAm,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = StayBrokerPrimary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = "Operational Overview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = prop.description,
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 19.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Listed by: ${prop.creatorEmail}",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            // Buyer's Inquiry Bottom action button bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { showLeadDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("inquire_button"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Inquire & Connect StayBroker Experts", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showLeadDialog) {
        LeadSubmissionDialog(
            property = prop!!,
            viewModel = viewModel
        ) {
            showLeadDialog = false
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENT: LEAD SUBMISSION FLOW MODAL DIALOG
// -------------------------------------------------------------
@Composable
fun LeadSubmissionDialog(
    property: PropertyEntity,
    viewModel: StayBrokerViewModel,
    onClose: () -> Unit
) {
    var name by remember { mutableStateOf(viewModel.currentUser.value?.fullName ?: "") }
    var email by remember { mutableStateOf(viewModel.currentUser.value?.email ?: "") }
    var phone by remember { mutableStateOf(viewModel.currentUser.value?.phone ?: "") }
    var message by remember { mutableStateOf("I am highly motivated in reviewing details and auditing the operational spreadsheet for ${property.title}. Please request broker call.") }

    Dialog(onDismissRequest = onClose) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Request Broker Call",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = StayBrokerPrimary
                )
                Text(
                    text = property.title,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Active Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Message Spec") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onClose, modifier = Modifier.minimumInteractiveComponentSize()) {
                        Text("Cancel", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (name.isBlank() || email.isBlank() || phone.isBlank()) return@Button
                            viewModel.createLead(
                                propertyId = property.id,
                                propertyTitle = property.title,
                                name = name,
                                email = email,
                                phone = phone,
                                message = message
                            )
                            onClose()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StayBrokerPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Submit Lead Request", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENT: STANDARD CARD PROPERTY list item
// -------------------------------------------------------------
@Composable
fun PropertyListItem(property: PropertyEntity, viewModel: StayBrokerViewModel) {
    val isFeatured = property.isFeatured
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.navigateTo(Screen.PropertyDetails(property.id)) }
            .padding(vertical = 6.dp)
            .testTag("property_card_${property.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isFeatured) 5.dp else 2.dp),
        border = if (isFeatured) BorderStroke(1.5.dp, Color(0xFFFFB300)) else null
    ) {
        Column {
            // Card banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = when {
                                isFeatured -> listOf(Color(0xFF0F172A), StayBrokerPrimary)
                                property.imageUrlIndex % 3 == 0 -> listOf(StayBrokerPrimary, StayBrokerSecondary)
                                property.imageUrlIndex % 3 == 1 -> listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
                                else -> listOf(Color(0xFF0F766E), Color(0xFF0D9488))
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Featured Tag
                if (isFeatured) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .background(Color(0xFFFFB300), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "FEATURED OPPORTUNITY",
                            color = Color.Black,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = if (isFeatured) Icons.Default.Star else Icons.Default.Home,
                        contentDescription = null,
                        tint = if (isFeatured) Color(0xFFFFD700) else Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isFeatured) "TOP TIER ACCREDITED DEALS" else "SECURE COMMERCIAL ASSET",
                        color = if (isFeatured) Color(0xFFFFD700) else Color.White.copy(alpha = 0.8f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = property.title,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            if (isFeatured) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.CheckCircle, "Accredited", tint = Color(0xFF10B981), modifier = Modifier.size(14.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = property.location,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "₹${property.rentOrPrice.toInt()}/mo",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = StayBrokerPrimary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Conditionally Show Financial Metrics
                if (property.monthlyRevenue > 0.0 || property.occupancyPercent > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .background(StayBrokerPrimary.copy(alpha = 0.04f), RoundedCornerShape(6.dp))
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (property.monthlyRevenue > 0.0) {
                            Column {
                                Text("Monthly Margin", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                Text("₹${property.monthlyRevenue.toInt()}/mo", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                        if (property.occupancyPercent > 0) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Active Occupancy", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                Text("${property.occupancyPercent}% Occupied", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Home, null, tint = StayBrokerSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${property.type} • ${property.roomsCount} Beds Available", fontSize = 11.sp, color = Color.DarkGray)
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isFeatured) Color(0xFFFFF7ED) else StayBrokerPrimary.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isFeatured) "PREMIUM PLACEMENT" else "VERIFIED ROI",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isFeatured) Color(0xFFD97706) else StayBrokerPrimary
                        )
                    }
                }
            }
        }
    }
}


