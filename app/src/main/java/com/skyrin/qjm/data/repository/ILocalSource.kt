package com.skyrin.qjm.data.repository

import androidx.lifecycle.LiveData
import com.skyrin.architecthure.repository.DataResult
import com.skyrin.qjm.data.bean.Sms

interface ILocalSource {
    interface SmsSource {
        fun getSmsList(): LiveData<List<Sms>>
        fun getUnreadSms(): LiveData<List<Sms>>
        fun getReadSms(): LiveData<List<Sms>>
        fun getSmsListWithKeywords(keywords: String): LiveData<List<Sms>>

        suspend fun insert(sms: Sms): Long
        suspend fun insert(smsList: List<Sms>): List<Long>

        suspend fun delete(sms: Sms): Int
        suspend fun deleteAllSms()
        suspend fun deleteUnreadSms()
        suspend fun deleteReadSms()

        suspend fun update(sms: Sms): Int
    }
}