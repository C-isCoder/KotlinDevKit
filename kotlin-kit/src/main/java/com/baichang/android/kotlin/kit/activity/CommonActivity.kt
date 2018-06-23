package com.baichang.android.kotlin.kit.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View

@SuppressLint("Registered")
open class CommonActivity : FragmentActivity() {
  private var isDarkBar = true

  override fun onCreate(savedInstanceState: Bundle?) {
    StatusBarUtil.TransparencyBar(this)
    if (isDarkBar) {
      StatusBarUtil.StatusBarLightMode(this)
    } else {
      StatusBarUtil.StatusBarDarkMode(this, 1)
      StatusBarUtil.StatusBarDarkMode(this, 2)
      StatusBarUtil.StatusBarDarkMode(this, 3)
    }
    super.onCreate(savedInstanceState)
    this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) // 过场动画
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 横屏
    ActivityCollector.addActivity(this)
  }

  /**
   * 设置是否启用亮色状态栏、即状态栏字体、图标黑色
   * 默认开启
   *
   * @param isVisible true 启用
   */
  fun setSystemBarDark(isVisible: Boolean) {
    isDarkBar = isVisible
  }

  fun back(v: View) {
    onBackPressed()
  }

  override fun onDestroy() {
    super.onDestroy()
    ActivityCollector.removeActivity(this)
  }
}
