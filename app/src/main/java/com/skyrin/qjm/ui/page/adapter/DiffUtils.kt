package com.skyrin.qjm.ui.page.adapter

import androidx.recyclerview.widget.DiffUtil
import com.skyrin.qjm.data.bean.Sms

class DiffUtils private constructor() {
    private var mSmsItemCallback: DiffUtil.ItemCallback<Sms>? = null

    val smsItemCallback: DiffUtil.ItemCallback<Sms>
        get() {
            if (mSmsItemCallback == null) {
                mSmsItemCallback = object : DiffUtil.ItemCallback<Sms>() {
                    override fun areItemsTheSame(oldItem: Sms, newItem: Sms): Boolean {
                        return oldItem == newItem
                    }

                    override fun areContentsTheSame(
                        oldItem: Sms,
                        newItem: Sms
                    ): Boolean {
                        return oldItem.body == newItem.body
                    }
                }
            }
            return mSmsItemCallback as DiffUtil.ItemCallback<Sms>
        }

    companion object {
        fun newInstance() = DiffUtils()
    }
}
