package com.music.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.myapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {
    private TextView skip;
    private int secound=10;
    private Handler handler;
    private Timer timer,timer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        init_view();
    }
    private void init_view(){
        skip=(TextView) findViewById(R.id.click_skip);
        skip.setText("("+secound+"S)跳过");
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==001)
                    secound--;
                if(secound<6) {
                    skip.setText("(" + secound + "S)点击跳过");
                }else{
                    skip.setText("(" + secound + "S)跳过");
                }

            }
        };

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(001);

            }
        },1000,1000);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(secound<6){
                startActivity(new Intent(LogoActivity.this,MainActivity.class));
                timer.cancel();
                timer2.cancel();
                finish();}
            }
        });
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LogoActivity.this,MainActivity.class));
                timer.cancel();
                finish();
            }
        };
        timer2=new Timer();
        timer2.schedule(timerTask,10000);
    }
}
