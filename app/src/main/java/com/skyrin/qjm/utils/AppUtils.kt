package com.skyrin.qjm.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.message.DialogMessageSettings
import com.skyrin.architecthure.utils.Utils

fun showShortToast(text: String){
    getToast(text,Toast.LENGTH_SHORT).show()
}

fun showLongToast(text: String){
    getToast(text,Toast.LENGTH_LONG).show()
}

fun showShortToast(stringRes: Int){
    getToast(Utils.getApp().getString(stringRes),Toast.LENGTH_SHORT).show()
}

fun showLongToast(stringRes: Int){
    getToast(Utils.getApp().getString(stringRes),Toast.LENGTH_LONG).show()
}

private fun getToast(text: String,duration: Int) : Toast{
    val toast = Toast.makeText(Utils.getApp().applicationContext,null,duration)
    toast.setText(text)
    return toast
}

fun showBottomActionDialog(
    context: Context,
    message: String,
    pBtnText: String,
    nBtnText: String,
    action: () -> Unit
) {
    MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
        message(text = message)
        positiveButton(text = pBtnText) {
            action()
        }
        negativeButton(text = nBtnText)
    }
}

fun copyText2Clipboard(text: String?){
    val clipboard = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("qjm copied text", text)
    clipboard.setPrimaryClip(clip)
}

fun shareText2OtherApp(context: Context,title: String,text: String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, title)
    context.startActivity(shareIntent)
}