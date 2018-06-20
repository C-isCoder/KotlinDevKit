package com.baichang.android.library.kotlin.extended

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.baichang.android.library.kotlin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * ImageView  加载圆形图片
 *
 * @param id 图片地址，非全路径
 */
fun ImageView.loadCircleUrl(id: String) {
  val path = Api.getImageUrl(id)
  val options = RequestOptions()
  options.circleCrop()
  options.centerCrop()
  options.error(R.mipmap.image_place)
  options.placeholder(R.mipmap.image_place)
  Glide.with(context)
      .load(path)
      .apply(options)
      .into(this)
}

/**
 * ImageView  加载头像
 *
 * @param id 图片地址，非全路径
 */
fun ImageView.loadAvatar(id: String) {
  val path = Api.getImageUrl(id)
  val options = RequestOptions()
  options.error(R.mipmap.avatar_default)
  options.placeholder(R.mipmap.avatar_default)
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
fun ImageView.loadCircleBitmap(bit: Bitmap) {
  val options = RequestOptions()
  options.error(R.mipmap.image_place)
  options.placeholder(R.mipmap.image_place)
  options.centerCrop()
  options.circleCrop()
  Glide.with(context)
      .load(bit)
      .apply(options)
      .into(this)
}

/**
 * ImageView  加载图片
 *
 * @param id 图片加载地址，非全地址
 */
fun ImageView.loadImage(id: String) {
  val path = Api.getImageUrl(id)
  if (BuildConfig.DEBUG) {
    Log.i("Glide", "image path: $path")
  }
  val options = RequestOptions()
  options.error(R.mipmap.image_place)
  options.placeholder(R.mipmap.image_place)
  options.centerCrop()
  Glide.with(context)
      .load(path)
      .apply(options)
      .into(this)
}

/**
 * ImageView  加载图片
 *
 * @param id 图片加载地址，非全地址
 */
fun ImageView.loadImage(
  id: String,
  width: Int,
  height: Int
) {
  val path = Api.getImageUrl(id, width, height)
  if (BuildConfig.DEBUG) {
    Log.i("Glide", "image path: $path")
  }
  val options = RequestOptions()
  options.error(R.mipmap.image_place)
  options.placeholder(R.mipmap.image_place)
  options.centerCrop()
  Glide.with(context)
      .load(path)
      .apply(options)
      .into(this)
}
