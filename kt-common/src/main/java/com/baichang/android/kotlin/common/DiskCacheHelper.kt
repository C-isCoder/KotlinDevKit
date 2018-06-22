package com.baichang.android.kotlin.common

/**
 * Created by iCong on 2017/6/28.
 */

object DiskCacheHelper {
  private const val TOKEN_CACHE = "token_cache"

  fun setToken(token: String) {
    KtApplication.getACacheInstance()
        .put(TOKEN_CACHE, token)
  }

  fun getToken(): String? {
    return KtApplication.getACacheInstance()
        .getAsString(TOKEN_CACHE)
  }
}