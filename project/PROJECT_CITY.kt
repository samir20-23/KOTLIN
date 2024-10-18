package com.example.jebha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jebha.ui.theme.JebhaTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.BorderStroke

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JebhaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun imgChanger(modifier: Modifier = Modifier) {
    var imagePath by remember { mutableStateOf(R.drawable.jebha) }
    var description by remember { mutableStateOf("El Jebha is a beautiful coastal city located in northern Morocco.") }
    var buttonText by remember { mutableStateOf("Change img 2") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(imagePath),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.4f),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
                .border(
                    BorderStroke(2.dp, Color(0x4DFFFFFF))
                ),
            verticalArrangement = Arrangement.Center

        ) {
            Image(
                painter = painterResource(imagePath),
                contentDescription = null,
                modifier = Modifier
                    .size(290.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (imagePath == R.drawable.jebha) {
                        imagePath = R.drawable.jebha2
                        description = " Situated in the Al Hoceima region, it is approximately."
                        buttonText = "Change img 1"
                    } else {
                        imagePath = R.drawable.jebha
                        description = "El Jebha is a beautiful coastal city located in northern Morocco."
                        buttonText = "Change img 2"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CB371)) // Change to your desired color
            ) {
                Text(buttonText)
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    shadow = Shadow(color = Color.Black, offset = Offset(0.2f, 0.2f), blurRadius = 1f)

                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun App(modifier: Modifier = Modifier) {
    imgChanger()
}
