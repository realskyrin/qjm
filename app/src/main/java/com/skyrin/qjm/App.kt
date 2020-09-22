package com.skyrin.qjm

import com.kunminx.architecture.BaseApplication
import com.skyrin.architecthure.utils.Utils

class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}