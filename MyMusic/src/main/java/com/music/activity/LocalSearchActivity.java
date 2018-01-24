package com.music.activity;




import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.app.myapp.R;
import com.music.fragment.InternalFragment;
import com.music.fragment.LocalFragment;
import com.music.util.Lg;

public class LocalSearchActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    LocalFragment localFragment=new LocalFragment();
    InternalFragment internalFragment=new InternalFragment();
    private RadioGroup radioGroup;
    private RadioButton local,internal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_search);

        initview();

    }
    public void initview(){
        local= (RadioButton) findViewById(R.id.local_btn);
        local.setChecked(true);
        internal= (RadioButton) findViewById(R.id.internal_btn);
        radioGroup= (RadioGroup) findViewById(R.id.search_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(local.getId()==i){
                    Lg.print("radioGroup","local");

                    switchFragment(true);
                }else{
                    Lg.print("radioGroup","internal");
                    switchFragment(false);
                }
            }
        });
         FragmentManager manager =getSupportFragmentManager();

         FragmentTransaction transaction = manager.beginTransaction();
         transaction.add(R.id.local_content,localFragment);
         transaction.commit();
         back= (ImageView) findViewById(R.id.local_back);
        back.setOnClickListener(this);

    }


    public void switchFragment(boolean isLocal){
        FragmentManager manager =getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        if(isLocal){
            transaction.replace(R.id.local_content,localFragment);
            transaction.commit();
        }else{
            transaction.replace(R.id.local_content,internalFragment);
            transaction.commit();
        }

    }
    @Override
    public void onClick(View view) {
        if(view.getId()==back.getId()){
            startActivity(new Intent(this,MusicListActivity.class));
        }
    }
}
