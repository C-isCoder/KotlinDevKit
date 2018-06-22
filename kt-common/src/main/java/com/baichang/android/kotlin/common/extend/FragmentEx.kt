package com.baichang.android.kotlin.common.extend

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager

/**
 * 判断网络类型
 *
 * @return -1：没有网络  1：WIFI网络 3：net网络
 */
fun Fragment.getNetType(): Int {
  var netType = -1
  val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val networkInfo = connMgr.activeNetworkInfo ?: return netType
  val nType = networkInfo.type
  if (nType == ConnectivityManager.TYPE_MOBILE) {
    netType = 3
  } else if (nType == ConnectivityManager.TYPE_WIFI) {
    netType = 1
  }
  return netType
}

/**
 * Fragment 判断是否有网
 *
 */
fun Fragment.haveNet(): Boolean = getNetType() != -1

/**
 * Fragment 隐藏键盘
 *
 */
fun Fragment.hideKeyboard() {
  val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(activity?.window?.decorView?.windowToken, 0)
}
