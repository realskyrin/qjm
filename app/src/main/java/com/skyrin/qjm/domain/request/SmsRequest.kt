package com.skyrin.qjm.domain.request

import androidx.lifecycle.LiveData
import com.skyrin.architecthure.domain.request.BaseRequest
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.data.dao.SmsDao
import com.skyrin.qjm.data.repository.SmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsRequest private constructor(
    private val smsDao: SmsDao,
    private val scope: CoroutineScope
) : BaseRequest() {

    fun insert(sms: Sms) {
        scope.launch(Dispatchers.IO) {
            SmsRepository.getInstance(smsDao).insert(sms)
        }
    }

    fun delete(sms: Sms) {
        scope.launch(Dispatchers.IO) {
            SmsRepository.getInstance(smsDao).delete(sms)
        }
    }

    fun deleteAllSms(){
        scope.launch(Dispatchers.IO) {
            SmsRepository.getInstance(smsDao).deleteAllSms()
        }
    }

    fun update(sms: Sms){
        scope.launch(Dispatchers.IO) {
            SmsRepository.getInstance(smsDao).update(sms)
        }
    }

    fun requestSmsList(ifRead: Boolean): LiveData<List<Sms>> {
       return SmsRepository.getInstance(smsDao).getSmsList(ifRead)
    }

    companion object {
        @Volatile
        private var instance: SmsRequest? = null
        fun getInstance(smsDao: SmsDao, scope: CoroutineScope) =
            instance ?: synchronized(this) {
                instance ?: SmsRequest(smsDao, scope).also { instance = it }
            }
    }
}