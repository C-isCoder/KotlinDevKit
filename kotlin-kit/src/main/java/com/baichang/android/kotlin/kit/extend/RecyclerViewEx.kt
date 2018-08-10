package com.baichang.android.kotlin.kit.extend

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener

/**
 * RecyclerView 判断是否需要加载更多
 *
 */
fun RecyclerView.loadMore(
  pageSize: Int = 20,
  load: () -> Unit
) {
  this.addOnScrollListener(object : OnScrollListener() {
    override fun onScrolled(
      recyclerView: RecyclerView,
      dx: Int,
      dy: Int
    ) {
      super.onScrolled(recyclerView, dx, dy)
      if (recyclerView.adapter.itemCount >= pageSize && recyclerView.isScrollBottom() && recyclerView.adapter.itemCount % pageSize == 0) {
        load.invoke()
      }
    }
  })
}

/**
 * RecyclerView 判断是否需要加载更多
 *
 * @param action 滚动监听的回调
 */
fun RecyclerView.loadMore(
  pageSize: Int = 20,
  load: () -> Unit,
  action: (recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit
) {
  this.addOnScrollListener(object : OnScrollListener() {
    override fun onScrolled(
      recyclerView: RecyclerView,
      dx: Int,
      dy: Int
    ) {
      action.invoke(recyclerView, dx, dy)
      super.onScrolled(recyclerView, dx, dy)
      if (recyclerView.adapter.itemCount >= pageSize && recyclerView.isScrollBottom() && recyclerView.adapter.itemCount % pageSize == 0) {
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