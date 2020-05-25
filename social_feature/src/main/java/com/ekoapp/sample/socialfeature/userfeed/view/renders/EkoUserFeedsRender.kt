package com.ekoapp.sample.socialfeature.userfeed.view.renders

import android.content.Context
import com.ekoapp.ekosdk.EkoPost
import com.ekoapp.sample.socialfeature.components.BodyFeedsComponent
import com.ekoapp.sample.socialfeature.components.FooterFeedsComponent
import com.ekoapp.sample.socialfeature.components.HeaderFeedsComponent
import com.ekoapp.sample.socialfeature.editfeeds.data.EditUserFeedsData
import com.ekoapp.sample.socialfeature.users.data.UserData

data class ReactionData(val text: String, val isChecked: Boolean, val item: EkoPost)
data class EkoUserFeedsRenderData(val context: Context, val item: EkoPost)

fun EkoUserFeedsRenderData.userFeedRender(
        header: HeaderFeedsComponent,
        body: BodyFeedsComponent,
        footer: FooterFeedsComponent,
        eventLike: (Boolean) -> Unit,
        eventEdit: (EditUserFeedsData) -> Unit,
        eventDelete: (Boolean) -> Unit) {

    header.setupView(item)
    header.editFeeds {
        eventEdit.invoke(
                EditUserFeedsData(
                        userData = UserData(userId = item.postedUserId),
                        postId = item.postId,
                        description = body.getDescription()))
    }
    header.deleteFeeds(eventDelete::invoke)

    body.setupView(item)

    footer.setupView(item)
    footer.likeFeeds(eventLike::invoke)
}
