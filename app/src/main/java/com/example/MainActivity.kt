package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.StayBrokerApp
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.StayBrokerViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: StayBrokerViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        StayBrokerApp(viewModel = viewModel)
      }
    }
  }
}
