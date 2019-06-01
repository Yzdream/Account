package com.yz.account.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Context mContext;
    private Drawable mDivider;
    private int mOrientation;
    private  int drawable;
    private boolean isBottonLine;
    /**
     * 分割线缩进值
     */
    private int inset;
    private Paint paint;

    /**
     * @param  * @param orientation layout的方向  * @param drawable  引入的drawable的ID  * @param inset    分割线缩进值
     */
    public CustomDecoration(Builder builder){
        mContext = builder.mContext;
        drawable = builder.drawable;
        mOrientation = builder.mOrientation;
        inset = builder.inset;
        isBottonLine = builder.isBottonLine;
        mDivider = mContext.getResources().getDrawable(drawable);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        setOrientation(mOrientation);
    }

    public static class Builder {
        private Context mContext;
        private int mOrientation;
        private  int drawable;
        private int inset;
        private boolean isBottonLine;

        public Builder setContext(Context context){
            mContext = context;
            return this;
        }

        public Builder setOrientation(int Orientation){
            mOrientation = Orientation;
            return this;
        }

        public Builder setDrawable(int drawable){
            this.drawable = drawable;
            return this;
        }

        public Builder setInset(int inset){
            this.inset = inset;
            return this;
        }

        public Builder setIsBottonLine(boolean isBottonLine){
            this.isBottonLine = isBottonLine;
            return this;
        }

        public CustomDecoration build(){
            return new CustomDecoration(this);
        }


    }



    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        //最后一个item不画分割线
        if (!isBottonLine){
            childCount = childCount-1;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            if (inset > 0) {
                c.drawRect(left, top, right, bottom, paint);
                mDivider.setBounds(left + inset, top, right - inset, bottom);
            } else {
                mDivider.setBounds(left, top, right, bottom);
            }
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        //最后一个item不画分割线
        if (!isBottonLine){
            childCount = childCount-1;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //由于Divider也有宽高，每一个Item需要向下或者向右偏移
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
