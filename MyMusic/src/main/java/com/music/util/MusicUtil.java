package com.music.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.app.apputil.MusicAction;
import com.app.myapp.MyMusicApp;
import com.app.myapp.R;
import com.music.actutil.LocalSearch;
import com.music.database.TableMusicPlayRecord;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2017/12/7.
 */

public class MusicUtil {
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }
    public static int getMusicListOdex(long id){
        int odex=-1;
        for(long i:musicPlayItem.music_id_list){
            odex++;
            if(i==id){
                break;}
        }
        return odex;
    }
    public  static class Music{
        //获取专辑封面的Uri
        public static MusicPlayItem musicPlayItem=new MusicPlayItem(true);
        private static MusicPlayItem musicPlayItem2;
        public static final int PLAY_RECORDS_LOCALLY_SIZE=10;
        private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        private static MediaPlayer mp =null;
        private static boolean clickStop=false;
        private static PlayStatus playStatus=PlayStatus.stop;
        private static PlayType playType=PlayType.cycle;
        public static MyMusicApp context;


        private static Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    if(playType==PlayType.cycle){
                        if(musicPlayItem.odex==musicPlayItem.musicList.size()-1)musicPlayItem.odex=0;
                        musicPlayItem.odex++;
                    }else if(playType==PlayType.noRules){
                        musicPlayItem.odex=(int)(Math.random()*musicPlayItem.musicList.size()-1);
                    }
                    play(musicPlayItem.odex);
                }
            }
        };

        public static void init_data(MyMusicApp context){
             Music.context=context;
        }
        public static void setMusicPlayItem(MusicPlayItem item){
            if(musicPlayItem.isLocal()){
                musicPlayItem2=musicPlayItem;
            }
            musicPlayItem=item;
        }
        public static void resetMusicPlayItem(){
            musicPlayItem=musicPlayItem2;
        }
        public static void switchPlayType(ImageView imageView){
            if(playType==PlayType.cycle){
                playType=PlayType.noRules;
                imageView.setImageResource(R.mipmap.random_normall);
            }else if(playType==PlayType.Singles){
                playType=PlayType.cycle;
                imageView.setImageResource(R.mipmap.loop_play);
            }else{
                playType=PlayType.Singles;
                imageView.setImageResource(R.mipmap.single_sycle);
            }
        }
        public static void setCurrentPlayType(Context context,ImageView imageView){
            if(playType==PlayType.cycle){
                imageView.setImageResource(R.mipmap.loop_play);
            }else if(playType==PlayType.Singles){
                imageView.setImageResource(R.mipmap.single_sycle);
            }else{
                imageView.setImageResource(R.mipmap.random_normall);
            }
        }

        public static void next(){
            if(mp!=null)
            mp.stop();
            if(playType==PlayType.cycle){
                if(musicPlayItem.odex==musicPlayItem.musicList.size()-1)musicPlayItem.odex=0;
                musicPlayItem.odex++;
            }else if(playType==PlayType.noRules){
                musicPlayItem.odex=(int)(Math.random()*musicPlayItem.musicList.size()-1);
            }
            Intent intent=new Intent();
            intent.setAction("com.musion.ACTION_NEXT");
            context.sendBroadcast(intent);
            play(musicPlayItem.odex);
        }
        public static PlayType getPlayType() {
            return playType;
        }
        public static MusicMeaaage getCurrentMusicMessage(){
            if(musicPlayItem.musicList==null||musicPlayItem.musicList.size()==0){
                return new MusicMeaaage();
            }
            return  musicPlayItem.musicList.get(musicPlayItem.getOdex());
        }
        public static PlayStatus getPlayStatus() {
            return playStatus;
        }
        private  static void setPlayStatus(PlayStatus playStatus){
            Music.playStatus=playStatus;
        }
        public static void setPlayType(PlayType playType) {
            Music.playType = playType;
            SharedPreferences.Editor editor = context.getSharedPreferences("my_music_app", context.MODE_PRIVATE).edit();
            //步骤2-2：将获取过来的值放入文件
            editor.putInt("CurrentPlayType", playType.type);
            //步骤3：提交
            editor.commit();
        }

        public static ArrayList<MusicMeaaage> scanningMusic() {
            ArrayList<MusicMeaaage> musicList=new ArrayList<>();
            musicPlayItem.music_id_list=new ArrayList<>();
            int odex=0;
            ContentResolver mResolver = context.getContentResolver();
            Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));//title
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));//时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));//大小
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));//路径
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));//专辑
                int album_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));//专辑
                long id= cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));   //音乐id
                musicList.add(new MusicMeaaage(title,url,album,duration,size,album_id,id,odex));
                musicPlayItem.music_id_list.add(id);
                odex++;
            }
            musicPlayItem.musicList=musicList;
            return musicList;
        }

        public static int switchNextMusic(){
            if(musicPlayItem.odex==musicPlayItem.musicList.size()-1){
                musicPlayItem.odex=0;
            }else{
                musicPlayItem.odex++;
            }


            return musicPlayItem.odex;
        }
        public static void start(){
            if(mp!=null){
                mp.start();
                playStatus=PlayStatus.play;

            }else{

            }
        }
        public static boolean play( int i){
            if(musicPlayItem.musicList==null||musicPlayItem.musicList.size()==0){
                return false;
            }
            if(mp!=null){
                if(playStatus==PlayStatus.pause&&i==musicPlayItem.odex){
                    playStatus=PlayStatus.play;
                    mp.start();
                    return true;
                }
                mp.reset();
            }else {
                mp = new MediaPlayer();
            }
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    handler.sendEmptyMessage(1);
                }
            });

            try {
                mp.setDataSource(context,musicPlayItem.musicList.get(i).getUrl());
            } catch (IOException e) {
                Toast.makeText(context,"--setDataSource fail--",Toast.LENGTH_SHORT).show();
            }
            try {
                mp.prepare();
            } catch (IOException e) {
                Toast.makeText(context,"--prepare fail--",Toast.LENGTH_SHORT).show();
            }
            mp.start();
            musicPlayItem.setOdex(i);
            setPlayStatus(PlayStatus.play);
            TableMusicPlayRecord.insert(context,(int)musicPlayItem.musicList.get(i).getId());
            LocalSearch.flushData(context);
            return true;
        }
        public static void pause(){
            if(mp!=null){
            if(mp.isPlaying()){
                mp.pause();
                setPlayStatus(PlayStatus.pause);
            }}
        }
        public static void setProcess(int i){
            if(mp!=null)
            mp.seekTo(i);
        }
        public static int getProcess(){
            if(mp!=null){

                return mp.getCurrentPosition();
            }else{
                return 0;
            }
        }
        public static void stop(){
            Log.e("--stop-success----:","start  play");
            if(mp!=null&&mp.isPlaying()){
                mp.stop();
                mp.release();
                mp=null;
                setPlayStatus(PlayStatus.stop);
            }
        }
        /**
         * 获取默认专辑图片
         * @param context
         * @return
         */
        public static Bitmap getDefaultArtwork(Context context,boolean small) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            if(small){  //返回小图片
                return BitmapFactory.decodeResource(context.getResources(),R.mipmap.music_default_small,opts);
            }

            return BitmapFactory.decodeResource(context.getResources(),R.mipmap.music_default_background,opts);
        }


        /**
         * 从文件当中获取专辑封面位图
         * @param context
         * @param songid
         * @param albumid
         * @return
         */
        private static Bitmap getArtworkFromFile(Context context, long songid, long albumid){
            Bitmap bm = null;
            if(albumid < 0 && songid < 0) {
                throw new IllegalArgumentException("Must specify an album or RequestPermission song id");
            }
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                FileDescriptor fd = null;
                if(albumid < 0){
                    Uri uri = Uri.parse("content://media/external/audio/media/"
                            + songid + "/albumart");
                    ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                    if(pfd != null) {
                        fd = pfd.getFileDescriptor();
                    }
                } else {
                    Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
                    ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                    if(pfd != null) {
                        fd = pfd.getFileDescriptor();
                    }
                }
                options.inSampleSize = 1;
                // 只进行大小判断
                options.inJustDecodeBounds = true;
                // 调用此方法得到options得到图片大小
                BitmapFactory.decodeFileDescriptor(fd, null, options);
                // 我们的目标是在800pixel的画面上显示
                // 所以需要调用computeSampleSize得到图片缩放的比例
                options.inSampleSize = 100;
                // 我们得到了缩放的比例，现在开始正式读入Bitmap数据
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                //根据options参数，减少所需要的内存
                bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return bm;
        }

        /**
         * 获取专辑封面位图对象
         * @param context
         * @param song_id
         * @param album_id
         * @param allowdefalut
         * @return
         */
        public static Bitmap getArtwork(Context context, long song_id, long album_id, boolean allowdefalut, boolean small){
            if(album_id < 0) {
                if(song_id < 0) {
                    Bitmap bm = getArtworkFromFile(context, song_id, -1);
                    if(bm != null) {
                        return bm;
                    }
                }
                if(allowdefalut) {
                    return getDefaultArtwork(context, small);
                }
                return null;
            }
            ContentResolver res = context.getContentResolver();
            Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
            if(uri != null) {
                InputStream in = null;
                try {
                    in = res.openInputStream(uri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //先制定原始大小
                    options.inSampleSize = 1;
                    //只进行大小判断
                    options.inJustDecodeBounds = true;
                    //调用此方法得到options得到图片的大小
                    BitmapFactory.decodeStream(in, null, options);
                    /** 我们的目标是在你N pixel的画面上显示。 所以需要调用computeSampleSize得到图片缩放的比例 **/
                    /** 这里的target为800是根据默认专辑图片大小决定的，800只是测试数字但是试验后发现完美的结合 **/
                    if(small){
                        options.inSampleSize = computeSampleSize(options, 40);
                    } else{
                        options.inSampleSize = computeSampleSize(options, 600);
                    }
                    // 我们得到了缩放比例，现在开始正式读入Bitmap数据
                    options.inJustDecodeBounds = false;
                    options.inDither = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    in = res.openInputStream(uri);
                    return BitmapFactory.decodeStream(in, null, options);
                } catch (FileNotFoundException e) {
                    Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                    if(bm != null) {
                        if(bm.getConfig() == null) {
                            bm = bm.copy(Bitmap.Config.RGB_565, false);
                            if(bm == null && allowdefalut) {
                                return getDefaultArtwork(context, small);
                            }
                        }
                    } else if(allowdefalut) {
                        bm = getDefaultArtwork(context, small);
                    }
                    return bm;
                } finally {
                    try {
                        if(in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        /**
         * 对图片进行合适的缩放
         * @param options
         * @param target
         * @return
         */
        public static int computeSampleSize(BitmapFactory.Options options, int target) {
            int w = options.outWidth;
            int h = options.outHeight;
            int candidateW = w / target;
            int candidateH = h / target;
            int candidate = Math.max(candidateW, candidateH);
            if(candidate == 0) {
                return 1;
            }
            if(candidate > 1) {
                if((w > target) && (w / candidate) < target) {
                    candidate -= 1;
                }
            }
            if(candidate > 1) {
                if((h > target) && (h / candidate) < target) {
                    candidate -= 1;
                }
            }
            return candidate;
        }
        public static enum PlayType{
            Singles(1),cycle(2),noRules(3);
            public int type;
            private PlayType(int i){
                this.type=i;
            }


        }

        public static enum PlayStatus{
            pause(3),play(4),stop(5);
            private int type;
            private PlayStatus(int i){
                type=i;
            }

            public int getType() {
                return type;
            }
        }
        public static class MusicMeaaage{
            private String title, url, album;
            private int duration,album_id,odex;
            private long size,id;
            private boolean isNull=false;

            public MusicMeaaage() {
                isNull=true;
            }

            public MusicMeaaage(String title, String url, String album, int duration, long size, int album_id, long id) {
                this.title = title;
                this.url = url;
                this.album = album;
                this.duration = duration;
                this.size = size;
                this.album_id=album_id;
                this.id=id;

            }

            //title,url,album,duration,size,album_id,id,odex
            public MusicMeaaage(String title, String url, String album, int duration, long size,int album_id,long id ,int odex) {
                this.title = title;
                this.id = id;
                this.size = size;
                this.odex = odex;
                this.album_id = album_id;
                this.duration = duration;
                this.album = album;
                this.url = url;
            }
            public boolean isNull(){
                return isNull;
            }
            public boolean isSimilar(String input){
                if(input!=null&&!input.equals("")) {
                    if (album == null) {
                        if (title.contains(input)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (title.contains(input) || album.contains(input)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }else{
                    return false;
                }
            }
            public int getAlbum_id() {
                return album_id;
            }

            public long getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAlbum() {
                return album;
            }

            public void setAlbum(String album) {
                this.album = album;
            }

            public Uri getUrl() {
                return Uri.parse(url);
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public long getSize() {
                return size;
            }

            public void setSize(long size) {
                this.size = size;
            }

            public int getOdex() {
                return odex;
            }

            public void setOdex(int odex) {
                this.odex = odex;
            }
        }
    }
    public static void PhoneStatusChanger(int status){

        try {
            switch (status) {
                case MusicAction.PHONE_CALL_STATE_RINGING:   //来电
                    MusicUtil.Music.pause();
                    break;
                case MusicAction.PHONE_CALL_STATE_OFFHOOK:   //接通电话
                    MusicUtil.Music.pause();
                    break;
                case MusicAction.PHONE_CALL_STATE_IDLE:  //挂掉电话
                    MusicUtil.Music.start();
                    Log.e("--start-----:","start  play");
                    break;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    public static class MusicData{
        public static final String CURRENT_MUSIC_KEY="current_play_music_id";
        public static final String CURRENT_PLAY_TYPE_KEY="current_play_type_id";
        private static final String MUSIC_FILE_NAME="data_file_name";
        private  static SharedPreferences spf;
        public static Class<MusicData> setSpf(Context context,String key,int id){
            spf=context.getSharedPreferences(MUSIC_FILE_NAME,Context.MODE_PRIVATE);
            spf.edit().putInt(key,id).commit();
            spf=null;
            return MusicData.class;
        }
        public static Class<MusicData> setSpf(Context context,String key,String id){
            spf=context.getSharedPreferences(MUSIC_FILE_NAME,Context.MODE_PRIVATE);
            spf.edit().putString(key,id).commit();
            spf=null;
            return MusicData.class;
        }
        public static String getSpfString(Context context,String key){
            spf=context.getSharedPreferences(MUSIC_FILE_NAME,Context.MODE_PRIVATE);
            String resert=spf.getString(key,null);
            spf=null;
            return resert;

        }
        public static int getSpfInt(Context context,String key){
            spf=context.getSharedPreferences(MUSIC_FILE_NAME,Context.MODE_PRIVATE);
            int resert=spf.getInt(key,-1);
            spf=null;
            return resert;
        }
public static void saveData(Context context){
    MusicUtil.MusicData.setSpf(context, MusicUtil.MusicData.CURRENT_MUSIC_KEY,musicPlayItem.getOdex());
    MusicUtil.MusicData.setSpf(context, MusicUtil.MusicData.CURRENT_PLAY_TYPE_KEY, MusicUtil.Music.getPlayType().type);
}

    }
}
