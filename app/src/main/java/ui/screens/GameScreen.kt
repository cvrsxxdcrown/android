package com.example.cardgame500.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.cardgame500.data.database.AppDatabase
import com.example.cardgame500.data.entity.GameResult
import com.example.cardgame500.ui.notifications.GameNotification

@Composable
fun GameScreen() {

    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val dao = remember { database.gameResultDao() }
    val scope = rememberCoroutineScope()

    var score by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Игра",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Очки: $score",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { score += 10 }) {
            Text("Добавить 10 очков")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                scope.launch {
                    dao.insert(
                        GameResult(
                            score = score,
                            date = System.currentTimeMillis()
                        )
                    )

                    GameNotification.show(context, score)

                    score = 0
                }
            }
        ) {
            Text("Завершить игру и сохранить")
        }
    }
}
