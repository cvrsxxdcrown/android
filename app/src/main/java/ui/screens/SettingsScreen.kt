package com.example.cardgame500.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import com.example.cardgame500.data.network.RetrofitInstance

@Composable
fun SettingsScreen() {

    // 🔹 имя, вводимое пользователем
    var name by remember { mutableStateOf("") }

    // 🔹 текст результата
    var resultText by remember {
        mutableStateOf("Введите имя и нажмите кнопку")
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

        // ПОЛЕ ВВОДА ИМЕНИ
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultText)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    resultText = "Имя не может быть пустым"
                    return@Button
                }

                scope.launch {
                    loading = true
                    try {

                        val response = RetrofitInstance.api.getAge(name)

                        resultText =
                            "Имя: ${response.name}\n" +
                                    "Предполагаемый возраст: ${response.age}\n" +
                                    "Количество записей: ${response.count}"

                    } catch (e: Exception) {
                        resultText = "Ошибка сети. Проверьте подключение к интернету."
                    }
                    loading = false
                }
            },
            enabled = !loading
        ) {
            Text(if (loading) "Загрузка..." else "Отправить запрос")
        }
    }
}
