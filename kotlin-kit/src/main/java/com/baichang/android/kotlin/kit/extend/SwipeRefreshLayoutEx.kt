package com.baichang.android.kotlin.kit.extend

import android.support.v4.widget.SwipeRefreshLayout

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
