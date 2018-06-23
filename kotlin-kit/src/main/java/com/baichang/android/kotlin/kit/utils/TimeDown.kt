package com.baichang.android.kotlin.kit.utils

import android.os.CountDownTimer

class TimeDown {
  companion object {
    private lateinit var timer: CountDownTimer
    fun start(
      time: Long,
      interval: Long,
      last: (lastTime: Long) -> Unit,
      finish: () -> Unit,
      isSecond: Boolean = true
    ) {
      val millisInFuture = if (isSecond) time * 1000 else time
      val countDownInterval = if (isSecond) interval * 1000 else interval
      timer = object : CountDownTimer(millisInFuture + 500, countDownInterval) {
        override fun onFinish() {
          finish.invoke()
        }

        override fun onTick(millisUntilFinished: Long) {
          last.invoke(if (isSecond) millisUntilFinished / 1000 else millisInFuture)
        }
      }.start()
    }

    fun stop() {
      timer.cancel()
    }
  }
}