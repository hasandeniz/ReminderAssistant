package com.hasandeniz.reminderassistant

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hasandeniz.reminderassistant.notify.NotificationUtils
import io.karn.notify.Notify

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        notificationUtils.getManager().notify(150, notification)
    }


}