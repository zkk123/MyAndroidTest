package com.music.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.apputil.MyAPP;

import com.app.myapp.R;
import com.music.broadcast.MusicBroadCastReceiver;
import com.music.LrcUtil.LrcInfo;
import com.music.LrcUtil.LrcRead;
import com.music.LrcUtil.LrcView;
import com.music.util.MusicUtil;
import com.zkk.view.MarqueeView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.music.util.MusicUtil.Music.musicPlayItem;

public class MusicActivity extends BaseActivity implements View.OnClickListener {
    private ImageView play, stop,next,play_type,back;
    private TextView title,ablum;
    private MarqueeView currentMusic;
    private LrcView mp3_words;
    private SeekBar seekBar;
    private LrcRead read;
    Timer timer;
    LinearLayout content;
    RelativeLayout music_words;
    private MusicBroadCastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Explode().setDuration(50));
        //getWindow().setExitTransition(new Explode().setDuration(1000));
        setContentView(R.layout.activity_music);
        init_view();
        MyAPP.setActivity(this);
        registerReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MusicUtil.Music.setCurrentPlayType(this,play_type);
        fulshPlayButton();
        currentMusic.startScroll();
    }
    public void setBackground(){
        MusicUtil.Music.MusicMeaaage msg= musicPlayItem.musicList.get(musicPlayItem.getOdex());
        Drawable drawable =new BitmapDrawable(MusicUtil.Music.getArtwork(MusicActivity.this,msg.getId(),msg.getAlbum_id(),true,false));
        content.setBackground(drawable);
    }

  private  void registerReceiver(){
       receiver = new MusicBroadCastReceiver();//广播接受者实例
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("com.musion.ACTION_NEXT");
      registerReceiver(receiver, intentFilter);
      receiver.setCallBack(new MusicBroadCastReceiver.CallBack() {
          @Override
          public void onReceive(Context context, Intent intent) {
              currentMusic.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getTitle());
              title.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getTitle());
              ablum.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getAlbum());
              setBackground();
          }
      });
  }

    private void init_view() {

        mp3_words= (LrcView) findViewById(R.id.mp3_words);
        read=new LrcRead();
        music_words= (RelativeLayout) findViewById(R.id.music_words);
        back=(ImageView) findViewById(R.id.music_back);
        content= (LinearLayout) findViewById(R.id.activity_music);
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        play.setImageResource(R.mipmap.play);
        stop = (ImageView) findViewById(R.id.stop);
        play_type=(ImageView) findViewById(R.id.play_type);
        currentMusic = (MarqueeView) findViewById(R.id.mMarqueeView);
        title = (TextView) findViewById(R.id.title_music);
        ablum = (TextView) findViewById(R.id.ablum_music);
        ablum.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getAlbum());
        title.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getTitle());
        currentMusic.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getTitle());
        seekBar = (SeekBar) findViewById(R.id.music_seekBar);
        seekBar.setMax(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicUtil.Music.setProcess(seekBar.getProgress());
            }
        });
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        play_type.setOnClickListener(this);
        back.setOnClickListener(this);
        if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    seekBar.setProgress(MusicUtil.Music.getProcess());
                }
            }, 100, 10);
        }else{
            seekBar.setProgress(MusicUtil.Music.getProcess());
        }
        setBackground();
    }
    public void setCurrentMusic(String name){
        currentMusic.setText(name);
    }

    public void setMusicLrcPath(String lrcPath){
        try {

            read.Read(lrcPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp3_words.setmLrcList(new LrcInfo(read.GetLyricContent()));

    }
    public void setIndex(int index){
        mp3_words.setIndex(index);
    }
    @Override
    public void onClick(View view) {
        if (musicPlayItem.musicList.size() == 0) return;
        if (view.getId() == play.getId()) {
            if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
                if(timer!=null) timer.cancel();
                MusicUtil.Music.context.pause();

            } else {
                MusicUtil.Music.context.play(musicPlayItem.getOdex());

                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar.setProgress(MusicUtil.Music.getProcess());
                    }
                }, 100, 10);
            }
        } else if (view.getId() == stop.getId()) {
            if(timer!=null) timer.cancel();
            MusicUtil.Music.context.stop();
            seekBar.setProgress(0);
        }else if (view.getId() == next.getId()) {
            MusicUtil.Music.next();
        }else if(view.getId()==play_type.getId()){
            MusicUtil.Music.switchPlayType(play_type);
        }else if(view.getId()==back.getId()){
            startActivity(new Intent(this, MusicListActivity.class), ActivityOptions.makeSceneTransitionAnimation(this,findViewById(R.id.activity_music),"shareName").toBundle());
        }
        fulshPlayButton();
    }

    public void fulshPlayButton() {
        if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
            play.setImageResource(R.mipmap.pause);
        } else {
            play.setImageResource(R.mipmap.play);
        }
        if(MusicUtil.Music.getPlayStatus()== MusicUtil.Music.PlayStatus.stop)
        seekBar.setProgress(0);

    }
    public void startTimeRecord(){

    }

    @Override
    public void flushView(int action) {
        fulshPlayButton();
    }
    @Override
    protected void onStop() {
        super.onStop();
        MusicUtil.MusicData.saveData(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        MyAPP.realseActivity();
    }
}
