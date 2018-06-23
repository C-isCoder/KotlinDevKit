package com.baichang.android.kotlin.kit.http

import android.text.TextUtils
import com.baichang.android.kotlin.kit.KtApplication
import com.baichang.android.kotlin.kit.http.TokenManager.getToken
import org.jetbrains.anko.defaultSharedPreferences
import java.security.MessageDigest

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

/**
 * String 请求加密 md5
 *
 */
fun String.sign(): String? {
  val content: String
  val token: String? = getToken()
  content = if (TextUtils.isEmpty(token)) {
    "content=$this"
  } else {
    "token=$token&content=$this"
  }
  val messageDigest: MessageDigest?
  try {
    messageDigest = MessageDigest.getInstance("MD5")
    messageDigest?.reset()
    messageDigest.update(content.toByteArray(charset("UTF-8")))
  } catch (e: Exception) {
    return null
  }

  val byteArray = messageDigest.digest()
  val md5StrBuff = StringBuffer()
  for (i in byteArray.indices) {
    if (Integer.toHexString(0xFF and byteArray[i].toInt()).length == 1)
      md5StrBuff.append("0").append(
          Integer.toHexString(0xFF and byteArray[i].toInt())
      ) else md5StrBuff.append(Integer.toHexString(0xFF and byteArray[i].toInt()))
  }
  return md5StrBuff.toString()
      .toLowerCase()
}
