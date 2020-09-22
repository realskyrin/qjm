package com.skyrin.qjm.msg

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.provider.Telephony
import android.util.Log
import com.skyrin.qjm.data.AppDatabase
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.domain.request.SmsRequest
import com.skyrin.qjm.utils.TAG
import kotlinx.coroutines.*
import java.lang.Exception

class SMSService : Service(), IReceiveSms {
    private var mySmsReceiver: SMSReceiver = SMSReceiver()
    private lateinit var appDatabase: AppDatabase
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private lateinit var smsRequest: SmsRequest

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter().apply {
            addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        }
        mySmsReceiver.smsReceiveListener = this
        applicationContext?.registerReceiver(mySmsReceiver, filter)
        appDatabase = AppDatabase.getInstance(applicationContext)
        smsRequest = SmsRequest.getInstance(appDatabase.smsDao(), serviceScope)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            applicationContext?.unregisterReceiver(mySmsReceiver)
        } catch (e: Exception) {
            // already unregistered
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onReceiveSms(sms: Sms) {
        smsRequest.insert(sms)
        Log.d(TAG, "onReceiveSms: $sms")
    }
}