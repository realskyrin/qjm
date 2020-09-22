package com.skyrin.qjm.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.skyrin.qjm.data.AppDatabase
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.utils.SMS_DATA_FILENAME
import com.skyrin.qjm.utils.TAG
import kotlinx.coroutines.coroutineScope

class SmsWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(SMS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val smsType = object : TypeToken<List<Sms>>() {}.type
                    val smsList: List<Sms> = Gson().fromJson(jsonReader, smsType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.smsDao().insert(smsList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}