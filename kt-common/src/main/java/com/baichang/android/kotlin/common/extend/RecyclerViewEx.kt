package com.baichang.android.kotlin.common.extend

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener

private const val PAGE_SIZE = 20
/**
 * RecyclerView 判断是否需要加载更多
 *
 */
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
 * RecyclerView 判断是否需要加载更多
 *
 * @param action 滚动监听的回调
 */
fun RecyclerView.loadMore(
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