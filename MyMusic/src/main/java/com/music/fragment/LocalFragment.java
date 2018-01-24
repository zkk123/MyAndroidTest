package com.music.fragment;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myapp.R;
import com.music.activity.LocalSearchActivity;
import com.music.activity.MusicListActivity;
import com.music.actutil.LocalSearch;
import com.music.adapter.MusicSearchResurtAdapter;
import com.music.util.Lg;
import com.music.util.MusicUtil;
import com.zkk.view.HotWordsLayout;
import com.zkk.view.SearchView;
import com.zkk.view.XCFlowLayout;

import java.util.ArrayList;

import static com.music.util.MusicUtil.Music.musicPlayItem;

/**
 * Created by 501000704 on 2018/1/5.
 */

public class LocalFragment extends Fragment {
    private  View view;
    private SearchView searchView;
    private ListView resurtList;
    private LocalSearch localSearch;
    private XCFlowLayout hotWordsLayout;
    private TextView history_word;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.local_fragment,container,false);
        initview(view);
        return view;

    }
    public void initview(View view){
        history_word= (TextView) view.findViewById(R.id.history_word);
        localSearch=new LocalSearch((LocalSearchActivity) getActivity());
        searchView= (SearchView) view.findViewById(R.id.local_searchView);
        resurtList= (ListView) view.findViewById(R.id.resurt_list);
        searchView = (SearchView) view.findViewById(R.id.local_searchView);
        hotWordsLayout= (XCFlowLayout) view.findViewById(R.id.hotlayout);
        hotWordsLayout.setAdapter(new XCFlowLayout.Adapter() {
            @Override
            public int getCount() {
                int size=LocalSearch.play_records_locally_list_id.size();
                if(size<LocalSearch.RECORDS_MAX_SHOW_SIZE+1)return size;
                else{
                    return LocalSearch.RECORDS_MAX_SHOW_SIZE+1;
                }
            }

            @Override
            public ViewGroup.MarginLayoutParams getMarginLayoutParams(int i) {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = 5;
                lp.rightMargin = 5;
                lp.topMargin = 10;
                lp.bottomMargin = 10;
                return lp;
            }

            @Override
            public View getView(final int  i, ViewGroup viewGroup) {

                TextView view = new TextView(getActivity());
                String text=musicPlayItem.musicList.get(LocalSearch.play_records_locally_list_id.get(i)).getTitle();
                if(text.length()>16){
                    text=text.substring(0,16)+"...";
                }
                view.setBackgroundResource(R.drawable.hot_words);
                view.setPadding(40,20,40,20);
                view.setGravity(Gravity.CENTER);
                view.setTextColor(Color.argb(255 ,56 ,54 ,54));
                view.setTextSize(11);
                if(i==LocalSearch.RECORDS_MAX_SHOW_SIZE){
                    view.setText("更多...");
                }else{
                    view.setText(text);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), MusicListActivity.class);
                            int intent_odex = LocalSearch.play_records_locally_list_id.get(i);
                            intent.putExtra("odex", intent_odex);
                            musicPlayItem.setOdex(intent_odex);
                            getActivity().startActivity(intent);
                        }
                    });
                }
                return view;
            }
        });
        searchView.setOnClickDeleteListener(new com.zkk.view.SearchView.OnClickDeleteListener() {
            @Override
            public void onClick(com.zkk.view.SearchView view) {
                searchView.setText("");
            }
        });
        initAction();
    }
    public void initAction() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<MusicUtil.Music.MusicMeaaage>msgs=localSearch.SearchResurt(searchView.getText().toString().trim());
                if(msgs.size()>0) {
                    hotWordsLayout.setVisibility(View.GONE);
                    history_word.setVisibility(View.GONE);
                    resurtList.setAdapter(new MusicSearchResurtAdapter(getActivity(), localSearch.SearchResurt(searchView.getText().toString().trim())));
                }else{
                    hotWordsLayout.setVisibility(View.VISIBLE);
                    history_word.setVisibility(View.VISIBLE);
                    resurtList.setAdapter(new MusicSearchResurtAdapter(getActivity(), localSearch.SearchResurt(searchView.getText().toString().trim())));
                }
                }
        });
    }
}
