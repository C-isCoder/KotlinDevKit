package com.baichang.android.library.kotlin.extended

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import java.io.File
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * String md5 加密
 *
 */
fun String.md5(): String? {
  val content: String
  val token: String? = TokenManager.getToken()
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

/**
 * Activity getActivity()
 *
 */
fun Activity.getActivity(): Activity {
  return this
}

/**
 * EditText 是否显示密码
 *
 * @param isShow true 显示 false 隐藏
 */
fun EditText.setShowOrHidePassword(isShow: Boolean) {
  transformationMethod = if (!isShow) {
    PasswordTransformationMethod.getInstance()
  } else {
    HideReturnsTransformationMethod.getInstance()
  }
  setSelection(length())
}

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
 * Fragment 判断是否有网
 *
 */
fun Fragment.haveNet(): Boolean = getNetType() != -1

/**
 * String 密码位数判断
 *
 */
fun String.checkPassword(): Boolean = this.length >= 6

/**
 * SwipeRefreshLayout 展示刷新圈
 *
 */
fun SwipeRefreshLayout.show() {
  isRefreshing = true
}

/**
 * SwipeRefreshLayout 隐藏刷新圈圈
 *
 */
fun SwipeRefreshLayout.hide() {
  isRefreshing = false
}

/**
 * RecyclerView 判断是否需要加载更多
 *
 */
private const val PAGE_SIZE = 20

fun RecyclerView.loadMore(load: () -> Unit) {
  this.addOnScrollListener(object : OnScrollListener() {
    override fun onScrolled(
      recyclerView: RecyclerView,
      dx: Int,
      dy: Int
    ) {
      super.onScrolled(recyclerView, dx, dy)
      if (recyclerView.adapter.itemCount >= PAGE_SIZE && recyclerView.isScrollBottom() && recyclerView.adapter.itemCount % PAGE_SIZE == 0) {
        load.invoke()
      }
    }
  })
}

/**
 * RecyclerView 判断是否滚动到底部
 *
 */
fun RecyclerView.isScrollBottom(): Boolean =
    computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange()

/**
 * Activity 隐藏键盘
 *
 */
fun Activity.hideKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

/**
 * Fragment 隐藏键盘
 *
 */
fun Fragment.hideKeyboard() {
  val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(activity?.window?.decorView?.windowToken, 0)
}

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

/**
 * EditText 关联一个按钮实现，无文本的时候 按钮置灰。
 *
 * @param button 要关联的按钮
 */
fun EditText.onTextChange(button: Button) {
  button.isEnabled = false
  addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      button.isEnabled = !TextUtils.isEmpty(s)
    }

    override fun beforeTextChanged(
      s: CharSequence?,
      start: Int,
      count: Int,
      after: Int
    ) {
    }

    override fun onTextChanged(
      s: CharSequence?,
      start: Int,
      before: Int,
      count: Int
    ) {
    }
  })
}

/**
 * EditText 关联一个按钮实现，无文本的时候 按钮置灰。
 *
 * @param button 要关联的按钮
 * @param action
 */
fun EditText.onTextChange(
  button: Button,
  action: (s: CharSequence?) -> Unit
) {
  button.isEnabled = false
  addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      button.isEnabled = !TextUtils.isEmpty(s)
      action.invoke(s)
    }

    override fun beforeTextChanged(
      s: CharSequence?,
      start: Int,
      count: Int,
      after: Int
    ) {
    }

    override fun onTextChanged(
      s: CharSequence?,
      start: Int,
      before: Int,
      count: Int
    ) {
    }
  })
}

/**
 * EditText 限制输入小数点后两位。金额相关
 *
 */
fun EditText.moneyModel() {
  this.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
  addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      s?.let {
        if (it.contains(".")) {
          val dos = it.split(".")
          val maxLength = dos[0].length + 3
          this@moneyModel.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }
      }
    }

    override fun beforeTextChanged(
      s: CharSequence?,
      start: Int,
      count: Int,
      after: Int
    ) {
    }

    override fun onTextChanged(
      s: CharSequence?,
      start: Int,
      before: Int,
      count: Int
    ) {
    }
  })
}