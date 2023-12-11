package com.saint.struct.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickyHeaderItemDecoration extends RecyclerView.ItemDecoration {

    private final StickyHeaderInterface mListener;

    public StickyHeaderItemDecoration(StickyHeaderInterface listener) {
        mListener = listener;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawStickyHeader(c, parent);
    }

    private void drawStickyHeader(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(view);

            if (mListener.isStickyHeader(adapterPosition)) {
                // Draw your sticky header view here.
                // You can get the header view from the listener and draw it on the canvas.
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (mListener.isStickyHeader(position)) {
            // Set top margin to the height of your sticky header view.
            outRect.top = mListener.getStickyHeaderHeight();
        }
    }

    public interface StickyHeaderInterface {
        boolean isStickyHeader(int position);

        int getStickyHeaderHeight();
    }
}
