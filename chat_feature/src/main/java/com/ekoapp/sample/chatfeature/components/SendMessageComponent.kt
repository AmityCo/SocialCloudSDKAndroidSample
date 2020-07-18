package com.ekoapp.sample.chatfeature.components

import android.Manifest
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.ekoapp.sample.chatfeature.R
import com.ekoapp.sample.chatfeature.dialogs.CustomMessageBottomSheetFragment
import com.ekoapp.sample.chatfeature.dialogs.SelectPhotoBottomSheetFragment
import com.ekoapp.sample.core.data.Metadata
import com.ekoapp.sample.core.utils.dispatchSearchFileIntent
import com.ekoapp.sample.core.utils.dispatchSearchImageFileIntent
import com.ekoapp.sample.core.utils.dispatchTakePictureIntent
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.component_send_message.view.*


class SendMessageComponent : ConstraintLayout {

    init {
        LayoutInflater.from(context).inflate(R.layout.component_send_message, this, true)
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private fun setupView() {
        edit_text_message.clearComposingText()
        edit_text_message.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                edit_text_message.removeTextChangedListener(this)
                text.setSendButton()
                edit_text_message.addTextChangedListener(this)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        })
    }

    fun textMessage(sent: (String) -> Unit) {
        image_send.setOnClickListener {
            sent.invoke(edit_text_message.text.toString())
            edit_text_message.setText("")
            edit_text_message.clearComposingText()
        }
    }

    fun imageMessage(fm: FragmentManager, path: (String) -> Unit) {
        image_camera.setOnClickListener {
            renderSelectPhoto(fm, path)
        }
    }

    fun customMessage(fm: FragmentManager, sent: (Metadata) -> Unit) {
        image_code.setOnClickListener {
            val customMessageBottomSheet = CustomMessageBottomSheetFragment()
            customMessageBottomSheet.show(fm, customMessageBottomSheet.tag)
            customMessageBottomSheet.sendCustomMessage {
                sent.invoke(it)
                customMessageBottomSheet.dialog?.cancel()
            }
        }
    }

    fun attachMessage() {
        image_attach.setOnClickListener {
            Dexter.withContext(context)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            (context as AppCompatActivity).dispatchSearchFileIntent()
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {

                        }

                        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                            token.continuePermissionRequest()
                        }
                    }).check()
        }
    }

    private fun Editable?.setSendButton() {
        if (!this?.trim().isNullOrEmpty()) {
            image_send.visibility = View.VISIBLE
        } else {
            image_send.visibility = View.GONE
        }
    }

    private fun renderSelectPhoto(fm: FragmentManager, path: (String) -> Unit) {
        val selectPhotoBottomSheet = SelectPhotoBottomSheetFragment()
        selectPhotoBottomSheet.show(fm, selectPhotoBottomSheet.tag)
        selectPhotoBottomSheet.renderCamera {
            selectPhotoBottomSheet.requestPermission(Manifest.permission.CAMERA) {
                (context as AppCompatActivity).dispatchTakePictureIntent(path)
            }
        }
        selectPhotoBottomSheet.renderGallery {
            selectPhotoBottomSheet.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                (context as AppCompatActivity).dispatchSearchImageFileIntent()
            }
            selectPhotoBottomSheet.dialog?.cancel()
        }
    }

    private fun SelectPhotoBottomSheetFragment.requestPermission(permission: String, action: () -> Unit) {
        Dexter.withContext(context)
                .withPermission(permission)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        action.invoke()
                        dialog?.cancel()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        dialog?.cancel()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }
}