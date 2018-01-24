package com.app.apputil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.music.activity.BaseActivity;
import com.music.util.MusicUtil;

/**
 * Created by 501000704 on 2017/12/27.
 */

public class TelePhoneAction {

    private static TelephonyManager tm;
    public static  void addPhoneListener(Context context){
        if(tm!=null){
            return;
        }
        tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(new PhoneListener(context), PhoneStateListener.LISTEN_CALL_STATE);


    }
    public static void addPhoneListener(Intent intent){
        if(intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){ //通话状态改变的广播
            //来电号码
            String incomingNum = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            //改变的状态
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(TelephonyManager.EXTRA_STATE_IDLE.equals(state)){
                MusicUtil.PhoneStatusChanger(MusicAction.PHONE_CALL_STATE_IDLE);
                flushActivityView(MusicAction.PHONE_CALL_STATE_IDLE);
            } else if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)){
                MusicUtil.PhoneStatusChanger(MusicAction.PHONE_CALL_STATE_OFFHOOK);
                flushActivityView(MusicAction.PHONE_CALL_STATE_OFFHOOK);
                //通话状态，对于呼出而言，开始就是这个状态了；对于接听者而言，接起电话就是这个状态了
            } else if(TelephonyManager.EXTRA_STATE_RINGING.equals(state)){
                MusicUtil.PhoneStatusChanger(MusicAction.PHONE_CALL_STATE_RINGING);
                flushActivityView(MusicAction.PHONE_CALL_STATE_RINGING);
                //响铃状态，即来电
            }

        }
    }
    private static void  flushActivityView(int action){
        if(MyAPP.activity!=null) {
            MyAPP.activity.flushView(action);
        }
    }
    private final static class PhoneListener extends PhoneStateListener {
        private Context context;
        public PhoneListener(Context context) {
            this.context = context;
        }


    }
}
