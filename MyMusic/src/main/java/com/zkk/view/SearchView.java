package com.zkk.view;

/**
 * Created by 501000704 on 2017/12/19.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.app.myapp.R;


/**
 * 输入文本框 右边有自带的删除按钮 当有输入时，显示删除按钮，当无输入时，不显示删除按钮。
 *
 *
 */
public class SearchView extends EditText {
    private OnClickDeleteListener onClickDeleteListener;
    private float searchSize = 0;
    private float textSize = 0;
    private int textColor = 0xff000000;
    private Drawable mSearch,mDelete;
    private Paint paint;
    private boolean flags=false;
    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitResource(context, attrs);
        InitPaint();
    }

    private void InitResource(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.searchedit);
        float density = context.getResources().getDisplayMetrics().density;
        searchSize = mTypedArray.getDimension(R.styleable.searchedit_imagewidth, 18 * density + 0.5F);//图片宽度
        textColor = mTypedArray.getColor(R.styleable.searchedit_textcolor, 0xFF000000);
        textSize = mTypedArray.getDimension(R.styleable.searchedit_textSize, 14 * density + 0.5F);//字体大小
        mTypedArray.recycle();
        float dx = getWidth()-85;//开始dx
        float dy = (getHeight() - 50) / 2;//
        Log.e("zkk",dx+"--------------"+ dy);
    }

    private void InitPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawSearchIcon(canvas);
    }

    private void DrawSearchIcon(Canvas canvas) {
        if (this.getText().toString().length() == 0) {
            flags=false;
            float textWidth = paint.measureText("搜索");
            float textHeight = getFontLeading(paint);//搜索字体高度

            float dx = (getWidth() - searchSize - textWidth - 8) / 2;//开始dx
            float dy = (getHeight() - searchSize) / 2;//

            canvas.save();
            canvas.translate(getScrollX() + dx, getScrollY() + dy);
            if (mSearch != null) {
                mSearch.draw(canvas);
            }
            canvas.drawText("搜索", getScrollX() + searchSize + 8, getScrollY() + (getHeight() - (getHeight() - textHeight) / 2) - paint.getFontMetrics().bottom - dy, paint);
            canvas.restore();
        }else{
            flags=true;
            canvas.save();
            float dx = getWidth()-85;//开始dx
            float dy = (getHeight() - 50) / 2;//
            canvas.translate(getScrollX() +dx, dy);

           // mDelete = getContext().getResources().getDrawable(R.mipmap.edit_delete);
            //mDelete.setBounds(getWidth()-90, (getHeight()-70)/2, getWidth()-20, (getHeight()-70)/2+70);
            if (mDelete != null) {

                mDelete.draw(canvas);
            }
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mSearch == null) {
            try {
            mSearch = getContext().getResources().getDrawable(R.mipmap.search);
            mSearch.setBounds(0, 0, (int) searchSize, (int) searchSize);
        } catch (Exception e) {

        }
    }
        if (mDelete == null) {

            try {
                mDelete = getContext().getResources().getDrawable(R.mipmap.edit_delete);
                mDelete.setBounds(0, 0, 50, 50);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {

        if (mSearch != null) {
            mSearch.setCallback(null);
            mSearch = null;
        }
        if (mDelete != null) {
            Log.e("zkk","11111111111");
            mDelete.setCallback(null);
            mDelete = null;
        }
        super.onDetachedFromWindow();
    }

    public float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX()>getWidth()-85&&event.getX()<getWidth()&&event.getY()<getHeight()&&getY()>0){
            if(flags) {
                if(onClickDeleteListener!=null){onClickDeleteListener.onClick(this);}
            }
        }
        return super.onTouchEvent(event);

    }

    public void setOnClickDeleteListener(OnClickDeleteListener onClickDeleteListener){
        this.onClickDeleteListener=onClickDeleteListener;
    }
    public static interface OnClickDeleteListener{
          public void onClick(SearchView view);
    }
}
