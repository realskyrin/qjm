package com.skyrin.qjm.utils

import java.util.*

val QJM_SMS_SIGN = arrayOf(
    "【和驿】",
    "【来取】",
    "【社区人】",
    "【速递易】",
    "【如风达】",
    "【快递超市】",
    "【京东物流】",
    "【菜鸟驿站】",
    "【菜鸟裹裹】",
    "【顺丰速运】",
    "【百世快递】"
)

fun String.isQjmSms(): Boolean {
    return Arrays.stream(QJM_SMS_SIGN).anyMatch { char: CharSequence -> this.contains(char) }
}