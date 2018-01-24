package com.app.apputil;

import com.music.activity.BaseActivity;

/**
 * Created by 501000704 on 2017/12/27.
 */

public class MyAPP {
    public static BaseActivity activity;
    public static boolean isTest=false;
    public static void setActivity(BaseActivity mActivity){
        activity=mActivity;
    }
    public static void realseActivity(){
        activity=null;
    }

}
