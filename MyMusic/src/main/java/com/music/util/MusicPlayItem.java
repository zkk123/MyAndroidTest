package com.music.util;

import java.util.ArrayList;

/**
 * Created by 501000704 on 2018/1/15.
 */

public class MusicPlayItem {
    private boolean isLocal=false;
    public  ArrayList<MusicUtil.Music.MusicMeaaage> musicList;
    public  ArrayList<Long> music_id_list;
    int odex=0;
    int currentMusicProcess=0;
    public MusicPlayItem(){

    }
    public MusicPlayItem(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public  int getOdex() {
        return odex;
    }
    public static void setOdex(int odex) {
        if(odex==-1)odex=0;
        odex = odex;

    }
    public boolean isLocal(){
        return isLocal;
    }
    public void setLocalValue(boolean local) {
        isLocal = local;
    }
}
