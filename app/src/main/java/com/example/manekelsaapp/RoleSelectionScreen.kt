package com.example.manekelsaapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoleSelectionScreen(onRoleSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D1457)) // ಗಢ ನೇರಳೆ ಬಣ್ಣ (High Contrast Background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ಮನೆ ಕೆಲಸ ಆಪ್",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 60.dp)
        )

        // WORKER BUTTON - ಕೆಲಸಗಾರ
        Button(
            onClick = { onRoleSelected("worker") },
            modifier = Modifier.fillMaxWidth().height(140.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), // ಕಿತ್ತಳೆ ಬಣ್ಣ
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Engineering, "ಕೆಲಸಗಾರ", modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Text("ಕೆಲಸಗಾರ", fontSize = 30.sp, fontWeight = FontWeight.Black)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // RESIDENT BUTTON - ನಿವಾಸಿ
        Button(
            onClick = { onRoleSelected("resident") },
            modifier = Modifier.fillMaxWidth().height(140.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)), // ಹಸಿರು ಬಣ್ಣ
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Home, "ನಿವಾಸಿ", modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Text("ನಿವಾಸಿ", fontSize = 30.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}