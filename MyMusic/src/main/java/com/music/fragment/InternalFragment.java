package com.music.fragment;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myapp.R;
import com.music.activity.LocalSearchActivity;
import com.music.activity.TestActivity;
import com.music.actutil.LocalSearch;
import com.music.adapter.MusicSearchResurtAdapter;
import com.zkk.view.SearchView;
import com.zkk.view.XCFlowLayout;

/**
 * Created by 501000704 on 2018/1/5.
 */

public class InternalFragment extends Fragment {
    XCFlowLayout hotWordsLayout;
    private  View view;
    private SearchView searchView;
    private  String mNames[]={
            "难念的经","祝你平安","漂亮的姑娘就要嫁人了",
            "apple","jamy","kobe bryant",
            "jordan","神话","viewgroup",
            "margin","padding","text",
            "name","尽头","十年","logcat"
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.internal_fragment,container,false);
        hotWordsLayout= (XCFlowLayout) view.findViewById(R.id.hotlayout);

        hotWordsLayout.setAdapter(new XCFlowLayout.Adapter() {
            @Override
            public int getCount() {
                return mNames.length;
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
            public View getView(final int i, ViewGroup viewGroup) {
                TextView view = new TextView(getActivity());
                view.setText(mNames[i]);
                view.setBackgroundResource(R.drawable.hot_words);
                view.setPadding(40,20,40,20);
                view.setGravity(Gravity.CENTER);
                view.setTextColor(Color.argb(255 ,56 ,54 ,54));
                view.setTextSize(11);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),mNames[i],Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }
        });
        initview(view);
        return view;

    }
    public void initview(View view){
        searchView = (SearchView) view.findViewById(R.id.local_searchView);
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

            }
        });
    }
}
