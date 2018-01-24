package com.music.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.music.actutil.LocalSearch;

/**
 * Created by 501000704 on 2018/1/8.
 */

public class MusicDB {
    private  DBHelper dbHelper;
    private static MusicDB myMusicDB;
    public  SQLiteDatabase db;
    private MusicDB(Context context){
        dbHelper=new DBHelper(context);
    }
    public static   MusicDB create(Context context){
          myMusicDB=new MusicDB(context);
          return myMusicDB;
    }
    public static void init_data(Context context){

        TableMusicPlayRecord.creartTable(context);
        TableMyCollection.creartTable(context).init_data(context);
        LocalSearch.initPlayRecordsLocally(context);
    }
    public SQLiteDatabase getDatabase(boolean isWrite){
        if(isWrite){
            db=dbHelper.getWritableDatabase();
        }else{
            db=dbHelper.getReadableDatabase();
        }
        return db;
    }
    public static void close(){
        if(myMusicDB!=null){
          if(myMusicDB.db!=null){
              myMusicDB.db.close();
          }
            myMusicDB=null;
        }

    }



}
