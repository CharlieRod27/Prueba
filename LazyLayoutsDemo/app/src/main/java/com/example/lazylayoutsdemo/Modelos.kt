package com.example.lazylayoutsdemo

import androidx.compose.ui.graphics.Color

// Modelo de datos para los elementos de la LazyColumn
data class Contacto(
    val id: Int,
    val nombre: String,
    val rol: String,
    val color: Color
)

// Modelo de datos para los elementos de la LazyRow
data class Categoria(
    val id: Int,
    val nombre: String,
    val emoji: String
)

// Datos de ejemplo: 20 contactos (superan el alto de la pantalla)
fun generarContactos(): List<Contacto> {
    val nombres = listOf(
        "Ana López", "Carlos Ruiz", "María Torres", "Luis Hernández", "Sofía Díaz",
        "Jorge Ramírez", "Valeria Cruz", "Diego Morales", "Fernanda Ortiz", "Andrés Vega",
        "Camila Reyes", "Ricardo Flores", "Daniela Ríos", "Miguel Castro", "Paola Mendoza",
        "Héctor Silva", "Lucía Navarro", "Pablo Guerrero", "Renata Campos", "Emilio Salazar"
    )
    val roles = listOf(
        "Desarrollador Android", "Diseñadora UX", "Analista de datos", "DevOps",
        "Project Manager", "QA Tester", "Backend Developer", "Soporte TI"
    )
    val colores = listOf(
        Color(0xFF2196F3), Color(0xFF4CAF50), Color(0xFFFF9800),
        Color(0xFF9C27B0), Color(0xFFE91E63), Color(0xFF009688)
    )
    return nombres.mapIndexed { index, nombre ->
        Contacto(
            id = index + 1,
            nombre = nombre,
            rol = roles[index % roles.size],
            color = colores[index % colores.size]
        )
    }
}

// Datos de ejemplo: 15 categorías
fun generarCategorias(): List<Categoria> = listOf(
    Categoria(1, "Android", "🤖"),
    Categoria(2, "Kotlin", "💜"),
    Categoria(3, "Compose", "🎨"),
    Categoria(4, "Python", "🐍"),
    Categoria(5, "Flask", "🧪"),
    Categoria(6, "MySQL", "🗄️"),
    Categoria(7, "Git", "🌿"),
    Categoria(8, "Redes", "🌐"),
    Categoria(9, "Seguridad", "🔒"),
    Categoria(10, "Cloud", "☁️"),
    Categoria(11, "IA", "🧠"),
    Categoria(12, "Datos", "📊"),
    Categoria(13, "Web", "💻"),
    Categoria(14, "Linux", "🐧"),
    Categoria(15, "DevOps", "⚙️")
)
