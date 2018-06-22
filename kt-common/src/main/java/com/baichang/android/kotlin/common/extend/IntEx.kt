package com.baichang.android.kotlin.common.extend

/**
 * Int 保留小数字后两位
 *
 */
fun Int.keep2Number(): String = "%.2f".format(this)