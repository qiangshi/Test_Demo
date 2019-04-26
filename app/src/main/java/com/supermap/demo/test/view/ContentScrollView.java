package com.supermap.demo.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.ScrollView;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/17 15:57
 */
public class ContentScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private ContentScrollView.OnScrollChangedListener listener;

    public ContentScrollView(Context context) {
        super(context);
    }

    public ContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollChangeListener(ContentScrollView.OnScrollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = this.getParent();
        while (parent != null) {
            if (parent instanceof ScrollLayout) {
                ((ScrollLayout) parent).setAssociatedScrollView(this);
                break;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ViewParent parent = this.getParent();
        if (parent instanceof ScrollLayout) {
            if (((ScrollLayout) parent).getCurrentStatus() == ScrollLayout.Status.OPENED )
                return false;
        }
        return super.onTouchEvent(ev);
    }
}
