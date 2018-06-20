package com.baichang.android.kotlin.common

import android.app.Application
import com.baichang.android.kotlin.common.cache.ACache

/**
 * Created by iCong on 2017/6/26.
 */
class KtApplication : Application() {
  companion object {
    fun getApplication() = Inner.INSTANCE!!
    fun getACacheInstance() = Inner.CACHE_INSTANCE!!
  }

  override fun onCreate() {
    super.onCreate()
    Inner.INSTANCE = this
    Inner.CACHE_INSTANCE = ACache.get(this)
    // Crash
    CrashHandler.getInstance().init(this)
  }

  private object Inner {
    var INSTANCE: Application? = null
    var CACHE_INSTANCE: ACache? = null
  }

}