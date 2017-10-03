package com.ditclear.paonet.vendor.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 页面描述：LoadMoreRecyclerView
 *
 * Created by ditclear on 2017/10/3.
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private boolean loadMore;

    public boolean getLoadMore() {
        return loadMore;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context,
            @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
