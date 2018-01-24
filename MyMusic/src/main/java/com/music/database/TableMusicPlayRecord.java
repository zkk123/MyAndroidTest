package com.music.database;

import android.content.Context;
import android.database.Cursor;

import com.app.my.MyMusicData;
import com.music.actutil.LocalSearch;
import com.music.util.Lg;
import com.music.util.MusicPlayItem;
import com.music.util.MusicUtil;

import java.util.ArrayList;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2018/1/8.
 */

public class TableMusicPlayRecord extends BaseTable{


    public static void creartTable(Context context){
        MusicDB.create(context).getDatabase(true).execSQL(SQLStatement.CREAT_TABLE);
        close();
    }
    public static void init_data(Context context){
        ArrayList<MusicUtil.Music.MusicMeaaage>list=new ArrayList<>();
        ArrayList<Integer> list2=getData(context,0,false);
        for(int i:list2){
            list.add(musicPlayItem.musicList.get(MusicUtil.getMusicListOdex(i)));
        }
        MyMusicData.recordPlayItem=new MusicPlayItem();
        MyMusicData.recordPlayItem.musicList=list;
    }
    public static void insert(Context context,int value){
       if(LocalSearch.play_records_locally.contains(value)){
           delete(context,value);
       }
        //插入数据
        String insert="INSERT INTO "+Field.TABLIE_NAME+" ("+Field.MUSIC_ID+") VALUES ("+value+");";
        MusicDB.create(context).getDatabase(true).execSQL(insert);
        close();
    }
    public static void delete(Context context,long value){
        MusicDB.create(context).getDatabase(true).delete(Field.TABLIE_NAME,Field.MUSIC_ID+"=?",new String[]{value+""});
        close();
    }
    public static void deleteAll(Context context){
        MusicDB.create(context).getDatabase(true).delete(Field.TABLIE_NAME,Field.ID+">?",new String[]{"-1"});
        close();
    }
    public static ArrayList<Integer> getData(Context context,int size,boolean isOrder){
        ArrayList<Integer> ls=new ArrayList<>();
        Cursor cursor = MusicDB.create(context).getDatabase(true).rawQuery("select * from "+Field.TABLIE_NAME, null);
        int odex=0;
        if(size>0){
        if(isOrder){
        while (cursor.moveToNext()) {


                int music_id = cursor.getInt(1); //获取第一列的值,第一列的索引从0开始
                ls.add(music_id);
                odex++;
                if(odex==size){
                    break;
                }
            }
            cursor.close();
            close();

        }
        else{
            ArrayList<Integer> lss=new ArrayList<>();
            while (cursor.moveToNext()) {
                int music_id = cursor.getInt(1); //获取第一列的值,第一列的索引从0开始
                lss.add(music_id);

            }
            cursor.close();
            close();
            int length=lss.size();
                for (int a = 0; a < lss.size(); a++) {
                    ls.add(lss.get(length - 1 - a));
                    odex++;
                    if(odex==size)break;

                }

        }}
        return ls;
    }
    private static class SQLStatement{
        public static final String  CREAT_TABLE = "CREATE TABLE IF NOT EXISTS  "+Field.TABLIE_NAME+"(" + Field.ID + " integer primary key autoincrement , " + Field.MUSIC_ID + " integer not null);";

    }
    private static class Field{

        public static final String TABLIE_NAME="music_record_play";
        public static final String ID="ID";
        public static final String MUSIC_ID="music_id";


    }
}
