package com.skyrin.qjm.ui.page.adapter

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.skyrin.architecthure.utils.Utils
import com.skyrin.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.skyrin.qjm.R
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.databinding.AdapterUncheckSmsItemBinding
import com.skyrin.qjm.utils.addClickScale
import com.skyrin.qjm.utils.copyText2Clipboard
import com.skyrin.qjm.utils.showShortToast

class UnReadSmsAdapter(context: Context) :
    SimpleDataBindingAdapter<Sms, AdapterUncheckSmsItemBinding>(
        context,
        R.layout.adapter_uncheck_sms_item,
        DiffUtils.newInstance().smsItemCallback
    ) {

    override fun getEmptyView(): Int {
        return R.layout.adapter_empty_view
    }

    override fun onBindItem(
        binding: AdapterUncheckSmsItemBinding,
        item: Sms,
        holder: RecyclerView.ViewHolder?
    ) {
        binding.sms = item
        binding.item.addClickScale()
        binding.btnCopy.addClickScale(scale = 0.8f)
        binding.btnCopy.setOnClickListener {
            copyText2Clipboard(item.code)
            showShortToast("已复制${item.codeType}")
        }
    }
}