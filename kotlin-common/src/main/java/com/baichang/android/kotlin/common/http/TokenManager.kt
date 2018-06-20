package com.baichang.android.kotlin.common.http

import android.text.TextUtils
import com.baichang.android.kotlin.common.KtApplication
import org.jetbrains.anko.defaultSharedPreferences

/**
 * Created by iCong on 2017/6/28.
 */
object TokenManager {
  private var token: String? = null
  private const val APP_TOKEN = "app_token"

  fun setToken(token: String) {
    TokenManager.token = token
    KtApplication.getApplication()
        .defaultSharedPreferences.edit()
        .putString(APP_TOKEN, token)
        .apply()
  }

  fun getToken(): String? {
    return if (TextUtils.isEmpty(
            token
        )
    ) KtApplication.getApplication().defaultSharedPreferences.getString(
        APP_TOKEN, ""
    ) else token
  }
}