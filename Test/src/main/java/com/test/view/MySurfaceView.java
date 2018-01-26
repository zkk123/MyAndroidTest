package com.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.com.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 501000704 on 2018/1/25.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder mHolder;
    private Canvas mCanvas;//绘图的画布
    private Timer timer = null;
    private MyTimerTask task = null;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("--surfaceview--","ffffffffffff");
        initView();
    }
    private void initView() {
        mHolder = getHolder();//获取SurfaceHolder对象
        mHolder.addCallback(this);//注册SurfaceHolder的回调方法

    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        // TODO Auto-generated method stub
        timer = new Timer();
        task = new MyTimerTask(mHolder);
        timer.schedule(task, 500, 1000);
        //new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        task = null;
    }


    public class MyTimerTask extends TimerTask {
        private SurfaceHolder holder = null;
        private Paint paint = null;
        private int count = 0;

        public MyTimerTask(SurfaceHolder _holder) {
            holder = _holder;
            paint = new Paint();
            paint.setColor(Color.WHITE);
        }
        public void run() {
            // TODO Auto-generated method stub
            if(count > 40)//测试程序，大于20不再画了
                return;

            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas(null);//锁整个画布
                if(count % 2 == 0) {
                    canvas.drawText(count+"", 50, count*8, paint);
                }else {
                    canvas.drawText(count+"", 150, count*8, paint);
                }
            }catch (Exception e) {
                // TODO: handle exception
            }finally {
                if(canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            count++;
        }

        public void clearDraw() {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas(null);
                canvas.drawColor(Color.BLACK);
            }catch (Exception e) {
                // TODO: handle exception
            }finally {
                if(canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
