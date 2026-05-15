package com.example.manekelsaapp

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.manekelsaapp.model.Worker
// Finalizing UI for submission

// Data class for Top Categories with search logic
data class CategoryIcon(val label: String, val icon: ImageVector, val searchKey: String)

// Updated Translation Helper with full coverage for 2026 data
private fun translateToKannada(text: String?): String {
    if (text.isNullOrEmpty()) return "ಮಾಹಿತಿ ಇಲ್ಲ"

    val map = mapOf(
        // Names
        "suresh" to "ಸುರೇಶ್",
        "kavitha" to "ಕವಿತಾ",
        "kushi" to "ಖುಷಿ",
        "manjunath" to "ಮಂಜುನಾಥ್",
        "girish" to "ಗಿರೀಶ್",
        "santosh" to "ಸಂತೋಷ್",
        "arif" to "ಆರಿಫ್",
        "ravi" to "ರವಿ",
        "lokesh" to "ಲೋಕೇಶ್",
        "praveen" to "ಪ್ರವೀಣ್",
        "Manjunath" to "ಮಂಜುನಾಥ್",
        "kiran" to "ಕಿರಣ್",
        "mahesh" to "ಮಹೇಶ್",

        // Skills / Categories
        "painter" to "ಪೇಂಟರ್",
        "carpenter" to "ಬಡಗಿ",
        "driver" to "ಚಾಲಕ",
        "electrician" to "ಎಲೆಕ್ಟ್ರಿಷಿಯನ್",
        "plumber" to "ಪ್ಲಂಬರ್",
        "gardener" to "ತೋಟಗಾರ",
        "cook" to "ಅಡುಗೆ ಕೆಲಸ",
        "maid" to "ಮನೆಗೆಲಸ",
        "house maid" to "ಮನೆಯ ಸಹಾಯಕಿ",
        "ac mechanic" to "ಎಸಿ ಮೆಕಾನಿಕ್",
        "babysitter" to "ಬೇಬಿಸಿಟರ್",
        "delivery boy" to "ಡೆಲಿವರಿ ಬಾಯ್",
        "security guard" to "ಸೆಕ್ಯೂರಿಟಿ ಗಾರ್ಡ್",
        "cleaner" to "ಕ್ಲೀನಿಂಗ್ ಕೆಲಸ",
        "mason" to "ಮೇಸನ್ (ಗಾರೆ ಕೆಲಸ)",
        "tailor" to "ಟೈಲರ್",
        "photographer" to "ಫೋಟೋಗ್ರಾಫರ್",
        "welder" to "ವೆಲ್ಡರ್",
        "mechanic" to "ಮೆಕಾನಿಕ್",
        "computer repair" to "ಕಂಪ್ಯೂಟರ್ ಕೆಲಸ",
        "nurse" to "ನರ್ಸ್",
        "teacher" to "ಶಿಕ್ಷಕರು",
        "moving" to "ಮೂವಿಂಗ್ ಸೇವೆ"
    )

    val key = text.trim().lowercase()
    return map[key] ?: text
}

@Composable
fun ResidentHomeScreen(
    viewModel: ManeKelsaViewModel,
    onWorkerClick: (String) -> Unit
) {
    var showProfile by remember { mutableStateOf(false) }

    BackHandler(enabled = showProfile) {
        showProfile = false
    }

    if (showProfile) {
        ProfileContent(
            onBackClick = { showProfile = false },
            onLogoutClick = { /* Add Firebase SignOut Logic Here */ }
        )
    } else {
        HomeContent(
            viewModel = viewModel,
            onWorkerClick = onWorkerClick,
            onProfileClick = { showProfile = true }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    viewModel: ManeKelsaViewModel,
    onWorkerClick: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    val workers by viewModel.workers
    val isLoading by viewModel.isLoading
    var searchQuery by remember { mutableStateOf("") }

    val categoryList = listOf(
        CategoryIcon("ಪೇಂಟರ್", Icons.Default.FormatPaint, "painter"),
        CategoryIcon("ಚಾಲಕ", Icons.Default.DirectionsCar, "driver"),
        CategoryIcon("ಪ್ಲಂಬರ್", Icons.Default.WaterDrop, "plumber"),
        CategoryIcon("ಬಡಗಿ", Icons.Default.Handyman, "carpenter"),
        CategoryIcon("ವೈರಿಂಗ್", Icons.Default.ElectricBolt, "electrician"),
        CategoryIcon("ಡೆಲಿವರಿ", Icons.Default.DeliveryDining, "delivery"),
        CategoryIcon("ಅಡುಗೆ", Icons.Default.Restaurant, "cook"),
        CategoryIcon("ಕ್ಲೀನಿಂಗ್", Icons.Default.CleaningServices, "cleaner"),
        CategoryIcon("ತೋಟಗಾರ", Icons.Default.Agriculture, "gardener"),
        CategoryIcon("ಸೆಕ್ಯೂರಿಟಿ", Icons.Default.Security, "security"),
        CategoryIcon("ಮೆಕಾನಿಕ್", Icons.Default.Build, "mechanic"),
        CategoryIcon("ಟೈಲರ್", Icons.Default.ContentCut, "tailor"),
        CategoryIcon("ಮೇಸನ್", Icons.Default.Construction, "mason"),
        CategoryIcon("ಎಲೆಕ್ಟ್ರಿಷಿಯನ್", Icons.Default.Power, "electrician"),
        CategoryIcon("ವೆಲ್ಡರ್", Icons.Default.Fireplace, "welder"),
        CategoryIcon("ಫೋಟೋಗ್ರಾಫರ್", Icons.Default.CameraAlt, "photographer"),
        CategoryIcon("ಟೀಚರ್", Icons.Default.MenuBook, "teacher"),
        CategoryIcon("ನರ್ಸ್", Icons.Default.LocalHospital, "nurse"),
        CategoryIcon("ಕಂಪ್ಯೂಟರ್ ಕೆಲಸ", Icons.Default.Computer, "computer"),
        CategoryIcon("ಮೂವಿಂಗ್ ಸೇವೆ", Icons.Default.LocalShipping, "moving"),
        CategoryIcon("ಮನೆಯ ಸಹಾಯಕಿ", Icons.Default.CleaningServices, "housemaid"),
        CategoryIcon("ಡೆಲಿವರಿ ಬಾಯ್", Icons.Default.DeliveryDining, "deliveryboy"),
        CategoryIcon("ಬೇಬಿಸಿಟರ್", Icons.Default.ChildCare, "babysitter")
    )

    val filteredWorkers = remember(workers, searchQuery) {
        if (searchQuery.isEmpty()) workers
        else workers.filter {
            it.name.contains(searchQuery, true) || it.skill.contains(searchQuery, true)
        }
    }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 8.dp) {
                Column(modifier = Modifier.background(Color(0xFF1A0933))) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("ಮನೆ ಕೆಲಸಗಾರರು", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = onProfileClick) {
                            Icon(Icons.Default.AccountCircle, null, tint = Color(0xFFFFC107), modifier = Modifier.size(32.dp))
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        placeholder = { Text("ಹುಡುಕಿ...", color = Color.LightGray) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, null, tint = Color.White)
                                }
                            } else {
                                Icon(Icons.Default.Search, null, tint = Color.White)
                            }
                        },
                        singleLine = true
                    )

                    LazyRow(
                        modifier = Modifier.padding(vertical = 16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(categoryList) { category ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable { searchQuery = category.searchKey }
                            ) {
                                Surface(
                                    modifier = Modifier.size(56.dp),
                                    shape = CircleShape,
                                    color = if (searchQuery == category.searchKey) Color(0xFFFFC107) else Color.White.copy(alpha = 0.15f)
                                ) {
                                    Icon(
                                        category.icon,
                                        null,
                                        tint = if (searchQuery == category.searchKey) Color.Black else Color(0xFFFFC107),
                                        modifier = Modifier.padding(14.dp)
                                    )
                                }
                                Text(category.label, color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFF1A0933))
            } else {
                if (filteredWorkers.isEmpty()) {
                    Text("ಯಾವುದೇ ಕೆಲಸಗಾರರು ಕಂಡುಬಂದಿಲ್ಲ", modifier = Modifier.align(Alignment.Center), color = Color.Gray)
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(filteredWorkers) { worker -> WorkerCard(worker, onWorkerClick) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        TopAppBar(
            title = { Text("ನನ್ನ ಪ್ರೊಫೈಲ್", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A0933), titleContentColor = Color.White)
        )

        Column(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(modifier = Modifier.size(100.dp).border(3.dp, Color(0xFF1A0933), CircleShape), shape = CircleShape, color = Color.White) {
                    Icon(Icons.Default.AccountCircle, null, modifier = Modifier.size(100.dp), tint = Color(0xFF1A0933).copy(alpha = 0.2f))
                }
                Surface(modifier = Modifier.size(30.dp), shape = CircleShape, color = Color(0xFFFFC107), border = BorderStroke(2.dp, Color.White)) {
                    Icon(Icons.Default.Edit, null, modifier = Modifier.padding(6.dp), tint = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("ಬಳಕೆದಾರರು", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A0933))
            Text("+91 9876543210", fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ProfileMenuItem(Icons.Default.History, "ನನ್ನ ಆರ್ಡರ್‌ಗಳು")
                    ProfileMenuItem(Icons.Default.LocationOn, "ಉಳಿಸಿದ ವಿಳಾಸಗಳು")
                    ProfileMenuItem(Icons.Default.Notifications, "ನೋಟಿಫಿಕೇಶನ್")

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Color(0xFFEEEEEE))

                    Row(modifier = Modifier.fillMaxWidth().clickable { onLogoutClick() }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.Logout, null, tint = Color.Red)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("ಲಾಗ್ ಔಟ್", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, title: String) {
    Row(modifier = Modifier.fillMaxWidth().clickable {}.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Color(0xFF1A0933), modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
        Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.LightGray)
    }
}

@Composable
fun WorkerCard(worker: Worker, onWorkerClick: (String) -> Unit) {
    val context = LocalContext.current
    val availabilityText = if (worker.available) "ಲಭ್ಯವಿದ್ದಾರೆ" else "ಲಭ್ಯವಿಲ್ಲ"
    val statusColor = if (worker.available) Color(0xFF00C853) else Color.Red
    val callButtonColor = if (worker.available) Color(0xFF2E7D32) else Color.Gray

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onWorkerClick(worker.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                AsyncImage(
                    model = worker.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(90.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(10.dp).background(statusColor, CircleShape))
                            Spacer(Modifier.width(8.dp))
                            Text(translateToKannada(worker.name), fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ThumbUp, null, tint = Color(0xFF1A0933), modifier = Modifier.size(14.dp))
                            Text(" ${worker.rating}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(" (${worker.ratingCount})", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                    Text(availabilityText, color = statusColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("ನುರಿತ ${translateToKannada(worker.skill)}", color = Color.Gray, fontSize = 14.sp)
                    Text("ನಿಮ್ಮಿಂದ ${worker.distance} ಕಿ.ಮೀ ದೂರ", color = Color(0xFF1A0933), fontSize = 12.sp, fontWeight = FontWeight.Medium)

                    Row(modifier = Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Text(" ವಯಸ್ಸು: ${worker.age}", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(Icons.Default.HistoryEdu, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Text(" ಅನುಭವ: ${worker.experience} ವರ್ಷ", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("ದಿನದ ಕೂಲಿ", fontSize = 11.sp, color = Color.Gray)
                    Text("₹${worker.dailyRate}", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Color(0xFF2E7D32))
                }
                Button(
                    onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, "tel:${worker.phone}".toUri())) },
                    colors = ButtonDefaults.buttonColors(containerColor = callButtonColor, contentColor = Color.White),
                    enabled = worker.available,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Call, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("ಕರೆ ಮಾಡಿ", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}