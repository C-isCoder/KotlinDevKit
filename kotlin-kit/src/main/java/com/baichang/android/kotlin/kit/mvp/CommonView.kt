package com.baichang.android.kotlin.kit.mvp

interface CommonView<T> {
  var presenter: T
  fun showMessage(msgId: Int)
  fun showMessage(msg: String)
  fun showProgress()
  fun hideProgress()
}