package com.nabilakhairunnisa0028.asesmen1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nabilakhairunnisa0028.asesmen1.navigation.SetupNavGraph
import com.nabilakhairunnisa0028.asesmen1.ui.theme.Asesmen1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asesmen1Theme {
                SetupNavGraph()
            }
        }
    }
}

