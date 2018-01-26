package com.test.surfaceview;

/**
 * Created by 501000704 on 2018/1/26.
 */

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Timer timer = null;
    private MyTimerTask task = null;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        timer = new Timer();
        task = new MySurfaceView.MyTimerTask(holder);
        timer.schedule(task, 50, 500);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
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
            paint.setTextSize(50);
            paint.setColor(Color.WHITE);
        }
        public void run() {
            // TODO Auto-generated method stub
            if(count > 40)//测试程序，大于20不再画了
                return;

            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas(null);//锁整个画布
                canvas.drawColor(Color.BLACK);
                if(count % 2 == 0) {
                    canvas.drawText(count+"", 150, count*60, paint);
                }else {
                    canvas.drawText(count+"", 450, count*60, paint);
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
