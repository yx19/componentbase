package com.smartlab.componentbase.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：xxl
 * @Created in：2018/12/14
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int top;
    private int left;
    private int rigth;
    private int bottom;

    public SpacesItemDecoration(int top,int left,int rigth,int bottom) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.rigth = rigth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = rigth;
        outRect.bottom = bottom;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = top;
    }

}
