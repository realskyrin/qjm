package com.skyrin.qjm.ui.state

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyrin.architecthure.utils.Utils
import com.skyrin.qjm.data.AppDatabase
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.domain.request.SmsRequest

class MainFragmentViewModel : ViewModel() {

    //TODO 此处用于绑定的状态，使用 LiveData 而不是 ObservableField，主要是考虑到 ObservableField 具有防抖的特性，不适合该场景。
    //如果这么说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350
    val unreadSmsList = MutableLiveData<List<Sms>>()

    val readSmsList = MutableLiveData<List<Sms>>()

    val notifyCurrentListChanged = MutableLiveData<Boolean>()

    val initTabAndPage = ObservableBoolean()

    private val appDatabase = AppDatabase.getInstance(Utils.getApp().applicationContext)

    val smsRequest = SmsRequest.getInstance(appDatabase.smsDao(), viewModelScope)

    init {
        initTabAndPage.set(true)
    }
}