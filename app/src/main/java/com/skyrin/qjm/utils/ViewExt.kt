package com.skyrin.qjm.utils

import android.annotation.SuppressLint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

fun View.gone() {
    if (this.isGone) return
    this.visibility = View.GONE
}

fun View.invisible() {
    if (this.isInvisible) return
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    if (this.isVisible) return
    this.visibility = View.VISIBLE
}

fun View.setMargins(size: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(size)
}

fun View.setMarginTop(size: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = size
}

fun View.setMarginRight(size: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = size
}

fun View.setMarginBottom(size: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = size
}

fun View.setMarginLeft(size: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = size
}

@SuppressLint("ClickableViewAccessibility")
fun View.addClickScale(scale: Float = 0.95f, duration: Long = 150) {
    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
            }
        }
        this.onTouchEvent(event)
    }
}