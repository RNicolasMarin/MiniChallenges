package com.example.coroutines.and.minichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coroutines.and.minichallenges.february_2025.battery_indicator_ui.BatteryIndicatorUI
import com.example.coroutines.and.minichallenges.ui.theme.MiniChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniChallengesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*ThousandsSeparatorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFEF7FF))
                            .padding(16.dp)
                    )*/
                    BatteryIndicatorUI(
                        70,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE7E9EF))
                            .padding(32.dp)
                    )
                }
            }
        }
    }
}