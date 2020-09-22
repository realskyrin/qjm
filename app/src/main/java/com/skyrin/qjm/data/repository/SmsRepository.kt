package com.skyrin.qjm.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.data.dao.SmsDao

class SmsRepository private constructor(private val smsDao: SmsDao) : ILocalSource.SmsSource {

    override fun getUnreadSms(): LiveData<List<Sms>> {
        return smsDao.getUnreadSms()
    }

    override fun getReadSms(): LiveData<List<Sms>> {
        return smsDao.getReadSms()
    }

    override fun getSmsListWithKeywords(keywords: String): LiveData<List<Sms>> {
        return smsDao.getSmsListWithKeywords(keywords)
    }

    fun getSmsList(ifRead: Boolean): LiveData<List<Sms>> {
        return if (ifRead) getReadSms() else getUnreadSms()
    }

    override fun getSmsList(): LiveData<List<Sms>> {
        return smsDao.getSmsList()
    }

    @WorkerThread
    override suspend fun insert(sms: Sms): Long {
        return smsDao.insert(sms)
    }

    @WorkerThread
    override suspend fun insert(smsList: List<Sms>): List<Long> {
        return smsDao.insert(smsList)
    }

    @WorkerThread
    override suspend fun delete(sms: Sms): Int {
        return smsDao.delete(sms)
    }

    @WorkerThread
    override suspend fun deleteAllSms() {
        return smsDao.deleteAllSms()
    }

    override suspend fun deleteUnreadSms() {
        return smsDao.deleteUnreadSms()
    }

    override suspend fun deleteReadSms() {
        return smsDao.deleteReadSms()
    }

    @WorkerThread
    override suspend fun update(sms: Sms): Int {
        return smsDao.update(sms)
    }

    companion object {
        @Volatile
        private var instance: SmsRepository? = null
        fun getInstance(smsDao: SmsDao) =
            instance ?: synchronized(this) {
                instance ?: SmsRepository(smsDao).also { instance = it }
            }
    }
}