package com.example.myaibuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sceneview.Scene
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AutoWelcomeScreen()
            }
        }
    }
}

@Composable
fun AutoWelcomeScreen() {
    var greetingText by remember { mutableStateOf("Quyoncha uyg'onmoqda...") }

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    val bunnyNode = remember {
        ModelNode(
            modelInstance = modelLoader.createModelInstance("models/bunny.glb")
        ).apply {
            scale = io.github.sceneview.math.Scale(1.5f)
            isEditable = true
        }
    }

    LaunchedEffect(Unit) {
        delay(1500)
        greetingText = "Salom! Men sizning AI do'stingizman. Bugun qanday yordam bera olaman?"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "🐰 My AI Buddy",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Scene(
                modifier = Modifier.fillMaxSize(),
                engine = engine,
                modelLoader = modelLoader,
                materialLoader = materialLoader,
                environmentLoader = environmentLoader,
                childNodes = listOf(bunnyNode)
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF252525)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = greetingText,
                color = Color.Cyan,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}
