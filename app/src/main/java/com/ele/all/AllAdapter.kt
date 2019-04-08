package com.ele.all

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ele.R
import com.ele.db.Entities.NotificationEntity
import com.ele.utils.DateAndTimeUtil
import kotlinx.android.synthetic.main.fragment_recording.view.*

class AllAdapter(var list: MutableList<NotificationEntity>, var listener: ItemClickListener) : RecyclerView.Adapter<AllAdapter.ViewHolder>() {


    interface ItemClickListener {
        fun onItemClicked(repos: NotificationEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recording, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        val context = itemView.context
        fun bindItems(model: NotificationEntity) {
            itemView.tv_name.text = model.name
            itemView.tv_number.text = model.number
            if(model.type !="Incoming"){
                itemView.iv_type.setImageResource(R.drawable.ic_call_received)
            }else itemView.iv_type.setImageResource(R.drawable.ic_call_outgoing)

            var a = ""
            a = try {
                DateAndTimeUtil.getTimeAgo(context, DateAndTimeUtil.getDateInMilliSeconds(model.server_time!!))!!
            } catch (e: Exception) {
                "ago"
            }
            itemView.tv_date.text = a

            itemView.setOnClickListener {
                listener.onItemClicked(model)
            }

        }

    }


}
