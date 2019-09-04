package com.android.test.makestart2.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by AllureDream on 2018-11-19.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
      android.R.attr.listDivider
    };
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable drawable;
    private int orientation;

    public DividerItemDecoration(Context context, int orientation){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        drawable = a.getDrawable(0);
        a.recycle();

    }
    public void setOrientation(int orientation){
        if(orientation!=HORIZONTAL_LIST && orientation!=VERTICAL_LIST) {
        throw new IllegalArgumentException("invalid orientation");
        }
        orientation = orientation;
    }
    @Override
    public void onDraw(Canvas c,RecyclerView parent){
        if(orientation ==VERTICAL_LIST){
            drawVertical(c,parent);
        }else{
            drawVertical(c,parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final  int right = parent.getWidth()-parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
                final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final  int top=child.getBottom()+params.bottomMargin;
            final int bottom = top+drawable.getIntrinsicHeight();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }
    public void drawHorizontal(Canvas c,RecyclerView parent){
        final  int top = parent.getPaddingTop();
        final  int bottom = parent.getHeight()-parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            final View child = parent.getChildAt(i);
            final  RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight()+params.rightMargin;
            final int right = left+drawable.getIntrinsicHeight();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    @Override
        public void getItemOffsets(Rect outRect,int itemPosition,RecyclerView parent){
            if(orientation==VERTICAL_LIST){
                outRect.set(0,0,0,drawable.getIntrinsicHeight());
            }else{
                outRect.set(0,0,drawable.getIntrinsicWidth(),0);
            }
        }
}
