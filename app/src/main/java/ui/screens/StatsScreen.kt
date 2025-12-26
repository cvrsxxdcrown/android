package com.example.cardgame500.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

import com.example.cardgame500.data.database.AppDatabase
import com.example.cardgame500.data.entity.GameResult

@Composable
fun StatsScreen() {

    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val dao = remember { database.gameResultDao() }
    val scope = rememberCoroutineScope()

    var results by remember { mutableStateOf<List<GameResult>>(emptyList()) }

    LaunchedEffect(Unit) {
        results = dao.getAll()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Статистика игр",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(results) { result ->
                StatItem(result)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val text = buildString {
                    append("Мои результаты в игре 500:\n\n")
                    results.forEach {
                        append("Очки: ${it.score}, ")
                        append("Дата: ${formatDate(it.date)}\n")
                    }
                }

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }

                context.startActivity(
                    Intent.createChooser(intent, "Поделиться результатами")
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться результатами")
        }
    }
}

@Composable
private fun StatItem(result: GameResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Очки: ${result.score}")
            Text("Дата: ${formatDate(result.date)}")
        }
    }
}

private fun formatDate(time: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(time))
}
