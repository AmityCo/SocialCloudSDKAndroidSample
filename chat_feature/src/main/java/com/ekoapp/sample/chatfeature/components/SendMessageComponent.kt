package com.ekoapp.sample.chatfeature.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.ekoapp.sample.chatfeature.R

class SendMessageComponent : ConstraintLayout {

    init {
        LayoutInflater.from(context).inflate(R.layout.component_send_message, this, true)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
}