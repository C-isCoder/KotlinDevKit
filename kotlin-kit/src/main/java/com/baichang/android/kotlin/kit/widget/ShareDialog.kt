package com.baichang.android.kotlin.kit.widget

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.baichang.android.kotlin.kit.R
import com.baichang.android.kotlin.kit.http.HttpDialog.dismiss
import com.baichang.android.kotlin.kit.widget.ShareDialog.ShareAdapter.Holder
import kotlinx.android.synthetic.main.item_share.view.ivLogo
import kotlinx.android.synthetic.main.item_share.view.tvName
import org.jetbrains.anko.sdk25.coroutines.onClick

class ShareDialog {

  companion object {
    const val WECHAT = 0  // 微信
    const val CIRCLE = 1  // 朋友圈
    fun show(
      context: Context,
      action: (type: Int) -> Unit
    ) {
      val dialog = BottomSheetDialog(context)
      val contentView = LayoutInflater.from(context)
          .inflate(R.layout.dialog_share_util, null)
      val list = contentView.findViewById<RecyclerView>(R.id.rvList)
      val btnCancel = contentView.findViewById<Button>(R.id.btnCancel)
      list.adapter = ShareAdapter {
        action.invoke(it)
        dialog.dismiss()
      }
      btnCancel.onClick { dialog.dismiss() }
      dialog.setContentView(contentView)
      dialog.show()
    }
  }

  class ShareAdapter(val action: (type: Int) -> Unit) : Adapter<Holder>() {

    val names = listOf("微信", "朋友圈")
    val images = listOf(R.mipmap.we_chat, R.mipmap.icon_moments)

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_share, parent, false)
    )

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(
      holder: Holder,
      position: Int
    ) {
      holder.itemView.ivLogo.setImageResource(images[position])
      holder.itemView.tvName.text = names[position]
      holder.itemView.onClick {
        action.invoke(position)
        dismiss()
      }
    }

    inner class Holder(view: View) : ViewHolder(view)

  }

}
