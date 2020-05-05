package com.github.mag0716.conversationssample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Best practices(https://developer.android.com/guide/topics/ui/bubbles#best_practices)
 *
 * * 継続中のコミュニケーションのためや、ユーザが明示的に要求した場合のみに利用すること
 * * Bubble はユーザが無効化することも可能なので、無効化されたケースも想定しておくこと
 * * Bubble で開いた画面もタスクを保持しているので極力シンプルな機能のみ利用できるようにしておくこと
 *
 */
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
        Handler(Looper.myLooper()!!).postDelayed({
            textView.text = "${textView.text}\nYou:$message"
            sendNotification(message)
        }, 5000)
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
            .setBubbleMetadata(createBubbleMetaData())
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

    /**
     * Bubble の表示タイミング
     *
     * * MessagingStyle を利用していて、Person が追加されている
     * * CATEGORY_CALL の Service.startForeground から呼ばれ、Person が追加されている
     * * アプリがフォアグラウンドの時に Notification が送信された場合
     */
    private fun createBubbleMetaData(): NotificationCompat.BubbleMetadata {
        val intent = Intent(this, MainActivity::class.java)
        val bubbleIntent = PendingIntent.getActivity(this, 0, intent, 0)
        return NotificationCompat.BubbleMetadata.Builder()
            .setDesiredHeight(600)
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_android))
            .setIntent(bubbleIntent)
            //.setAutoExpandBubble(true) // 自動的に Bubble を開く。ユーザ操作きっかけ時に利用することが推奨されている
            //.setSuppressNotification(true) // Bubble 生成時に Notification の表示を抑制する。一般的にはユーザ操作で Bubble が生成された時に利用する
            .build()
    }
}
