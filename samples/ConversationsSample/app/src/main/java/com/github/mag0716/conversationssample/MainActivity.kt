package com.github.mag0716.conversationssample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID = "conversations"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        button.setOnClickListener {
            val message = editText.text.toString()
            sendMessage(message)
            receiveMessage(message)
        }
    }

    private fun sendMessage(message: String) {
        textView.text = "${textView.text}\nI:$message"
    }

    private fun receiveMessage(message: String) {
        textView.text = "${textView.text}\nYou:$message"
        sendNotification(message)
    }

    private fun sendNotification(message: String) {
        val person = Person.Builder()
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_android))
            .setName("You")
            .build()
        val notificationStyle = NotificationCompat.MessagingStyle(person)
            .addMessage(message, System.currentTimeMillis(), person)
            .setGroupConversation(false)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_adb)
            .setContentTitle("new message")
            .setContentText(message)
            .setStyle(notificationStyle)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "conversations",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
