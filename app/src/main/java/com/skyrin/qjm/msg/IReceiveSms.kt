package com.skyrin.qjm.msg

import com.skyrin.qjm.data.bean.Sms

interface IReceiveSms {
    fun onReceiveSms(sms: Sms)
}