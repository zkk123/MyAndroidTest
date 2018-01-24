package com.test.view;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by 501000704 on 2018/1/23.
 */

public class BottemRelativeLayout extends RelativeLayout {
    private View bottemMenu,mainMenu;//底部栏对象
    private ViewDragHelper viewDragHelper;
    private int width;
    private float dragRange;//拖拽范围

    private FloatEvaluator floatEvaluator;//浮点数计算器
    private IntEvaluator intEvaluator;//整数计算器

    private DragState currentState=DragState.STATE_CLOSE;//默认是关闭状态
    public BottemRelativeLayout(Context context) {
        super(context);
        init();
    }

    public BottemRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottemRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BottemRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public enum DragState{
        STATE_OPEN,STATE_CLOSE
    }
    /**
     * 该方法在onMeasure执行完成后执行，可以在该方法中初始化自己和子View的宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredHeight();
        dragRange=(int)(bottemMenu.getHeight());


    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private void init(){
        viewDragHelper=ViewDragHelper.create(this,new MyCallback());
        floatEvaluator=new FloatEvaluator();
        intEvaluator=new IntEvaluator();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount()!=2){
            throw new IllegalArgumentException("ERROR!");
        }
        mainMenu=getChildAt(0);
        bottemMenu=getChildAt(1);
        bottemMenu.layout(0,getHeight()-700,getWidth(),0);

    }

    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(BottemRelativeLayout.this);//刷新
        }
    }
    private class MyCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==bottemMenu;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.e("[top]","top===="+top+",dy===="+dy+",width=="+width+",dragRange="+dragRange);
            if(child==bottemMenu){
                Log.e("top","top===="+top+",dy===="+dy);
                if(top>width) top=width-30;//限制左边界
                if(top<width-dragRange)top= (int) (width-dragRange);//限制右边界
            }
            return top;
        }
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

        }
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == bottemMenu) {

            }else if(changedView == mainMenu){

            }
        }


    }
}
