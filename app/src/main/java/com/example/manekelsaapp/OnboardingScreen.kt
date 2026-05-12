package com.example.manekelsaapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D1457)) // Deep Purple Background
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Header Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "ಮನೆ ಕೆಲಸ ಆಪ್‌ಗೆ\nಸ್ವಾಗತ", // Welcome to Mane Kelsa App
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp
            )
            Text(
                text = "ನಿಮ್ಮೂರಿನ ಕೆಲಸಗಾರರನ್ನು ಸುಲಭವಾಗಿ ಹುಡುಕಿ", // Find workers in your area easily
                color = Color.LightGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // 2. Feature Logos/Icons Section (Visual Guide)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OnboardingFeatureItem(
                icon = Icons.Default.Groups,
                text = "ನೂರಾರು ಕೆಲಸಗಾರರು ಲಭ್ಯ" // Hundreds of workers available
            )
            OnboardingFeatureItem(
                icon = Icons.Default.Call,
                text = "ನೇರವಾಗಿ ಕರೆ ಮಾಡಿ ಮಾತನಾಡಿ" // Call directly and talk
            )
            OnboardingFeatureItem(
                icon = Icons.Default.Handshake,
                text = "ಸರಿಯಾದ ಬೆಲೆಗೆ ಕೆಲಸ ಸಿಗಲಿದೆ" // Get work at the right price
            )
        }

        // 3. Action Section
        Button(
            onClick = onFinished,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), // Massive button for easy tapping
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), // Bright Orange
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "ಮುಂದುವರಿಸಿ", // Continue
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }
    }
}

@Composable
fun OnboardingFeatureItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Yellow,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}