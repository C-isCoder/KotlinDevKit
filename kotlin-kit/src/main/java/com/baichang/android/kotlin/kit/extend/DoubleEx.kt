package com.baichang.android.kotlin.kit.extend

/**
 * String 保留小数字后两位
 *
 */
fun Double.keep2Number(): String = "%.2f".format(this)