package com.music.broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.app.apputil.TelePhoneAction;

/**
 * Created by 501000704 on 2017/12/10.
 */

public class MusicBroadCastReceiver extends BroadcastReceiver {
    private CallBack  callBack;
    @Override
    public void onReceive(Context context, Intent intent) {
           callBack.onReceive(context,intent);
    }
    public void setCallBack(CallBack callBack){
        this.callBack=callBack;
    }
    public static interface CallBack{
        public void onReceive(Context context, Intent intent);
    }


}
