package com.example.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.view.MyView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout1,layout2;
    TextView textview,myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout1= (RelativeLayout) findViewById(R.id.layout1);
        layout2= (RelativeLayout) findViewById(R.id.layout2);
        textview= (TextView) findViewById(R.id.textView);
        textview.setClickable(true);
        myView= (TextView) findViewById(R.id.myView);


        layout1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("layout1","44444444444444444");
                return false;
            }
        });
        layout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("layout2","555555555555555555555");
                return false;
            }
        });
        textview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("textview","66666666666666666666");
                return false;
            }
        });
    }
}
