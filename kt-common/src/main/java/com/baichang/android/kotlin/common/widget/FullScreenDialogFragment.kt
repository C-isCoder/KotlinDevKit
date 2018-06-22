package com.baichang.android.kotlin.common.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.ViewGroup
import android.view.Window
import com.baichang.android.kotlin.common.R

open class FullScreenDialogFragment : DialogFragment() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 设置自定义主题样式，修改系统栏的颜色。
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    val window = dialog.window!!
    window.requestFeature(Window.FEATURE_NO_TITLE)
    // super 的顺序很重要。（全屏 FragmentDialog）
    super.onActivityCreated(savedInstanceState)
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    window.attributes.windowAnimations = R.style.Dialog_Window
  }

  override fun onSaveInstanceState(outState: Bundle) {}
}