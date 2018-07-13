package com.baichang.android.library.kotlin.extended

/**
 * Int 保留小数字后两位
 *
 */
fun Int.format2Decimal(): String = "%.2f".format(this)