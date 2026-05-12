package com.example.manekelsaapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String,
    userPhone: String,
    imageUrl: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ನನ್ನ ಪ್ರೊಫೈಲ್",
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ಹಿಂದಕ್ಕೆ",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black, // High Contrast Black
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.White // Pure White Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // PROFILE IMAGE - Thick black border for contrast
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            // USER INFO - Bold Black Text
            Text(
                text = userName,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Text(
                text = userPhone,
                fontSize = 18.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // HIGH CONTRAST ROWS
            HighContrastOption(Icons.Default.Favorite, "ನನ್ನ ಮೆಚ್ಚಿನ ಕೆಲಸಗಾರರು")
            HighContrastOption(Icons.Default.History, "ಕೆಲಸದ ಇತಿಹಾಸ")
            HighContrastOption(Icons.Default.Language, "ಭಾಷೆ: ಕನ್ನಡ")
            HighContrastOption(Icons.Default.PrivacyTip, "ಗೌಪ್ಯತಾ ನೀತಿ")

            Spacer(modifier = Modifier.weight(1f))

            // LOGOUT BUTTON - Bright Red for high visibility
            Button(
                onClick = { /* Logout Logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD50000)),
                shape = RoundedCornerShape(8.dp) // Sharper corners for modern look
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "ಖಾತೆಯಿಂದ ಹೊರಬನ್ನಿ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun HighContrastOption(icon: ImageVector, title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp)), // Black border
        color = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Black)
        }
    }
}