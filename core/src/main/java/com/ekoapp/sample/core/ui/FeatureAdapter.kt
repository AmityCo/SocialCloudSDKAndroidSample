package com.ekoapp.sample.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekoapp.sample.core.R
import kotlinx.android.synthetic.main.item_feature.view.*

open class FeatureAdapter(private val dataSet: List<String> ,
                     private val listener: FeatureItemListener) : RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {

    class FeatureViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface FeatureItemListener {
        fun onClick(featureName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val featureItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_feature, parent, false)
        return FeatureViewHolder(featureItemView)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val featureName = dataSet[position]
        holder.view.textview.text = featureName
        holder.view.setOnClickListener {
            listener.onClick(featureName)
        }
    }

    override fun getItemCount() = dataSet.size
}