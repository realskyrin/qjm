package com.skyrin.qjm.ui.callback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val onAllPermissionGranted = MutableLiveData<Boolean>()
}