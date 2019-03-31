package com.ele.all

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ele.R
import com.ele.model.LanguageModel
import kotlinx.android.synthetic.main.fragment_recording.view.*

class AllAdapter(var list: ArrayList<LanguageModel>, var listener: ItemClickListener) : RecyclerView.Adapter<AllAdapter.ViewHolder>() {


    interface ItemClickListener {
        fun onItemClicked(repos: LanguageModel)
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
        fun bindItems(model: LanguageModel) {
            itemView.tv.text = model.langNameEng
            itemView.setOnClickListener {
                listener.onItemClicked(model)
            }

        }

    }


}
