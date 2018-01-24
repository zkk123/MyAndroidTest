package com.music.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app.apputil.MyAPP;
import com.app.apputil.RequestPermission;
import com.app.myapp.R;
import com.music.adapter.MusicListAdapter;
import com.music.broadcast.MusicBroadCastReceiver;
import com.music.util.Lg;
import com.music.util.MusicUtil;
import com.zkk.view.MarqueeView;
import com.zkk.view.SearchView;

import static com.music.util.MusicUtil.Music.musicPlayItem;

public class MusicListActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private ImageView play, stop, next, play_type;
    private MarqueeView currentMusic;
    private MusicListAdapter adapter;
    private TextView music_list_search;
    private MusicBroadCastReceiver receiver;
    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MyAPP.isTest){
            startActivity(new Intent(this,TestActivity.class));
        }
        setContentView(R.layout.activity_music_list);
        Log.e("permissions","start2");
        RequestPermission.requestPermission(this);
        init_view();
        registerReceiver();
        MyAPP.setActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();


        currentMusic.startScroll();
        MusicUtil.Music.setCurrentPlayType(this, play_type);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fulshPlayButton();

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
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init_view() {
        myIntent=getIntent();
        music_list_search = (TextView) findViewById(R.id.music_list_search);
        play_type = (ImageView) findViewById(R.id.play_type);
        listView = (ListView) findViewById(R.id.main_music_list_listview);
        adapter = new MusicListAdapter(musicPlayItem.musicList, this);
        listView.setAdapter(adapter);
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        play.setImageResource(R.mipmap.play);
        stop = (ImageView) findViewById(R.id.stop);
        currentMusic = (MarqueeView) findViewById(R.id.mMarqueeView);
        currentMusic.setText(musicPlayItem.musicList.get(musicPlayItem.getOdex()).getTitle());
        currentMusic.setOnClickListener(this);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        music_list_search.setOnClickListener(this);
        play_type.setOnClickListener(this);
        listView.setSelection(musicPlayItem.getOdex());
        if(myIntent!=null){
            int intent_odex=myIntent.getIntExtra("odex",-1);
            Lg.print("odex",intent_odex+"");
            if(intent_odex!=-1){
                listView.setSelection(intent_odex);
                MusicUtil.Music.context.play(musicPlayItem.getOdex());
                adapter.notifyDataSetChanged();
                flushView(0);
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (musicPlayItem.musicList.size() == 0) return;
        if (view.getId() == play.getId()) {
            if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
                MusicUtil.Music.context.pause();
            } else {
                MusicUtil.Music.context.play(musicPlayItem.getOdex());
            }
        } else if (view.getId() == stop.getId()) {
            MusicUtil.Music.context.stop();
        } else if (view.getId() == currentMusic.getId()) {
            Intent intent = new Intent(this, MusicActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (view.getId() == next.getId()) {
            MusicUtil.Music.next();
            listView.setSelection(musicPlayItem.getOdex());
        } else if (view.getId() == play_type.getId()) {
            MusicUtil.Music.switchPlayType(play_type);
        }else if(view.getId()==music_list_search.getId()){
            startActivity(new Intent(this, LocalSearchActivity.class),ActivityOptions.makeSceneTransitionAnimation(this,(RelativeLayout)findViewById(R.id.activity_music_list_temp1), "shareNames").toBundle());
        }
        fulshPlayButton();
    }

    public void setCurrentMusic(String name) {
        currentMusic.setText(name);
    }


    public void fulshPlayButton() {
        if (MusicUtil.Music.getPlayStatus() == MusicUtil.Music.PlayStatus.play) {
            play.setImageResource(R.mipmap.pause);
        } else {
            play.setImageResource(R.mipmap.play);
        }
    }
    @Override
    public void flushView(int action) {
        fulshPlayButton();
    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.

        MusicUtil.MusicData.saveData(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        MyAPP.realseActivity();

    }


}
