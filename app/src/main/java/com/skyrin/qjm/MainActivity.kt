package com.skyrin.qjm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.skyrin.architecthure.ui.page.BaseActivity
import com.skyrin.qjm.ui.callback.SharedViewModel
import com.skyrin.qjm.ui.page.MainFragment
import com.skyrin.qjm.ui.state.MainActivityViewModel
import com.skyrin.qjm.utils.TAG


class MainActivity : BaseActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main, BR.vm, mainActivityViewModel)
    }

    override fun initViewModel() {
        mainActivityViewModel = getActivityViewModel(MainActivityViewModel::class.java)
        sharedViewModel = appViewModelProvider.get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                moveTaskToBack(true)
            }
        }
        onBackPressedDispatcher.addCallback(this,callback)
    }

    fun tapHome(){
//        val intent = Intent(Intent.ACTION_MAIN)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.addCategory(Intent.CATEGORY_HOME)
//        startActivity(intent)
    }
}