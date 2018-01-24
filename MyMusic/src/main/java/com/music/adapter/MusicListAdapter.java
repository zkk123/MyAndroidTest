package com.music.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.myapp.R;
import com.music.util.MusicUtil;
import com.music.activity.MusicListActivity;


import java.util.ArrayList;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2017/12/7.
 */

public class MusicListAdapter extends BaseAdapter {
    private ArrayList<MusicUtil.Music.MusicMeaaage> list;
    private MusicListActivity context;

    public MusicListAdapter(ArrayList<MusicUtil.Music.MusicMeaaage> list, MusicListActivity context) {
        this.list = list;
        this.context = context;
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
        ViewHolder viewHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.music_item, null);
            TextView title = (TextView) view.findViewById(R.id.music_itme_title);
            TextView album = (TextView) view.findViewById(R.id.music_itme_album);
            ImageView image = (ImageView) view.findViewById(R.id.music_itme_image);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.music_itme_checkbox);
            viewHolder = new ViewHolder();
            viewHolder.title = title;
            viewHolder.albem = album;
            viewHolder.image = image;
            viewHolder.checkBox = checkBox;
            view.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) view.getTag();

        }

        if(i== musicPlayItem.getOdex()){
            view.setBackground(context.getResources().getDrawable(R.drawable.music_item_after_type));
            view.setAlpha(0.8F);
        }else{
            view.setBackground(context.getResources().getDrawable(R.drawable.seacher_item));
            view.setAlpha(0.8F);
        }
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.albem.setText(list.get(i).getAlbum());
        viewHolder.checkBox.setVisibility(View.GONE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 MusicUtil.Music.context.play(i);
                 context.setCurrentMusic(musicPlayItem.musicList.get(i).getTitle());
                 context.fulshPlayButton();
                 notifyDataSetChanged();
            }
        });
        return view;
    }
    private class ViewHolder{
        TextView title;
        TextView albem;
        ImageView image;
        CheckBox checkBox;

    }
}
