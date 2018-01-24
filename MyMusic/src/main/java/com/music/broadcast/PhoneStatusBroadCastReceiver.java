package com.music.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.app.apputil.TelePhoneAction;

/**
 * Created by 501000704 on 2017/12/27.
 */

public class PhoneStatusBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("--getAction-----:",intent.getAction());
        Log.e("--action-----:",intent.getStringExtra(TelephonyManager.EXTRA_STATE));
        TelePhoneAction.addPhoneListener( intent);
    }
}
