package com.baichang.android.library.kotlin.widget

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import com.baichang.android.kotlin.width.R
import com.baichang.android.kotlin.width.R.color
import com.baichang.android.kotlin.width.R.layout
import com.baichang.android.library.kotlin.utils.DrawableUtil
import com.baichang.android.library.kotlin.utils.PhotoFragUtil
import com.baichang.android.library.kotlin.config.Configuration
import org.jetbrains.anko.support.v4.dip

class PhotoSelectDialog : DialogFragment(), OnClickListener {
  /**
   * 拍照
   */
  private var btnTake: Button? = null
  /**
   * 相册
   */
  private var btnImage: Button? = null
  /**
   * 取消
   */
  private var btnCancel: Button? = null
  /**
   * 是否自由裁剪
   */
  private var isFree = false

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(layout.photo_select_dialog, container, false)
    val window = dialog.window!!
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    window.requestFeature(Window.FEATURE_NO_TITLE)
    initView(view)
    return view
  }

  private fun initView(view: View) {
    btnCancel = view.findViewById(R.id.photo_select_btn_cancel)
    btnTake = view.findViewById(R.id.photo_select_btn_take)
    btnImage = view.findViewById(R.id.photo_select_btn_image)
    btnImage!!.setOnClickListener(this)
    btnTake!!.setOnClickListener(this)
    btnCancel!!.setOnClickListener(this)
    val normalColor = Configuration.get()
        .getColorAccent()
    val radios = dip(5).toFloat()
    setTakeButtonBackground(color.button_no_activate, normalColor, radios)
    setImageButtonBackground(color.button_no_activate, normalColor, radios)
    setCancelButtonBackground(color.button_no_activate, normalColor, radios, dip(1))
  }

  /**
   * 设置取消按钮的背景颜色
   *
   * @param pressResID 按下颜色id
   * @param normalResID 正常颜色id
   * @param radios 圆角
   */
  private fun setCancelButtonBackground(
    pressResID: Int,
    normalResID: Int,
    radios: Float,
    stoke: Int
  ) {
    val pressDrawable =
        DrawableUtil.getShapeDrawable(ContextCompat.getColor(context!!, normalResID), radios)
    val normalDrawable = DrawableUtil.getShapeDrawable(
        ContextCompat.getColor(context!!, pressResID), radios, stoke,
        ContextCompat.getColor(context!!, normalResID)
    )
    val drawable = DrawableUtil.getPressedDrawable(normalDrawable, pressDrawable)
    btnCancel!!.background = drawable
  }

  /**
   * 设置拍照按钮的背景颜色
   *
   * @param pressResID 按下颜色id
   * @param normalResID 正常颜色id
   * @param radios 圆角
   */
  private fun setTakeButtonBackground(
    pressResID: Int,
    normalResID: Int,
    radios: Float
  ) {
    setButtonDrawableForColor(btnTake!!, pressResID, normalResID, radios)
  }

  /**
   * 改变按钮已设置的Drawable颜色
   *
   * @param btn 要在设置的按钮
   * @param pressId 按下颜色 id
   * @param normalId 正常颜色 id
   * @param radios 圆角
   */
  private fun setButtonDrawableForColor(
    btn: Button,
    pressId: Int,
    normalId: Int,
    radios: Float
  ) {
    btn.background = DrawableUtil.getPressedDrawable(
        ContextCompat.getColor(context!!, pressId), ContextCompat.getColor(context!!, normalId),
        radios
    )
  }

  /**
   * 设置相册按钮的背景颜色
   *
   * @param pressResID 按下颜色id
   * @param normalResID 正常颜色id
   * @param radios 圆角
   */
  private fun setImageButtonBackground(
    pressResID: Int,
    normalResID: Int,
    radios: Float
  ) {
    setButtonDrawableForColor(btnImage!!, pressResID, normalResID, radios)
  }

  override fun onClick(v: View) {
    val id = v.id
    if (id == R.id.photo_select_btn_cancel) {
      dismiss()
    } else if (id == R.id.photo_select_btn_image) {
      if (callback != null) {
        PhotoFragUtil.choose(this, 0)
      } else if (resultListener != null) {
        resultListener!!.onResult(0)
        dismiss()
      }
    } else if (id == R.id.photo_select_btn_take) {
      if (callback != null) {
        PhotoFragUtil.choose(this, 1)
      } else if (resultListener != null) {
        resultListener!!.onResult(1)
        dismiss()
      }
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if (PhotoFragUtil.IsCancel() && requestCode != 100) {
      PhotoFragUtil.cleanActivity()
      return
    }
    if (requestCode == 100 && data != null) {
      //相册选择返回
      if (isFree) {
        PhotoFragUtil.photoZoomFree(data.data)
      } else {
        PhotoFragUtil.photoZoom(data.data)
      }
    } else if (requestCode == 101) {
      //拍照返回 调用裁剪
      if (isFree) {
        PhotoFragUtil.photoZoomFree(null)
      } else {
        PhotoFragUtil.photoZoom(null)
      }
    } else if (requestCode == 102 && resultCode != 0) {
      if (callback != null) {
        Log.i(toString(), "PhotoPath: " + PhotoFragUtil.getPhotoPath())
        callback!!.onResult(PhotoFragUtil.gePhotoBitmap(), PhotoFragUtil.getPhotoPath())
        dismiss()
      }
      PhotoFragUtil.cleanActivity()
    }
  }

  /**
   * 设置是否 自由裁剪
   *
   * @param isFreeCrop 是否自由裁剪
   */
  fun setIsFreeCrop(isFreeCrop: Boolean) {
    isFree = isFreeCrop
  }

  fun show(manager: FragmentManager) {
    this.show(manager, "Photo")
  }

  private var resultListener: OnResultListener? = null

  fun setResultListener(listener: OnResultListener) {
    resultListener = listener
  }

  interface OnResultListener {

    fun onResult(result: Int)
  }

  private var callback: PhotoSelectCallback? = null

  fun setSelectCallBack(listener: PhotoSelectCallback) {
    callback = listener
  }

  interface PhotoSelectCallback {

    fun onResult(
      bitmap: Bitmap?,
      photoPath: String
    )
  }
}