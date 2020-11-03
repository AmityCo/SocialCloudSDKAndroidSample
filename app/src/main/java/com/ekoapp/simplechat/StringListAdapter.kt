package com.ekoapp.simplechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_string.view.*

class StringListAdapter(
        private val dataSet: List<String>,
        private val listener: StringItemListener?
) : RecyclerView.Adapter<StringListAdapter.StringViewHolder>() {

    class StringViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface StringItemListener {
        fun onClick(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val featureItemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_string, parent, false)
        return StringViewHolder(featureItemView)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val text = dataSet[position]
        holder.view.textView.text = text
        holder.view.setOnClickListener {
            listener?.onClick(text)
        }
    }

    override fun getItemCount() = dataSet.size
}