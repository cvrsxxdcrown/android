package com.example.cardgame500.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.cardgame500.data.network.RetrofitInstance

private const val PREFS_NAME = "http_settings"
private const val KEY_NAME = "user_name"

@Composable
fun SettingsScreen() {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var name by remember {
        mutableStateOf(prefs.getString(KEY_NAME, "") ?: "")
    }

    var resultText by remember {
        mutableStateOf("Введите имя и выполните HTTP-запрос")
    }

    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "HTTP-запрос (Agify API)",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                prefs.edit().putString(KEY_NAME, it).apply()
            },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultText)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    loading = true
                    try {
                        val response = RetrofitInstance.api.getAge(name.ifBlank { "ivan" })
                        resultText =
                            "Имя: ${response.name}\n" +
                                    "Предполагаемый возраст: ${response.age}\n" +
                                    "Количество записей: ${response.count}"
                    } catch (e: Exception) {
                        resultText = "Ошибка сети"
                    }
                    loading = false
                }
            },
            enabled = !loading && name.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (loading) "Загрузка..." else "Отправить запрос")
        }
    }
}
