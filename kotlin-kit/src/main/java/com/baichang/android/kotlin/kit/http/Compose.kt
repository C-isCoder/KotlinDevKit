package com.baichang.android.kotlin.kit.http

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Compose {
  fun <T> schedulers(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
      upstream.subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }

  fun <T> upload(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
      upstream.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }

  fun <T> download(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
      upstream.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }
}