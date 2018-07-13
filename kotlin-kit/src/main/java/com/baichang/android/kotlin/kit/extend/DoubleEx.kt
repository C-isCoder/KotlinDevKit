package com.baichang.android.kotlin.kit.extend

/**
 * String 保留小数字后两位
 *
 */
fun Double.format2Decimal(): String = "%.2f".format(this)