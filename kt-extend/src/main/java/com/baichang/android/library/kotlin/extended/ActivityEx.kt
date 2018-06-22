package com.baichang.android.library.kotlin.extended

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager

/**
 * Activity getActivity()
 *
 */
fun Activity.getActivity(): Activity {
  return this
}

/**
 * Activity 判断网络类型
 *
 * @return -1：没有网络  1：WIFI网络 3：net网络
 */
fun Activity.getNetType(): Int {
  var netType = -1
  val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
 * Activity 判断是否有网
 *
 */
fun Activity.haveNet(): Boolean = getNetType() != -1

/**
 * Activity 隐藏键盘
 *
 */
fun Activity.hideKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

/**
 * Activity 获取版本号
 *
 */
fun Activity.getVersionCode(): String {
  // 获取packageManager的实例
  val packageManager = packageManager
  // getPackageName()是你当前类的包名，0代表是获取版本信息
  return try {
    val packInfo = packageManager.getPackageInfo(packageName, 0)
    packInfo.versionCode.toString()
  } catch (e: PackageManager.NameNotFoundException) {
    ""
  }
}

/**
 * Activity 获取版本名
 *
 */
fun Activity.getVersionName(): String {
  // 获取packageManager的实例
  val packageManager = packageManager
  // getPackageName()是你当前类的包名，0代表是获取版本信息
  return try {
    val packInfo = packageManager.getPackageInfo(packageName, 0)
    packInfo.versionName.toString()
  } catch (e: PackageManager.NameNotFoundException) {
    ""
  }
}

