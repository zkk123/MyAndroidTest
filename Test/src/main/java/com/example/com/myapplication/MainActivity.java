package com.example.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.view.BottemRelativeLayout;
import com.test.view.MySurfaceView;
import com.test.view.MyView;
import com.test.view.testSurfaceView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout1,myView;
    BottemRelativeLayout main;
    MySurfaceView surfaceView;
    Button sur,bot,test;
    boolean flags=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main= (BottemRelativeLayout) findViewById(R.id.activity_main);
        sur= (Button) findViewById(R.id.surfaceView);
        test= (Button) findViewById(R.id.surfaceTest);
        bot= (Button) findViewById(R.id.bottem);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });
        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.open();
            }
        });
        sur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,testSurfaceView.class));
            }
        });
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


        myView= (RelativeLayout) findViewById(R.id.myView);



    }
}
