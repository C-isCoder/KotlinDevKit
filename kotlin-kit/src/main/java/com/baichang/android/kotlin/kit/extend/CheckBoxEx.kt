package com.baichang.android.kotlin.kit.extend

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import android.widget.EditText

/**
 * CheckBox 密码模式下显示隐藏密码
 *
 * @param editText 要关联的 EditText
 */
fun CheckBox.setPasswordMode(editText: EditText) {
  transformationMethod = if (!isChecked) {
    PasswordTransformationMethod.getInstance()
  } else {
    HideReturnsTransformationMethod.getInstance()
  }
  editText.setSelection(length())
}
