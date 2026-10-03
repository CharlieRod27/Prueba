package com.example.tarjetaperfil
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.annotation.DrawableRes
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

// Colores tomados de los diseños sugeridos
private val CardGray = Color(0xFFEDEDED)
private val MediumGray = Color(0xFFD9D9D9)
private val BorderBlue = Color(0xFF2196F3)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileCard(
            title = "usuario1",
            subtitle = "Apellidos\nname_tag",
            imageRes = R.drawable.profile1
        )
        ProfileCard(
            title = "Charlie_twenty_seven",
            subtitle = "texto plano\ndale a like",
            imageRes = R.drawable.profile2
        )
    }
}

/* ---------- Avatar circular reutilizable (Box + Image) ---------- */
@Composable
fun Avatar(size: Dp, @DrawableRes imageRes: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Foto de perfil",
        contentScale = ContentScale.Crop,   // llena el círculo sin deformar la foto
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(4.dp, Color.White, CircleShape)
    )
}

/* ---------- Tarjeta 2: vertical (Column + Box con encabezado) ---------- */
@Composable
fun ProfileCard(
    title: String,
    subtitle: String,
    @DrawableRes imageRes: Int
) {
    var likes by remember { mutableIntStateOf(0) }
    var liked by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var sentMessage by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Encabezado con imagen + avatar superpuesto
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(MediumGray)
                        .align(Alignment.TopCenter)
                ) {
                    Text(
                        text = "Image",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                    )
                }
                Avatar(
                    size = 100.dp,
                    imageRes = imageRes,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            Text(
                text = title,   // antes: "Title of Card"
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = if (sentMessage.isEmpty()) subtitle else "Mensaje: $sentMessage",  // antes: "Text text and\nmore subtext"
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            // Icon interactivo: like con contador
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    liked = !liked
                    likes += if (liked) 1 else -1
                }) {
                    Icon(
                        imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Me gusta",
                        tint = if (liked) Color.Red else Color.Gray
                    )
                }
                Text("$likes", color = Color.Black)
            }

            // TextField con estado: escribir un mensaje
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Escribe un mensaje") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = { message = ""; sentMessage = "" }) {
                    Text("Limpiar", color = Color.Gray)
                }
                Button(
                    onClick = { sentMessage = message },
                    enabled = message.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = MediumGray)
                ) {
                    Text("Enviar", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme { ProfileScreen() }
}