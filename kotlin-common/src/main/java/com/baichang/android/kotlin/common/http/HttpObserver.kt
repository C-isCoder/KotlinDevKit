package com.baichang.android.kotlin.common.http

import android.util.Log
import android.widget.Toast
import com.baichang.android.kotlin.common.App
import io.reactivex.observers.DisposableObserver

/**
 * Created by iCong on 06/01/2018.
 */
class HttpObserver<T> : DisposableObserver<T> {

  private val TAG = "REQUEST"

  private lateinit var success: ((value: T) -> Unit)
  private var error: ((error: String) -> Unit)? = null
  private var isShow: Boolean = true

  constructor()

  constructor(success: (value: T) -> Unit) : this() {
    this.success = success
  }

  constructor(
    success: (value: T) -> Unit,
    error: (value: String) -> Unit
  ) : this(success) {
    this.error = error
  }

  constructor(
    success: (value: T) -> Unit,
    isShow: Boolean
  ) : this(success) {
    this.isShow = isShow
  }

  constructor(
    success: (value: T) -> Unit,
    error: (value: String) -> Unit,
    isShow: Boolean
  ) : this(success, error) {
    this.isShow = isShow
  }

  override fun onStart() {
    super.onStart()
    if (isShow) {
      HttpDialog.show(App.getApplication())
    }
  }

  override fun onComplete() {
    if (isShow) {
      HttpDialog.dismiss()
    }
  }

  override fun onNext(value: T) {
    success.invoke(value)
  }

  override fun onError(e: Throwable) {
    Log.e(TAG, "HTTP error: $e")
    if (error != null) {
      error!!.invoke(e.message!!)
    } else {
      Toast.makeText(App.getApplication(), e.message, Toast.LENGTH_SHORT)
          .show()
    }
  }

}
