package com.baichang.android.kotlin.kit.extend

import java.security.MessageDigest

/**
 * String 密码位数判断 最少 6 位
 *
 * @return true 通过，false 小于 6 位
 */
fun String.checkPassword(): Boolean = this.length >= 6

/**
 * String 密码位数判断
 *
 * @param length 密码不少于的长度
 *
 * @return true 通过，false 小于 6 位
 */
fun String.checkPassword(length: Int): Boolean = this.length >= length

/**
 * String 保留小数字后两位
 *
 */
fun String.format2Decimal(): String = "%.2f".format(this)

/**
 * String 格式化手机号空格分割
 * eg 176 8661 6852
 */
fun String.formatMobileWithSpace(): String =
    if (this.length >= 11) "${this.substring(0, 3)} ${this.substring(3, 7)} ${this.substring(
        7, this.length
    )}" else this

/**
 * String 格式化手机号
 *
 * @param parting 分隔符
 */
fun String.formatMobile(parting: String): String =
    if (this.length >= 11) "${this.substring(0, 3)}$parting${this.substring(
        3, 7
    )}$parting${this.substring(7, this.length)}" else this

/**
 * String 手机号隐藏
 * eg 176****6852
 *
 */
fun String.hideMobie(): String =
    "${this.substring(0, 3)}****${this.substring(this.length - 4, this.length)}"

/**
 * String md5 加密
 *
 */
fun String.md5(): String {
  val messageDigest: MessageDigest?
  try {
    messageDigest = MessageDigest.getInstance("MD5")
    messageDigest?.reset()
    messageDigest.update(this.toByteArray(charset("UTF-8")))
  } catch (e: Exception) {
    return ""
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