package com.supermap.demo.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.supermap.demo.test.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: MaxRecyclerView
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 15:39
 */
public class MaxHeightRecyclerView extends RecyclerView {

    private int mMaxHeight; public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight);
        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

