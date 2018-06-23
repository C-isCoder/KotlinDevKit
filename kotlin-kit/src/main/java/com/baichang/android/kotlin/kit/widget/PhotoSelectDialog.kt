package com.baichang.android.kotlin.kit.widget

import android.annotation.SuppressLint
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
import com.baichang.android.kotlin.kit.R
import com.baichang.android.kotlin.kit.config.Configuration
import com.baichang.android.kotlin.kit.utils.DrawableUtil
import com.baichang.android.kotlin.kit.utils.PhotoFragmentUtil
import org.jetbrains.anko.support.v4.dip

@SuppressLint("ValidFragment")
class PhotoSelectDialog : DialogFragment,
    OnClickListener {

  constructor() : super()

  constructor(listener: (bitmap: Bitmap, filePath: String) -> Unit) : super() {
    this.listener = listener
  }

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
  /**
   * 回掉
   */
  private var listener: ((bitmap: Bitmap, filePath: String) -> Unit)? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.photo_select_dialog, container, false)
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
    val normalColor = Configuration.getColorAccent()
    val radios = dip(5).toFloat()
    setTakeButtonBackground(R.color.button_no_activate, normalColor, radios)
    setImageButtonBackground(R.color.button_no_activate, normalColor, radios)
    setCancelButtonBackground(R.color.button_no_activate, normalColor, radios, dip(1))
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
    when (v.id) {
      R.id.photo_select_btn_cancel -> dismiss()
      R.id.photo_select_btn_image  -> doResult(0)
      R.id.photo_select_btn_take   -> doResult(1)
    }
  }

  private fun doResult(which: Int) {
    callback?.let { PhotoFragmentUtil.choose(this, which) }
    listener?.let { PhotoFragmentUtil.choose(this, which) }
    resultListener?.let {
      it.onResult(which)
      dismiss()
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if (PhotoFragmentUtil.IsCancel() && requestCode != 100) {
      PhotoFragmentUtil.cleanActivity()
      return
    }
    if (requestCode == 100 && data != null) {
      //相册选择返回
      if (isFree) {
        PhotoFragmentUtil.photoZoomFree(data.data)
      } else {
        PhotoFragmentUtil.photoZoom(data.data)
      }
    } else if (requestCode == 101) {
      //拍照返回 调用裁剪
      if (isFree) {
        PhotoFragmentUtil.photoZoomFree(null)
      } else {
        PhotoFragmentUtil.photoZoom(null)
      }
    } else if (requestCode == 102 && resultCode != 0) {
      callback?.let {
        Log.i(toString(), "PhotoPath: " + PhotoFragmentUtil.getPhotoPath())
        it.onResult(
            PhotoFragmentUtil.gePhotoBitmap(), PhotoFragmentUtil.getPhotoPath()
        )
        dismiss()
      }
      listener?.let {
        Log.i(toString(), "PhotoPath: " + PhotoFragmentUtil.getPhotoPath())
        it.invoke(PhotoFragmentUtil.gePhotoBitmap(), PhotoFragmentUtil.getPhotoPath())
        dismiss()
      }
      PhotoFragmentUtil.cleanActivity()
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

  fun setSelectCallBack(listener: (bitmap: Bitmap, filePath: String) -> Unit) {
    this.listener = listener
  }

  interface PhotoSelectCallback {

    fun onResult(
      bitmap: Bitmap?,
      photoPath: String
    )
  }
}