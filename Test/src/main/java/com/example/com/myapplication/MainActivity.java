package com.example.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.view.BottemRelativeLayout;
import com.test.view.MyView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout1,layout2,myView;
    BottemRelativeLayout main;
    TextView textview;
    boolean flags=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main= (BottemRelativeLayout) findViewById(R.id.activity_main);
        main.setOnDragStateChangeListener(new BottemRelativeLayout.onDragStateChangeListener(){

            @Override
            public void onOpen() {
                Log.e("onOpen","yyyyyyyyyyyyyyyyyy");
            }

            @Override
            public void onClose() {
                Log.e("onClose","yyyyyyyyyyyyyyyyyy");
            }

            @Override
            public void Draging(float fraction) {
                Log.e("Draging","-------------"+fraction);
            }
        });
        layout1= (RelativeLayout) findViewById(R.id.layout1);
        layout2= (RelativeLayout) findViewById(R.id.layout2);
        textview= (TextView) findViewById(R.id.textView);
        textview.setClickable(true);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick","------------");
                flags=!flags;
                if(flags)main.open();
                else main.close();
            }
        });
        myView= (RelativeLayout) findViewById(R.id.myView);


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
