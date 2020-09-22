package com.skyrin.qjm.msg

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.google.gson.Gson
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.utils.TAG
import com.skyrin.qjm.utils.getAutoTime
import com.skyrin.qjm.utils.isQjmSms
import kotlin.random.Random

class SMSReceiver : BroadcastReceiver() {
    var smsReceiveListener: IReceiveSms? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {

            val cursor = context?.contentResolver?.query(
                Telephony.Sms.CONTENT_URI,
                null,
                Telephony.Sms.TYPE + " = ?",
                arrayOf(
                    Telephony.Sms.MESSAGE_TYPE_INBOX.toString()
                ), // search for msg from inbox
                Telephony.Sms.DATE + " DESC LIMIT 1"  // search for the fist record in descending time order
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY))
//                    if (!body.isQjmSms()) continue
                    val dateInTimeInMillis =
                        cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE))
                    val date = getAutoTime(dateInTimeInMillis.toLong())
                    val address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS))
//                    val simId = cursor.getString(cursor.getColumnIndex("sim_id")) // compatibility issues

                    Sms(
                        uid = 0,
                        sender = address,
                        date = dateInTimeInMillis.toLong(),
                        body = body,
                        code = "233333",
                        codeType = if (Random.nextInt(2) > 0) "提货码" else "取件码",
                        brand = "顺丰速运",
                        ifRead = 0,
                        simId = Random.nextInt(1, 3).toString(),
                        remarks = "123123"
                    ).also {
                        smsReceiveListener?.onReceiveSms(it)
                    }
                }
                cursor.close()
            }
        }
    }
}