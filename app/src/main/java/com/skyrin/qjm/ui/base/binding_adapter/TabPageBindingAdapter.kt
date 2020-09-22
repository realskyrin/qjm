package com.skyrin.qjm.ui.base.binding_adapter;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.skyrin.architecthure.ui.adapter.CommonViewPagerAdapter;
import com.skyrin.qjm.R;

@BindingAdapter(value = ["initTabAndPage"], requireAll = false)
fun initTabAndPage(tabLayout: TabLayout, initTabAndPage: Boolean) {
    val count = tabLayout.tabCount
    val title = arrayOfNulls<String>(count)
    for (i in 0 until count) {
        tabLayout.getTabAt(i)?.text.let {
            title[i] = it.toString()
        }
    }
    val viewPager: ViewPager = tabLayout.rootView.findViewById(R.id.view_pager)
    viewPager.adapter = CommonViewPagerAdapter(count, false, title)
    tabLayout.setupWithViewPager(viewPager)
}

@BindingAdapter(value = ["tabSelectedListener"], requireAll = false)
fun tabSelectedListener(tabLayout: TabLayout, listener: OnTabSelectedListener?) {
    tabLayout.addOnTabSelectedListener(listener)
}