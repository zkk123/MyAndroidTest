package com.test.view;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

    private int height;
    private float dragRange;//拖拽范围
    private onDragStateChangeListener listener;


    private DragState currentState=DragState.STATE_CLOSE;//默认是关闭状态

    public BottemRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("[BottemRelativeLayout]","======BottemRelativeLayout1=======");
        init();
    }

    public BottemRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("[BottemRelativeLayout]","======BottemRelativeLayout2=======");
        init();
    }

    public void setOnDragStateChangeListener(onDragStateChangeListener listener) {
        this.listener = listener;
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
        height = getMeasuredHeight();

        dragRange=(int)(bottemMenu.getMeasuredHeight());
        Log.e("[onSizeChanged]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(bottemMenu,widthMeasureSpec, heightMeasureSpec);
        Log.e("[onMeasure]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());
        Log.e("bottemMenu","hight===="+bottemMenu.getMeasuredHeight());
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
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("[onLayout]","changed="+changed+",l===="+l+",t===="+t+",r===="+r+",b===="+b);// changed=true,l====0,t====0,r====1080,b====1776
        super.onLayout(changed, l, t, r, b);


    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("[onScrollChanged]",",l===="+l+",t===="+t+",oldl===="+oldl+",oldt===="+oldt);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("[onFinishInflate]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());
        if(getChildCount()!=2){
            throw new IllegalArgumentException("ERROR!");
        }
        mainMenu=getChildAt(0);
        bottemMenu=getChildAt(1);

    }
    //侧滑面板打开
    public void open() {
        Log.e("[open]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());
        Log.e("[open]","(int)(height- dragRange)="+((int)(height- dragRange)));
        viewDragHelper.smoothSlideViewTo(bottemMenu, 0, (int)(height- dragRange));
       ViewCompat.postInvalidateOnAnimation(BottemRelativeLayout.this);//刷新

    }
    //侧滑面板关闭
    public void close() {
        Log.e("[close]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());
        viewDragHelper.smoothSlideViewTo(bottemMenu, 0, height);

        ViewCompat.postInvalidateOnAnimation(BottemRelativeLayout.this);//刷新
        invalidate();
    }
    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
            // //必须调用View的postInvalidate()/invalidate()，如果不加会导致View的移动只会第一帧。
            ViewCompat.postInvalidateOnAnimation(BottemRelativeLayout.this);//刷新
            //postInvalidate();
        }
    }
    private class MyCallback extends ViewDragHelper.Callback {
        /**
         * 用于判断是否捕获当前child的触摸事件
         * @param child 当前触摸的子View
         * @param pointerId
         * @return true:就捕获并解析 false：不处理
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==bottemMenu||child==mainMenu;
        }

        /**
         * 获取view水平方向的拖拽范围,但是目前不能限制边界,返回的值目前用在手指抬起的时候view缓慢移动的动画世界的计算上面; 最好不要返回0
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.e("[----top]","top===="+top+",dy===="+dy+",width=="+height+",dragRange="+dragRange);
            if(child==bottemMenu){
                Log.e("top","top===="+top+",dy===="+dy);
                Log.e("bottemMenu","hight===="+bottemMenu.getHeight()+",dy===="+dy);
                if(top>height) top=height-30;//限制左边界
                if(top<height-dragRange)top= (int) (height-dragRange);//限制右边界
            }else{
                Log.e("top","top===="+top+",dy===="+dy);

                top=0;
            }
            return top;
        }



        /**
         * 当child的位置改变的时候执行,一般用来做其他子View的伴随移动
         * changedView：位置改变的child
         * left：child当前最新的left
         * top: child当前最新的top
         * dx: 本次水平移动的距离
         * dy: 本次垂直移动的距离
         */

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.e("[onViewPositionChanged]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());
            if (changedView == bottemMenu) {
                 float fac=(height-bottemMenu.getTop())/dragRange;
                 if(currentState==DragState.STATE_CLOSE&&fac==1){
                     currentState=DragState.STATE_OPEN;
                         if(listener!=null)listener.onOpen();
                         mainMenu.setAlpha(0.3f);

                 }else if(currentState==DragState.STATE_OPEN&&fac==0){
                         currentState=DragState.STATE_CLOSE;
                         if(listener!=null)listener.onClose();
                     mainMenu.setAlpha(1f);
                 }else{
                     if(listener!=null)listener.Draging(fac);
                 }
            }else if(changedView == mainMenu){

            }
        }
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.e("[onViewReleased]","getHeight="+getHeight()+"getMeasuredHeight="+getMeasuredHeight());
            Log.e("[onViewReleased]","bottemMenu.getTop()="+bottemMenu.getTop()+"height-dragRange/2="+(height-dragRange/2));
            if(bottemMenu.getTop()>height-dragRange/2){//在拖拽范围的左边，关闭
               close();
            }else {//在拖拽范围的右边，打开
               open();
            }
            //当用户稍微滑动一下，根据X轴方向的速度来打开或者关闭侧滑菜单
            if(xvel>200&&currentState!=DragState.STATE_OPEN){
                open();
            }else if(xvel<-200&&currentState!=DragState.STATE_CLOSE){
                close();
            }
        }
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            Log.e("[onEdgeDragStarted]","edgeFlags="+edgeFlags);
        }
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {

            super.onEdgeTouched(edgeFlags, pointerId);
            Log.e("[onEdgeTouched]","edgeFlags="+edgeFlags);
        }
    }
    public static interface onDragStateChangeListener{
        /**
         * 侧滑菜单打开
         */
        void onOpen();

        /**
         * 侧滑菜单处于关闭
         */
        void onClose();

        /**
         *正在拖拽,将此时的百分比随时暴露给调用者
         */
        void Draging(float fraction);
    }
    private static class comment{


//                01-24 04:31:04.357 25162-25162/com.example.com.myapplication E/[BottemRelativeLayout]: ======BottemRelativeLayout1=======
//                01-24 04:31:04.385 25162-25162/com.example.com.myapplication E/[onFinishInflate]: getHeight=0getMeasuredHeight=0
//                01-24 04:31:04.469 25162-25162/com.example.com.myapplication E/[onMeasure]: getHeight=0getMeasuredHeight=1560
//                01-24 04:31:04.469 25162-25162/com.example.com.myapplication E/bottemMenu: hight====900
//                01-24 04:31:04.503 25162-25162/com.example.com.myapplication E/[onMeasure]: getHeight=0getMeasuredHeight=1776
//                01-24 04:31:04.503 25162-25162/com.example.com.myapplication E/bottemMenu: hight====900
//                01-24 04:31:04.505 25162-25162/com.example.com.myapplication E/[onSizeChanged]: getHeight=1776getMeasuredHeight=1776
//                01-24 04:31:04.505 25162-25162/com.example.com.myapplication E/[onLayout]: changed=true,l====0,t====0,r====1080,b====1776
//                01-24 04:31:04.534 25162-25162/com.example.com.myapplication E/[computeScroll]: getHeight=1776getMeasuredHeight=1776
    }
    }




