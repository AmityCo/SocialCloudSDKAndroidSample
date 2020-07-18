package com.ekoapp.sample.chatfeature.settings.list.viewholder

import android.view.View
import com.ekoapp.sample.core.base.list.BaseViewHolder
import com.ekoapp.sample.core.preferences.PreferenceHelper.channelTypes
import com.ekoapp.sample.core.preferences.PreferenceHelper.defaultPreference
import kotlinx.android.synthetic.main.item_checkbox_type.view.*

class CheckboxTypeViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
    private val context = itemView.context
    private val prefs = defaultPreference(context)

    companion object {
        private var selecteds = ArrayList<String>()
    }

    override fun bind(item: String) {
        itemView.checkbox_type.text = item.capitalize()
        prefs.channelTypes?.forEach { result ->
            if (result.capitalize() == itemView.checkbox_type.text) {
                itemView.checkbox_type.isChecked = true
            }
        }
    }

    fun checked(action: (Set<String>) -> Unit) {
        val text = itemView.checkbox_type.text.toString().decapitalize()
        itemView.checkbox_type.setOnClickListener {
            if (itemView.checkbox_type.isChecked) {
                selecteds.add(text)
            } else {
                selecteds.remove(text)
            }
            action.invoke(selecteds.toSet())
        }
    }
}