package com.music.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.myapp.R;
import com.zkk.view.HotWordsLayout;
import com.zkk.view.XCFlowLayout;

public class TestActivity extends AppCompatActivity {
    XCFlowLayout mFlowLayout;
    private  String mNames[]={
            "welcome","android","TextView",
            "apple","jamy","kobe bryant",
            "jordan","layout","viewgroup",
            "margin","padding","text",
            "name","type","search","logcat"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mFlowLayout= (XCFlowLayout) findViewById(R.id.hotlayout);

        mFlowLayout.setAdapter(new XCFlowLayout.Adapter() {
            @Override
            public int getCount() {
                return mNames.length;
            }

            @Override
            public ViewGroup.MarginLayoutParams getMarginLayoutParams(int i) {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = 5;
                lp.rightMargin = 5;
                lp.topMargin = 10;
                lp.bottomMargin = 10;
                return lp;
            }

            @Override
            public View getView(int i, ViewGroup viewGroup) {
                TextView view = new TextView(TestActivity.this);
                view.setText(mNames[i]);
                view.setBackgroundResource(R.drawable.hot_words);
                view.setPadding(30,20,30,20);
                view.setGravity(Gravity.CENTER);
                view.setTextColor(Color.BLUE);
                return view;
            }
        });
    }

}
