package com.app.myapp;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.app.apputil.MyAPP;
import com.app.apputil.TelePhoneAction;
import com.music.activity.BaseActivity;
import com.music.broadcast.MusicBroadCastReceiver;
import com.music.database.MusicDB;
import com.music.util.MusicUtil;
import com.music.service.MusicService;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2017/12/9.
 */

public class MyMusicApp extends Application {
    private MusicService musicService;
    MusicBroadCastReceiver receiver;
    private MusicBroadCastReceiver musicBroadCastReceiver;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService=((MusicService.MusicBinder)iBinder).getMusicService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("permissions","start1");
        MusicUtil.Music.init_data(this);
        bindService();
        init_data();
        MusicDB.init_data(getApplicationContext());
        TelePhoneAction.addPhoneListener(getApplicationContext());

    }

    private void bindService(){
        Intent bindIntent = new Intent(this, MusicService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }
    public MusicService getMusicService(){
        return musicService;
    }
    private void stopService(){
        stopService( new Intent(this, MusicService.class));
    }
    public void play(int odex){
        MusicUtil.Music.play(odex);
    }
    public void pause(){
        MusicUtil.Music.pause();
    }
    public void stop(){
        MusicUtil.Music.stop();
    }
    public void init_data(){
        MusicUtil.Music.scanningMusic();
        musicPlayItem.setOdex(MusicUtil.MusicData.getSpfInt(this,MusicUtil.MusicData.CURRENT_MUSIC_KEY));
        int value =MusicUtil.MusicData.getSpfInt(this,MusicUtil.MusicData.CURRENT_PLAY_TYPE_KEY);
        if(value==-1){
            MusicUtil.Music.setPlayType(MusicUtil.Music.PlayType.cycle);
            MusicUtil.MusicData.setSpf(this,MusicUtil.MusicData.CURRENT_PLAY_TYPE_KEY,MusicUtil.Music.PlayType.cycle.type);
        }else{

                if (value== MusicUtil.Music.PlayType.cycle.type){
                    MusicUtil.Music.setPlayType(MusicUtil.Music.PlayType.cycle);
                }else if (value== MusicUtil.Music.PlayType.Singles.type){
                    MusicUtil.Music.setPlayType(MusicUtil.Music.PlayType.Singles);
                }else if (value== MusicUtil.Music.PlayType.noRules.type){
                    MusicUtil.Music.setPlayType(MusicUtil.Music.PlayType.noRules);
                }



        }

    }
    public void initBroadCast(){
        musicBroadCastReceiver=new MusicBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(musicBroadCastReceiver, intentFilter);
        musicBroadCastReceiver.setCallBack(new MusicBroadCastReceiver.CallBack() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        });
    }



}
