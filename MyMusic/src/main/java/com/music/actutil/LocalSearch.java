package com.music.actutil;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import com.app.myapp.R;
import com.music.activity.LocalSearchActivity;
import com.music.activity.MusicListActivity;
import com.music.database.TableMusicPlayRecord;
import com.music.util.Lg;
import com.music.util.MusicUtil;

import java.util.ArrayList;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2017/12/25.
 */

public class LocalSearch {
    private LocalSearchActivity myActivity;
    public static ArrayList<Integer> play_records_locally;
    public static ArrayList<Integer> play_records_locally_list_id;
    public static final int RECORDS_MAX_SHOW_SIZE=15;
    public LocalSearch(LocalSearchActivity myActivity) {
        this.myActivity = myActivity;
    }

    public ArrayList<MusicUtil.Music.MusicMeaaage> SearchResurt(String input){
        ArrayList<MusicUtil.Music.MusicMeaaage> list=new ArrayList<>();
        for(int i=0;i<musicPlayItem.musicList.size();i++){
            MusicUtil.Music.MusicMeaaage mm=musicPlayItem.musicList.get(i);
            if(mm.isSimilar(input)){
                  list.add(mm);
            }
        }
        return list;
    }

    public static void initPlayRecordsLocally(Context context){
        play_records_locally_list_id=new ArrayList<>();
        play_records_locally=TableMusicPlayRecord.getData(context,50,false);
        Lg.print("list--"+3,"item");
        for(int i=0;i<play_records_locally.size();i++){
            play_records_locally_list_id.add(MusicUtil.getMusicListOdex(play_records_locally.get(i)));
        }

    }
    public static void flushData(Context context){
        initPlayRecordsLocally(context);
    }
}
