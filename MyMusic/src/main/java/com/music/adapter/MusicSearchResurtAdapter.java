package com.music.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.myapp.R;
import com.music.activity.LocalSearchActivity;
import com.music.activity.MusicListActivity;
import com.music.util.MusicUtil;
import java.util.ArrayList;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2017/12/26.
 */

public class MusicSearchResurtAdapter extends BaseAdapter {
    private ArrayList<MusicUtil.Music.MusicMeaaage> list;
    private Context context;

    public MusicSearchResurtAdapter(Context context, ArrayList<MusicUtil.Music.MusicMeaaage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
       if(view==null) {
           view = LayoutInflater.from(context).inflate(R.layout.music_search_resurt_item, null);
           TextView title = (TextView) view.findViewById(R.id.resurt_title);
           TextView albem = (TextView) view.findViewById(R.id.resurt_album);

           viewHolder=new ViewHolder();
           viewHolder.title=title;
           viewHolder.albem=albem;
           view.setTag(viewHolder);

       }else{
           viewHolder= (ViewHolder) view.getTag();

       }
        Log.e("Title"+i,list.get(i).getTitle()+"");
        Log.e("Album"+i,list.get(i).getAlbum()+"");
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.albem.setText(list.get(i).getAlbum());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicListActivity.class);
                int intent_odex = list.get(i).getOdex();
                intent.putExtra("odex", intent_odex);
                musicPlayItem.setOdex(intent_odex);
                context.startActivity(intent);

            }
        });
        return view;
    }
    private class ViewHolder{
        TextView title;
        TextView albem;

    }

}
