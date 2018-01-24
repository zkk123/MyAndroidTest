package com.test.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    private Paint mPaint;
    private Point mPoint;

    public MyView(Context context) {
        super(context);
        init();
    }
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoint == null) {
            mPoint = new Point(50, 50);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mPoint.getX(), mPoint.getY(), 50, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(50, 50);
        Point endPoint = new Point(getWidth() - 50, getHeight() - 50);

        ValueAnimator animator = ValueAnimator
                .ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mPoint = ((Point) animation.getAnimatedValue());

                invalidate();
            }
        });
        animator.setDuration(5000);
        animator.start();
    }
    public static class Point{

            private float x;
            private float y;

            public Point(float x, float y) {
                this.x = x;
                this.y = y;
            }

            public float getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public float getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }



    }
    public static class PointEvaluator implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float resultX = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
            float resultY = startValue.getY() + fraction * (endValue.getY() - startValue.getX());
            return new Point(resultX, resultY);
        }
    }


}
