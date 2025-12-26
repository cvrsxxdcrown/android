package com.example.cardgame500.ui.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cardgame500.R

object GameNotification {

    private const val CHANNEL_ID = "game_results"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Результаты игры",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о завершении игры"
            }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun show(context: Context, score: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Игра завершена")
            .setContentText("Ваш результат: $score очков")
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), notification)
    }
}
