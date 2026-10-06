package com.example.lazylayoutsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyLayoutsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LazyLayoutsScreen(modifier: Modifier = Modifier) {
    val contactos = remember { generarContactos() }
    val categorias = remember { generarCategorias() }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Estado de selección (null = ninguno seleccionado)
    var contactoSeleccionado by remember { mutableStateOf<Int?>(null) }
    var categoriaSeleccionada by remember { mutableStateOf<Int?>(null) }

    val mostrarBotonSubir by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 3 }
    }

    Box(modifier = modifier.fillMaxSize()) {

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "LazyRow: Categorías",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categorias, key = { it.id }) { categoria ->
                        CategoriaItem(
                            categoria = categoria,
                            seleccionada = categoria.id == categoriaSeleccionada,
                            onClick = {
                                categoriaSeleccionada =
                                    if (categoriaSeleccionada == categoria.id) null else categoria.id
                            }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "LazyColumn: Contactos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(contactos, key = { it.id }) { contacto ->
                ContactoItem(
                    contacto = contacto,
                    seleccionado = contacto.id == contactoSeleccionado,
                    onClick = {
                        contactoSeleccionado =
                            if (contactoSeleccionado == contacto.id) null else contacto.id
                    }
                )
            }
        }

        if (mostrarBotonSubir) {
            Button(
                onClick = { scope.launch { listState.animateScrollToItem(0) } },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("Subir ↑")
            }
        }
    }
}

/* ---------- Item de la LazyRow (con color animado al seleccionar) ---------- */
@Composable
fun CategoriaItem(categoria: Categoria, seleccionada: Boolean, onClick: () -> Unit) {
    val colorFondo by animateColorAsState(
        targetValue = if (seleccionada) Color(0xFFC8E6C9) else Color(0xFFEDEDED),
        animationSpec = tween(durationMillis = 400),
        label = "colorCategoria"
    )

    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Column(
            modifier = Modifier
                .size(width = 100.dp, height = 100.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = categoria.emoji, fontSize = 32.sp)
            Text(
                text = categoria.nombre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

/* ---------- Item de la LazyColumn (3 animaciones) ---------- */
@Composable
fun ContactoItem(contacto: Contacto, seleccionado: Boolean, onClick: () -> Unit) {
    // Estado local: texto visible u oculto
    var expandido by rememberSaveable { mutableStateOf(false) }

    // Animación 1: cambio de color al seleccionar
    val colorFondo by animateColorAsState(
        targetValue = if (seleccionado) Color(0xFFBBDEFB) else Color(0xFFF5F5F5),
        animationSpec = tween(durationMillis = 400),
        label = "colorContacto"
    )

    // Animación 3 (opcional): el avatar crece con efecto rebote al seleccionar
    val escalaAvatar by animateFloatAsState(
        targetValue = if (seleccionado) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "escalaAvatar"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .scale(escalaAvatar)
                        .clip(CircleShape)
                        .background(contacto.color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contacto.nombre.first().toString(),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = contacto.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = contacto.rol,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }

                Text(
                    text = "#${contacto.id}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Animación 2: mostrar / ocultar texto con expansión y desvanecido
            AnimatedVisibility(
                visible = expandido,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Text(
                    text = "${contacto.nombre} trabaja como ${contacto.rol}. " +
                            "Este texto aparece y desaparece con una animación al presionar el botón.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TextButton(onClick = { expandido = !expandido }) {
                Text(if (expandido) "Ocultar texto" else "Mostrar texto")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyLayoutsScreenPreview() {
    MaterialTheme { LazyLayoutsScreen() }
}
