package com.baichang.android.kotlin.common.extend

import java.io.File
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * File md5 加密
 *
 */
private val HEX_CHARS =
    charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

fun File.md5(): String {
  val messageDigest = MessageDigest.getInstance("MD5")
  messageDigest.update(readBytes())
  val digest = messageDigest.digest()
  val chars = CharArray(32)
  var i = 0
  while (i < chars.size) {
    val b = digest[i / 2]
    chars[i] = HEX_CHARS[b.toInt().ushr(4) and 15]
    chars[i + 1] = HEX_CHARS[(b and 15).toInt()]
    i += 2
  }
  return String(chars)
}
