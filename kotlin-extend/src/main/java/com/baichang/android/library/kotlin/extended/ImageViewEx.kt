package com.baichang.android.library.kotlin.extended

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.baichang.android.library.kotlin.BuildConfig
import com.baichang.android.library.kotlin.R
import com.baichang.android.library.kotlin.config.Configuration
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * ImageView  加载圆形图片
 *
 * @param urlOrId 图片全路径或者图片id
 * @param errorId 加载错误图片 id
 * @param isFull 是否是全路径
 */
fun ImageView.loadCircleUrl(
  isFull: Boolean = false,
  urlOrId: String,
  errorId: Int = R.mipmap.circle_default
) {
  val options = RequestOptions()
  val path = if (isFull) urlOrId else Configuration.get().getApiLoadImage() + urlOrId
  options.circleCrop()
  options.centerCrop()
  options.error(errorId)
  options.placeholder(errorId)
  options.override(width, height)
  Glide.with(context)
      .load(path)
      .apply(options)
      .into(this)
}

/**
 * ImageView  加载头像
 *
 * @param urlOrId 图片全路径或者图片id
 * @param errorId 加载错误图片 id
 * @param isFull 是否是全路径
 */
fun ImageView.loadAvatar(
  isFull: Boolean = false,
  urlOrId: String,
  errorId: Int = R.mipmap.avatar_default
) {
  val options = RequestOptions()
  val path = if (isFull) urlOrId else Configuration.get().getApiLoadImage() + urlOrId
  options.circleCrop()
  options.centerCrop()
  options.error(errorId)
  options.placeholder(errorId)
  options.override(width, height)
  Glide.with(context)
      .load(path)
      .apply(options)
      .into(this)
}

/**
 * ImageView  加载圆形图片
 *
 * @param bit 图片 Bitmap
 */
fun ImageView.loadCircleBitmap(
  bit: Bitmap,
  errorId: Int = R.mipmap.circle_default
) {
  val options = RequestOptions()
  options.error(errorId)
  options.placeholder(errorId)
  options.centerCrop()
  options.circleCrop()
  options.override(width, height)
  Glide.with(context)
      .load(bit)
      .apply(options)
      .into(this)
}

/**
 * ImageView  加载图片
 *
 * @param urlOrId 图片全路径或者图片id
 * @param errorId 加载错误图片 id
 * @param isFull 是否是全路径
 */
fun ImageView.loadImage(
  isFull: Boolean = false,
  urlOrId: String,
  errorId: Int = R.mipmap.avatar_default
) {
  val options = RequestOptions()
  val path = if (isFull) urlOrId else Configuration.get().getApiLoadImage() + urlOrId
  options.centerCrop()
  options.error(errorId)
  options.placeholder(errorId)
  options.override(width, height)
  Glide.with(context)
      .load(path)
      .apply(options)
      .into(this)
}

