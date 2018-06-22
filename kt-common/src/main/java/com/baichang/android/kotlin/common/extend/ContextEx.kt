package com.baichang.android.kotlin.common.extend

import android.content.Context
import android.content.pm.PackageManager

/**
 * Context 获取版本号
 *
 */
fun Context.getVersionCode(): String {
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
 * Context 获取版本名
 *
 */
fun Context.getVersionName(): String {
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