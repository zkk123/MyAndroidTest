package com.music.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.music.util.MusicUtil;

/**
 * Created by 501000704 on 2017/12/9.
 */

public class MusicService extends Service {
    private MusicBinder musicBinder=new MusicBinder();
    private CallBack callBack;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
    public void setProcess(int i){

    }
    public void setCallBack(CallBack callBack){
           this.callBack=callBack;
    }
    public class MusicBinder extends Binder {
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }
    public static interface CallBack {
        public int getProcess(int process);
    }
}
