package com.music.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.myapp.R;
import com.music.broadcast.MusicBroadCastReceiver;
import com.music.util.Lg;
import com.music.util.MusicUtil;
import com.nineoldandroids.view.ViewHelper;
import com.zkk.view.MarqueeView;
import com.zkk.view.MyLinerLayout;
import com.zkk.view.MySlideMenu;

import java.util.Timer;
import java.util.TimerTask;

import static com.music.util.MusicUtil.Music.musicPlayItem;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private ImageView play, stop, next, play_type,search;
    private MarqueeView currentMusic;
    private RelativeLayout home,local,record,collection,songlist;
    private MySlideMenu mySlideMenu;
    private ImageView iv_head;//主界面左上角图标对象
   // private MyLinerLayout myLinerLayout;
    private MusicBroadCastReceiver receiver;
    private int back_times=0;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();
    }
    private void init_view(){

        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        play.setImageResource(R.mipmap.play);
        stop = (ImageView) findViewById(R.id.stop);
        play_type = (ImageView) findViewById(R.id.play_type);
        currentMusic = (MarqueeView) findViewById(R.id.mMarqueeView);
        currentMusic.setText(MusicUtil.Music.getCurrentMusicMessage().getTitle());
        currentMusic.setOnClickListener(this);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        play_type.setOnClickListener(this);
        home= (RelativeLayout) findViewById(R.id.main_homepage);
        local=(RelativeLayout) findViewById(R.id.main_local);
        record=(RelativeLayout) findViewById(R.id.main_record);
        collection=(RelativeLayout) findViewById(R.id.main_collection);
        songlist=(RelativeLayout) findViewById(R.id.main_songlist);
        home.setOnClickListener(this);
        local.setOnClickListener(this);
        record.setOnClickListener(this);
        collection.setOnClickListener(this);
        songlist.setOnClickListener(this);
       // myLinerLayout= (MyLinerLayout) findViewById(R.id.my_layout);
        mySlideMenu= (MySlideMenu) findViewById(R.id.myslidemenu);
        iv_head= (ImageView) findViewById(R.id.iv_head);
        search= (ImageView) findViewById(R.id.iv_search);
        search.setOnClickListener(this);
        //myLinerLayout.setSlideMenu(mySlideMenu);
        mySlideMenu.setOnDragStateChangeListener(new MySlideMenu.onDragStateChangeListener() {
            @Override
            public void onOpen() {
                ViewHelper.setAlpha(iv_head,0);
            }
            @Override
            public void onClose() {


            }
            @Override
            public void Draging(float fraction) {
                ViewHelper.setAlpha(iv_head,1-fraction);
            }
        });
        //点击头部图标事件处理
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_OPEN){
                    mySlideMenu.close();
                }else if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_CLOSE){
                    mySlideMenu.open();
                }
            }
        });
        registerReceiver();
        fulshPlayButton();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==home.getId()){
            if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_OPEN){
                mySlideMenu.close();
            }else if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_CLOSE){
                mySlideMenu.open();
            }
        }else if(view.getId()==local.getId()){
             startActivity(new Intent(this,MusicListActivity.class));
        }else if(view.getId()==record.getId()){

        }else if(view.getId()==collection.getId()){

        }else if(view.getId()==songlist.getId()){

        }else if(view.getId()==search.getId()){
             startActivity(new Intent(this,LocalSearchActivity.class));
            finish();
        }else if (view.getId() == play.getId()) {
            if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
                MusicUtil.Music.context.pause();
            } else {
                MusicUtil.Music.context.play(musicPlayItem.getOdex());
            }
            fulshPlayButton();
        } else if (view.getId() == stop.getId()) {
            MusicUtil.Music.context.stop();
            fulshPlayButton();
        } else if (view.getId() == currentMusic.getId()) {
            Intent intent = new Intent(this, MusicActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (view.getId() == next.getId()) {
            MusicUtil.Music.next();
            fulshPlayButton();
        } else if (view.getId() == play_type.getId()) {
            MusicUtil.Music.switchPlayType(play_type);
            fulshPlayButton();
        }

    }
    private void registerReceiver() {
        receiver = new MusicBroadCastReceiver();//广播接受者实例
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.musion.ACTION_NEXT");
        registerReceiver(receiver, intentFilter);
        receiver.setCallBack(new MusicBroadCastReceiver.CallBack() {
            @Override
            public void onReceive(Context context, Intent intent) {
                currentMusic.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getTitle());

            }
        });
    }
    public void fulshPlayButton() {
        if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
            play.setImageResource(R.mipmap.pause);
        } else {
            play.setImageResource(R.mipmap.play);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_OPEN){
            mySlideMenu.close();
        }
        currentMusic.startScroll();
        fulshPlayButton();
    }
    @Override
    public void onBackPressed() {
        if(back_times==0){
            Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
            back_times=1;
            timer=new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run() {
                  back_times=0;
                }
            },3000);
        }else{
            timer.cancel();
            super.onBackPressed();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
