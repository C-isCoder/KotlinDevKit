package com.baichang.android.kotlin.common.widget

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baichang.android.kotlin.common.R
import kotlinx.android.synthetic.main.dialog_share_util.btnCancel
import kotlinx.android.synthetic.main.dialog_share_util.rvList

@SuppressLint("ValidFragment")
class ShareDialog(val action: (type: Int) -> Unit) : FullScreenDialogFragment() {

  companion object {
    const val WECHAT = 0  // 微信
    const val CIRCLE = 1  // 朋友圈
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.dialog_share_util, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    rvList.adapter = ShareAdapter()
    btnCancel.setOnClickListener { dismiss() }
  }

  inner class ShareAdapter : RecyclerView.Adapter<Holder>() {

    val names = listOf("微信", "朋友圈")
    val images = listOf(
        R.mipmap.we_chat,
        R.mipmap.icon_moments
    )

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_share, parent, false
        )
    )

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(
      holder: Holder,
      position: Int
    ) {
      holder.ivLogo.setImageResource(images[position])
      holder.tvName.text = names[position]
      holder.itemView.setOnClickListener {
        action.invoke(position)
        dismiss()
      }
    }
  }

  inner class Holder(view: View) : ViewHolder(view) {
    val ivLogo: ImageView = view.findViewById(R.id.ivLogo)
    val tvName: TextView = view.findViewById(R.id.tvName)
  }
}
