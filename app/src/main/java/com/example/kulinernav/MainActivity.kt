package com.example.kulinernav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kulinernav.navigation.KulinerNavHost
import com.example.kulinernav.ui.theme.KulinerNavTheme

/**
 * MainActivity hanya memasang tema dan NavHost.
 * Seluruh logic ada di package navigation, ui, dan data.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KulinerNavTheme {
                KulinerNavHost()
            }
        }
    }
}
