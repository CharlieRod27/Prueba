package com.example.evaluacionp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluacionp.ui.theme.EvaluacionPTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EvaluacionPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaPrincipal(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun PerfilCard() {
    var likes by remember { mutableIntStateOf(0) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen circular (placeholder gris)
            Image(
                painter = painterResource(id = R.drawable.si),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color.White, CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("Texto del usuario", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Mas texto para que el usuario pueda escribir", fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { },
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) { Text("Button", fontSize = 12.sp) }

                    Button(
                        onClick = { likes++ },
                        border = BorderStroke(2.dp, Color(0xFF2196F3)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) { Text("❤ $likes", fontSize = 12.sp, color = Color.White) }
                }
            }
        }
    }
}

@Composable
fun FilaHorizontal() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(10) { index ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFD9D9D9)),
                contentAlignment = Alignment.Center
            ) {
                Text("${index + 1}")
            }
        }
    }
}
@Composable
fun ListaVertical(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Lista Vertical", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(20) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFD9D9D9))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text("Elemento de texto ${index + 1}")
                }
            }
        }
    }
}
@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        PerfilCard()
        FilaHorizontal()
        Spacer(modifier = Modifier.height(16.dp))
        ListaVertical(modifier = Modifier.weight(1f))
    }
}
@Preview(showBackground = true)
@Composable
fun PantallaPreview() {
    EvaluacionPTheme {
        PantallaPrincipal()
    }
}