package com.mapolbs.vizibeebritannia.Utilities;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by RAMMURALI on 11/07/2018.
 */


public class VerticalLineDecorator extends RecyclerView.ItemDecoration {
    private int space=0;

    public VerticalLineDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view) == 0)
            outRect.top = space;

        outRect.bottom = space;
    }
}