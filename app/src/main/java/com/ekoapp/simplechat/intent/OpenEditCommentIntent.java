package com.ekoapp.simplechat.intent;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.ekoapp.ekosdk.comment.EkoComment;
import com.ekoapp.simplechat.comment.EditCommentActivity;

public class OpenEditCommentIntent extends BaseIntent {

    private static final String EXTRA_COMMENT = EXTRA + "comment";

    public OpenEditCommentIntent(@NonNull Context context, @NonNull EkoComment comment) {
        super(context, EditCommentActivity.class);
        putExtra(EXTRA_COMMENT, comment);
    }

    public static EkoComment getComment(Intent intent) {
        return intent.getExtras().getParcelable(EXTRA_COMMENT);
    }

}
