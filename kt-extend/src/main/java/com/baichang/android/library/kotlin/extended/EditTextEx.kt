package com.baichang.android.library.kotlin.extended

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText

/**
 * EditText 关联一个按钮实现，无文本的时候 按钮置灰。(button 背景需要实现 select-enable)
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
 * @param action 输入框文本变化回调
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
          this@moneyModel.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
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

/**
 * EditText 限制输入小数点后两位。金额相关
 *
 * @param action 输入状态的回调
 */
fun EditText.moneyModel(action: (s: CharSequence?) -> Unit) {
  this.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
  addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      s?.let {
        if (it.contains(".")) {
          val dos = it.split(".")
          val maxLength = dos[0].length + 3
          this@moneyModel.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
        }
      }
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
 * EditText 密码模式下显示隐藏密码
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

