package com.example.manekelsaapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerLoginScreen(
    viewModel: ManeKelsaViewModel,
    navController: NavController,
    onRegisterClick: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D1457)) // High-Contrast Deep Purple
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Large Visual Logo for Accessibility
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ಲಾಗಿನ್ ಮಾಡಿ", // Login
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Large Input Field
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("ನಿಮ್ಮ ಫೋನ್ ಸಂಖ್ಯೆ", color = Color.White) }, // Your Phone Number
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.Yellow
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // MASSIVE LOGIN BUTTON (80dp height for easy tapping)
        Button(
            onClick = {
                val worker = viewModel.workers.value.find { it.phone == phone }
                if (worker != null) {
                    navController.navigate("worker_detail/${worker.id}")
                } else {
                    // Error in Kannada
                    Toast.makeText(context, "ಕ್ಷಮಿಸಿ, ಈ ಸಂಖ್ಯೆ ನೋಂದಾಯಿಸಲ್ಪಟ್ಟಿಲ್ಲ", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), // Bright Orange
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("ಒಳಗೆ ಹೋಗಿ", fontSize = 26.sp, fontWeight = FontWeight.Black) // Enter / Go Inside
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register Link in Yellow for Contrast
        TextButton(onClick = onRegisterClick) {
            Text(
                text = "ಹೊಸ ಖಾತೆ ತೆರೆಯಲು ಇಲ್ಲಿ ಒತ್ತಿ", // Click here to open new account
                color = Color.Yellow,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}