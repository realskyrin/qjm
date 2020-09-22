package com.skyrin.qjm.ui.page

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.core.view.marginBottom
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.skyrin.architecthure.ui.page.BaseFragment
import com.skyrin.qjm.BR
import com.skyrin.qjm.R
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.msg.SMSService
import com.skyrin.qjm.ui.callback.SharedViewModel
import com.skyrin.qjm.ui.page.adapter.ReadSmsAdapter
import com.skyrin.qjm.ui.page.adapter.UnReadSmsAdapter
import com.skyrin.qjm.ui.state.MainFragmentViewModel
import com.skyrin.qjm.utils.*
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.random.Random

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun initViewModel() {
        viewModel = getFragmentViewModel(MainFragmentViewModel::class.java)
        sharedViewModel = appViewModelProvider.get(SharedViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        val unReadSmsAdapter = UnReadSmsAdapter(requireContext())
        val readSmsAdapter = ReadSmsAdapter(requireContext())

        unReadSmsAdapter.setOnItemLongClickListener { item, _ ->
            showURLCActionDialog(item)
        }

        readSmsAdapter.setOnItemLongClickListener { item, _ ->
            showRLCActionDialog(item)
        }

        return DataBindingConfig(R.layout.fragment_main, BR.vm, viewModel)
            .addBindingParam(BR.unReadSmsAdapter, unReadSmsAdapter)
            .addBindingParam(BR.readSmsAdapter, readSmsAdapter)
    }

    private fun showURLCActionDialog(item: Sms?) {
        if (item == null) return
        MaterialDialog(requireContext()).show {
            listItems(R.array.item_unread_msg_action) { dialog, index, text ->
                when (index) {
                    0 -> {
                        copyText2Clipboard(item.body)
                        showShortToast("已$text")
                    }
                    1 -> {
                        item.ifRead = SMS_READ
                        viewModel.smsRequest.update(item)
                        showShortToast("已$text")
                    }
                    2 -> {
                        shareText2OtherApp(requireContext(), "${text}到：", item.body!!)
                    }
                    3 -> {
                        showBottomActionDialog(
                            requireContext(),
                            "确定要删除该消息吗？",
                            "删除",
                            "取消"
                        ) {
                            viewModel.smsRequest.delete(item)
                            showShortToast("已$text")
                        }
                    }
                }
            }
        }
    }

    private fun showRLCActionDialog(item: Sms?) {
        if (item == null) return
        MaterialDialog(requireContext()).show {
            listItems(R.array.item_read_msg_action) { _, index, text ->
                when (index) {
                    0 -> {
                        copyText2Clipboard(item.body)
                        showShortToast("已$text")
                    }
                    1 -> {
                        item.ifRead = SMS_UNREAD
                        viewModel.smsRequest.update(item)
                        showShortToast("已$text")
                    }
                    2 -> {
                        shareText2OtherApp(requireContext(), "${text}到：", item.body!!)
                    }
                    3 -> {
                        showBottomActionDialog(
                            requireContext(),
                            "确定要删除该消息吗？",
                            "删除",
                            "取消"
                        ) {
                            viewModel.smsRequest.delete(item)
                            showShortToast("已$text")
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        AndPermission.with(this)
            .runtime()
            .permission(
                Permission.READ_SMS,
                Permission.RECEIVE_SMS
            )
            .onGranted {
                // all permissions are granted and we can load local sms.
                sharedViewModel.onAllPermissionGranted.value = true
            }
            .onDenied {
                // user is prompted to apply for authorization when permission is not granted.
                sharedViewModel.onAllPermissionGranted.value = false
            }
            .start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: >>>")

        viewModel.smsRequest.requestSmsList(true).observe(viewLifecycleOwner,
            Observer<List<Sms>> {
                Log.d(TAG, "onViewCreated: update readSmsList")
                viewModel.readSmsList.postValue(it)
            })
        viewModel.smsRequest.requestSmsList(false).observe(viewLifecycleOwner,
            Observer<List<Sms>> {
                Log.d(TAG, "onViewCreated: update unreadSmsList")
                viewModel.unreadSmsList.postValue(it)
            })

        sharedViewModel.onAllPermissionGranted.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onPermissionChanged: onAllPermissionGranted == $it")

            if (it) {
                cl_permission.gone()
                view_pager.visible()
            } else {
                cl_permission.visible()
                view_pager.gone()
            }
        })

        sdv.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_del_all,
                R.drawable.ic_baseline_delete_24
            )
                .setLabel("删除全部")
                .setFabImageTintColor(resources.getColor(R.color.colorPrimary))
                .setLabelColor(resources.getColor(R.color.colorPrimary))
                .setLabelBackgroundColor(resources.getColor(R.color.colorAccent))
                .setFabBackgroundColor(resources.getColor(R.color.colorAccent))
                .create()
        )
        sdv.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_check_list,
                R.drawable.ic_baseline_playlist_add_check_24
            ).setLabel("选择")
                .setFabImageTintColor(resources.getColor(R.color.colorPrimary))
                .setLabelColor(resources.getColor(R.color.colorPrimary))
                .setLabelBackgroundColor(resources.getColor(R.color.colorAccent))
                .setFabBackgroundColor(resources.getColor(R.color.colorAccent))
                .create()
        )
        sdv.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_settings,
                R.drawable.ic_baseline_settings_24
            ).setLabel("设置")
                .setFabImageTintColor(resources.getColor(R.color.colorPrimary))
                .setLabelColor(resources.getColor(R.color.colorPrimary))
                .setLabelBackgroundColor(resources.getColor(R.color.colorAccent))
                .setFabBackgroundColor(resources.getColor(R.color.colorAccent))
                .create()
        )
        sdv.setOnActionSelectedListener {
            when (it.id) {
                R.id.fab_settings -> {
                }
                R.id.fab_check_list -> {
                }
                R.id.fab_del_all -> {
                    showBottomActionDialog(
                        requireContext(),
                        "确定要删除所有消息吗？",
                        "删除",
                        "取消"
                    ) {
                        viewModel.smsRequest.deleteAllSms()
                        showShortToast("已删除")
                    }
                }
            }
            false
        }

        btn_go_settings.setOnClickListener {
            AndPermission.with(this)
                .runtime()
                .setting()
                .start(REQ_CODE_PERMISSION);
        }

        fab_add.setOnClickListener {
            val sms = Sms(
                uid = 0,
                sender = "1024",
                date = SystemClock.currentThreadTimeMillis(),
                body = "body",
                code = "233333",
                codeType = if (Random.nextInt(2) > 0) "提货码" else "取件码",
                brand = "顺丰速运",
                ifRead = 0,
                simId = Random.nextInt(1, 3).toString(),
                remarks = "123123"
            )
            viewModel.smsRequest.insert(sms)
        }
//        fab_add.gone()
        fab_add.setMargins(16.dp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_PERMISSION -> {
                sharedViewModel.onAllPermissionGranted.value = AndPermission.hasPermissions(
                    this,
                    Permission.READ_SMS,
                    Permission.RECEIVE_SMS
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.startService(Intent(context, SMSService::class.java))
        Log.d(TAG, "onCreate: >>>")
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.stopService(Intent(context, SMSService::class.java))
        Log.d(TAG, "onDestroy: >>>")
    }
}