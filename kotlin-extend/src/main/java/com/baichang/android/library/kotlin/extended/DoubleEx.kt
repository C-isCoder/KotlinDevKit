package com.baichang.android.library.kotlin.extended

/**
 * String 保留小数字后两位
 *
 */
fun Double.keep2Number(): String = "%.2f".format(this)