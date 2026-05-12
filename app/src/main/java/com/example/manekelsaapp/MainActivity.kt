package com.example.manekelsaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.manekelsaapp.ui.theme.ManeKelsaAppTheme

class MainActivity : ComponentActivity() {

    // REMOVED: The ViewModel is now handled inside the NavGraph
    // to ensure shared state between Login and Registration.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManeKelsaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // FIX: Call NavGraph() without parameters.
                    // It will internally initialize the ManeKelsaViewModel.
                    NavGraph()
                }
            }
        }
    }
}