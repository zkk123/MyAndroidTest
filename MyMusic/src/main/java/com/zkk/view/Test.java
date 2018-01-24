package com.zkk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by 501000704 on 2018/1/17.
 */

public class Test extends FrameLayout {
    public Test(Context context) {
        super(context);
    }
    public Test(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public Test(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
