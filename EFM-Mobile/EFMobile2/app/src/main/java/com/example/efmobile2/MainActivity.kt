package com.example.efmobile2

import android.os.Bundle    
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.efmobile2.ui.screens.MainScreen
import com.example.efmobile2.viewmodel.FormationViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = FormationViewModel()

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}