package com.music.LrcUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.app.myapp.R;


/**
 * Created by 501000704 on 2017/12/18.
 */

public class LrcView extends TextView {
    private float width;                   //歌词视图宽度
    private float height;                 //歌词视图高度
    private Paint currentPaint;          //当前画笔对象
    private Paint notCurrentPaint;      //非当前画笔对象
    private float textHeight = 65;      //文本高度
    private float textMaxSize = 50;
    private float textSize = 40;        //文本大小
    private int index = 0;              //list集合下标
    private LrcInfo infos;              //歌词信息

    public void setmLrcList(LrcInfo infos) {
        this.infos = infos;
    }

    public LrcView(Context context) {
        super(context);
        init();
    }

    public LrcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        setFocusable(true);     //设置可对焦
        //显示歌词部分
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);    //设置抗锯齿，让文字美观饱满
        currentPaint.setTextAlign(Paint.Align.CENTER);//设置文本对齐方式

        //非高亮部分
        notCurrentPaint = new Paint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 绘画歌词
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }

        currentPaint.setColor(getResources().getColor(R.color.blue));
        notCurrentPaint.setColor(getResources().getColor(R.color.hui));

        currentPaint.setTextSize(textMaxSize);
        currentPaint.setTypeface(Typeface.SERIF);

        notCurrentPaint.setTextSize(textSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);

        try {

            setText("");

            canvas.drawText(infos.getLrcLists().get(index).getContent(), width / 2, height / 2, currentPaint);

            float tempY = height / 2;
            //画出本句之前的句子
            for (int i = index - 1; i >= 0; i--) {
                //向上推移
                tempY = tempY - textHeight;
                canvas.drawText(infos.getLrcLists().get(i).getContent(), width / 2, tempY, notCurrentPaint);
            }
            tempY = height / 2;
            //画出本句之后的句子
            for (int i = index + 1; i < infos.getLrcLists().size(); i++) {
                //往下推移
                tempY = tempY + textHeight;
                canvas.drawText(infos.getLrcLists().get(i).getContent(), width / 2, tempY, notCurrentPaint);
            }

        } catch (Exception e) {
            canvas.drawText("...木有歌词文件，赶紧去下载...", width / 2, height / 2, notCurrentPaint);
        }

    }

    /**
     * 当view大小改变的时候调用的方法
     */

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}