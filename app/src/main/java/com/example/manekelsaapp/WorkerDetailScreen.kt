package com.example.manekelsaapp

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.manekelsaapp.model.Worker

/**
 * ಇಂಗ್ಲಿಷ್ ವಿವರಗಳನ್ನು ಮತ್ತು ಹೆಸರುಗಳನ್ನು ಕನ್ನಡಕ್ಕೆ ಪರಿವರ್ತಿಸುವ ಫಂಕ್ಷನ್.
 */
private fun translateToKannada(text: String?): String {
    if (text.isNullOrEmpty()) return "ಮಾಹಿತಿ ಇಲ್ಲ"

    val translationMap = mapOf(
        // --- ಹೆಸಾರುಗಳು ---
        "Kavitha" to "ಕವಿತಾ",
        "Kushi" to "ಖುಷಿ",
        "Manjunath" to "ಮಂಜುನಾಥ್",
        "Girish" to "ಗಿರೀಶ್",
        "Suresh" to "ಸುರೇಶ್",
        "santosh" to "ಸಂತೋಷ್",
        "Arif" to "ಆರಿಫ್",
        "Ravi" to "ರವಿ",
        "Lokesh" to "ಲೋಕೇಶ್",
        "Praveen" to "ಪ್ರವೀಣ್",
        "Manjunath" to "ಮಂಜುನಾಥ್",
        "Kiran" to "ಕಿರಣ್",
        "Mahesh" to "ಮಹೇಶ್",

        // --- ಪ್ರದೇಶಗಳು (Locations) ---
        "Peenya" to "ಪೀಣ್ಯ",
        "Dasarahalli" to "ದಾಸರಹಳ್ಳಿ",
        "Hosakote" to "ಹೊಸಕೋಟೆ",
        "Whitefield" to "ವೈಟ್‌ಫೀಲ್ಡ್",
        "Koramangala" to "ಕೋರಮಂಗಲ",
        "Electronic City" to "ಎಲೆಕ್ಟ್ರಾನಿಕ್ ಸಿಟಿ",
        "Jayanagar" to "ಜಯನಗರ",
        "Indiranagar" to "ಇಂದಿರಾನಗರ",
        "Yelahanka" to "ಯಲಹಂಕ",
        "BTM Layout" to "ಬಿಟಿಎಂ ಲೇಔಟ್",
        "Banashankari" to "ಬನಶಂಕರಿ",
        "Majestic" to "ಮೆಜೆಸ್ಟಿಕ್",
        "Rajajinagar" to "ರಾಜಾಜಿನಗರ",
        "Malleshwaram" to "ಮಲ್ಲೇಶ್ವರಂ",
        "Yeshwanthpur" to "ಯಶವಂತಪುರ",
        "Kengeri" to "ಕೆಂಗೇರಿ",
        "Marathahalli" to "ಮರತಹಳ್ಳಿ",
        "Vijayanagar" to "ವಿಜಯನಗರ",
        "HSR Layout" to "ಎಚ್ಎಸ್ಆರ್ ಲೇಔಟ್",


        // --- ಕೆಲಸದ ಲಭ್ಯತೆ (Time/Availability) ---
        "Full time" to "ಪೂರ್ಣ ಸಮಯ",
        "Part time" to "ಭಾಗಶಃ ಸಮಯ",
        "Weekend service" to "ವಾರಾಂತ್ಯ ಸೇವೆ",
        "Emergency service" to "ತುರ್ತು ಸೇವೆ ಲಭ್ಯ",
        "8 AM - 6 PM" to "ಬೆಳಿಗ್ಗೆ 8 ರಿಂದ ಸಂಜೆ 6",
        "10 AM - 10 PM" to "ಬೆಳಿಗ್ಗೆ 10 ರಿಂದ ಸಂಜೆ 10",
        "10 AM - 7 PM" to "ಬೆಳಿಗ್ಗೆ 10 ರಿಂದ ಸಂಜೆ 7",
        "11 AM - 9 PM" to "ಬೆಳಿಗ್ಗೆ 11 ರಿಂದ ಸಂಜೆ 9",
        "24 Hours" to "24 ಗಂಟೆಗಳ ಸೇವೆ",

        // --- ಕೆಲಸಗಾರರ ವೃತ್ತಿ (Skills) ---
        "Painter" to "ಪೇಂಟರ್",
        "Carpenter" to "ಬಡಗಿ",
        "Driver" to "ಚಾಲಕ",
        "Electrician" to "ಎಲೆಕ್ಟ್ರಿಷಿಯನ್",
        "Plumber" to "ಪ್ಲಂಬರ್",
        "Gardener" to "ತೋಟಗಾರ",
        "Cook" to "ಅಡುಗೆ ಕೆಲಸ",
        "Maid" to "ಮನೆಗೆಲಸ",
        "House Maid" to "ಮನೆಯ ಸಹಾಯಕಿ",
        "AC Mechanic" to "ಎಸಿ ಮೆಕಾನಿಕ್",
        "Babysitter" to "ಮಕ್ಕಳ ನೋಡಿಕೊಳ್ಳುವವರು",
        "Delivery Boy" to "ಡೆಲಿವರಿ ಬಾಯ್",

        // --- ವಿವರಣೆಗಳು ---
        "Professional painting services for homes and offices with high-quality finishing." to "ಮನೆ ಮತ್ತು ಕಚೇರಿಗಳಿಗೆ ಅತ್ಯುತ್ತಮ ಬಣ್ಣದ ಕೆಲಸ ಮಾಡಿಕೊಡಲಾಗುವುದು.",
        "Experienced in furniture making and repair services." to "ಪೀಠೋಪಕರಣಗಳ ತಯಾರಿ ಮತ್ತು ರಿಪೇರಿ ಕೆಲಸದಲ್ಲಿ ಪರಿಣತಿ ಹೊಂದಿದ್ದೇನೆ.",
        "Reliable driver known for safe driving and punctuality." to "ಸುರಕ್ಷಿತ ಚಾಲನೆ ಮತ್ತು ಸಮಯ ಪ್ರಜ್ಞೆಗೆ ಹೆಸರಾದ ಚಾಲಕರು.",
        "Expert in wiring and repair of all electrical appliances." to "ವೈರಿಂಗ್ ಮತ್ತು ಎಲ್ಲಾ ಎಲೆಕ್ಟ್ರಿಕಲ್ ಉಪಕರಣಗಳ ರಿಪೇರಿ ಮಾಡಲಾಗುವುದು.",
        "Skilled plumber specializing in tap repairs and pipeline installation." to "ನಲ್ಲೀ ರಿಪೇರಿ ಮತ್ತು ಪೈಪ್ ಲೈನ್ ಅಳವಡಿಕೆಯಲ್ಲಿ ನುರಿತ ಕೆಲಸಗಾರ.",
        "Specialist in plant care and beautiful garden maintenance." to "ಗಿಡಗಳ ಆರೈಕೆ ಮತ್ತು ಸುಂದರ ತೋಟದ ನಿರ್ವಹಣೆಯಲ್ಲಿ ಪರಿಣತಿ.",
        "Can help with grocery shopping or parcel delivery. Owns a bike and knows local routes." to "ತರಕಾರಿ ಖರೀದಿ ಅಥವಾ ಪಾರ್ಸೆಲ್ ಡೆಲಿವರಿ ಕೆಲಸಗಳಲ್ಲಿ ಸಹಾಯ ಮಾಡಬಹುದು. ಬೈಕ್ ಹೊಂದಿದ್ದು ಸ್ಥಳೀಯ ಮಾರ್ಗಗಳ ಬಗ್ಗೆ ಉತ್ತಮ ತಿಳುವಳಿಕೆ ಇದೆ.",
        "Expert in interior/exterior painting and waterproof coating. Very neat work." to "ಒಳಾಂಗಣ/ಹೊರಾಂಗಣ ಪೇಂಟಿಂಗ್ ಹಾಗೂ ವಾಟರ್‌ಪ್ರೂಫ್ ಕೋಟಿಂಗ್‌ನಲ್ಲಿ ಪರಿಣತಿ ಹೊಂದಿದ್ದು, ಅತ್ಯಂತ ಸ್ವಚ್ಛ ಮತ್ತು ನಿಖರವಾದ ಕೆಲಸ ಮಾಡಲಾಗುತ್ತದೆ.",
        "Specialist in split AC servicing and gas filling. Fast service within 2 hours." to "ಸ್ಪ್ಲಿಟ್ ಎಸಿ ಸರ್ವಿಸಿಂಗ್ ಮತ್ತು ಗ್ಯಾಸ್ ಫಿಲ್ಲಿಂಗ್‌ನಲ್ಲಿ ಪರಿಣತಿ ಹೊಂದಿದ್ದು, 2 ಗಂಟೆಗಳೊಳಗೆ ವೇಗವಾದ ಸೇವೆ ನೀಡಲಾಗುತ್ತದೆ.",
        "Cleans rooms, washes utensils, sweeps, mops floors, and helps maintain the house daily." to "ಕೊಠಡಿಗಳನ್ನು ಸ್ವಚ್ಛಗೊಳಿಸುವುದು, ಪಾತ್ರೆ ತೊಳೆಯುವುದು, ಮನೆ ಒರೆಹಾಕುವುದು ಮತ್ತು ದೈನಂದಿನ ಮನೆ ಕೆಲಸಗಳಲ್ಲಿ ಸಹಾಯ ಮಾಡುವುದು.",

        "Prepares meals, cuts vegetables, manages kitchen cleanliness, and may plan daily menus." to "ಊಟ ತಯಾರಿಸುವುದು, ತರಕಾರಿ ಕತ್ತರಿಸುವುದು, ಅಡಿಗೆಮನೆ ಸ್ವಚ್ಛತೆ ನೋಡಿಕೊಳ್ಳುವುದು ಮತ್ತು ದಿನನಿತ್ಯದ ಮೆನು ಸಿದ್ಧಪಡಿಸುವುದು.",

        "Takes care of children, helps with feeding, homework, playtime, and safety." to "ಮಕ್ಕಳನ್ನು ನೋಡಿಕೊಳ್ಳುವುದು, ಊಟ ಮಾಡಿಸುವುದು, ಗೃಹಪಾಠದಲ್ಲಿ ಸಹಾಯ ಮಾಡುವುದು, ಆಟವಾಡಿಸುವುದು ಮತ್ತು ಸುರಕ್ಷತೆಯನ್ನು ಗಮನಿಸುವುದು."

        )





    val key = text.trim()
    return translationMap[key] ?:
    translationMap.entries.find { it.key.equals(key, ignoreCase = true) }?.value ?:
    text
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerDetailScreen(
    workerId: String,
    viewModel: ManeKelsaViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val workers by viewModel.workers
    val worker = workers.find { it.id == workerId }

    if (worker == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF1A0933))
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        TopAppBar(
            title = { Text("ಕೆಲಸಗಾರರ ಪೂರ್ಣ ವಿವರ", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1A0933),
                titleContentColor = Color.White
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = worker.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFF1A0933), CircleShape),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = Modifier.size(24.dp).padding(2.dp),
                    shape = CircleShape,
                    color = if (worker.available) Color(0xFF00C853) else Color.Red,
                    border = BorderStroke(2.dp, Color.White)
                ) {}
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = translateToKannada(worker.name),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1A0933)
            )

            Surface(color = Color(0xFFFFC107), shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = "  ${translateToKannada(worker.skill)}  ",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ನನ್ನ ಬಗ್ಗೆ:", fontWeight = FontWeight.Bold, color = Color(0xFF1A0933), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = translateToKannada(worker.description),
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ಮಾಹಿತಿ", fontWeight = FontWeight.Bold, color = Color(0xFF1A0933), fontSize = 16.sp)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))

                    DetailRow(Icons.Default.Payments, "ದಿನದ ಕೂಲಿ:", "₹${worker.dailyRate}")
                    DetailRow(Icons.Default.Badge, "ಅನುಭವ:", "${worker.experience} ವರ್ಷ")
                    DetailRow(Icons.Default.LocationOn, "ಪ್ರದೇಶ:", translateToKannada(worker.location))

                    // FIXED: Now uses the actual availability field from the worker object instead of timing
                    DetailRow(
                        Icons.Default.AccessTime,
                        "ಕೆಲಸದ ಲಭ್ಯತೆ:",
                        if (worker.available) translateToKannada(worker.availability) else "ಲಭ್ಯವಿಲ್ಲ"
                    )

                    DetailRow(
                        Icons.Default.Schedule,
                        "ಸ್ಥಿತಿ:",
                        if (worker.available) "ಕೆಲಸಕ್ಕೆ ಲಭ್ಯವಿದ್ದಾರೆ" else "ಈಗ ಲಭ್ಯವಿಲ್ಲ"
                    )

                    DetailRow(Icons.Default.Star, "ರೇಟಿಂಗ್:", "${worker.rating} / 5.0")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, "tel:${worker.phone}".toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(58.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (worker.available) Color(0xFF2E7D32) else Color.Gray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = worker.available
            ) {
                Icon(Icons.Default.Call, null, tint = Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = if (worker.available) "ಕರೆ ಮಾಡಿ" else "ಈಗ ಲಭ್ಯವಿಲ್ಲ",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = Color(0xFFFFC107)
                )
            }

            Text(
                text = if (worker.available) "ಕೆಲಸಗಾರರು ಕೆಲಸಕ್ಕೆ ಲಭ್ಯವಿದ್ದಾರೆ" else "ಕ್ಷಮಿಸಿ, ಇವರು ಈಗ ಲಭ್ಯವಿಲ್ಲ",
                color = if (worker.available) Color(0xFF2E7D32) else Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = CircleShape,
            color = Color(0xFF1A0933).copy(alpha = 0.05f)
        ) {
            Icon(icon, null, tint = Color(0xFF1A0933), modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}