package com.ekoapp.sample.chatfeature.messages.view

import com.ekoapp.sample.chatfeature.R
import com.ekoapp.sample.chatfeature.constants.EXTRA_CHANNEL_MESSAGES
import com.ekoapp.sample.chatfeature.data.ChannelData
import com.ekoapp.sample.chatfeature.data.MessageData
import com.ekoapp.sample.chatfeature.di.DaggerChatActivityComponent
import com.ekoapp.sample.chatfeature.messages.view.list.MainMessageAdapter
import com.ekoapp.sample.core.base.list.RecyclerBuilder
import com.ekoapp.sample.core.base.viewmodel.SingleViewModelActivity
import com.ekoapp.sample.core.ui.extensions.coreComponent
import com.ekoapp.sample.core.ui.extensions.observeNotNull
import kotlinx.android.synthetic.main.activity_messages.*

class MessagesActivity : SingleViewModelActivity<MessagesViewModel>() {

    private lateinit var adapter: MainMessageAdapter

    override fun bindViewModel(viewModel: MessagesViewModel) {
        val item = intent.extras?.getParcelable<ChannelData>(EXTRA_CHANNEL_MESSAGES)
        viewModel.setupIntent(item)
        renderList(viewModel)
    }

    private fun renderList(viewModel: MessagesViewModel) {
        adapter = MainMessageAdapter(this, viewModel)
        RecyclerBuilder(context = this, recyclerView = recycler_message)
                .builder()
                .build(adapter)
        viewModel.getIntentChannelData {
            viewModel.bindGetMessageCollectionByTags(MessageData(channelId = it.channelId))
                    .observeNotNull(this, adapter::submitList)
        }
    }

    override fun getViewModelClass(): Class<MessagesViewModel> {
        return MessagesViewModel::class.java
    }

    override fun getLayout(): Int {
        return R.layout.activity_messages
    }

    override fun initDependencyInjection() {
        DaggerChatActivityComponent
                .builder()
                .coreComponent(coreComponent())
                .build()
                .inject(this)
    }
}