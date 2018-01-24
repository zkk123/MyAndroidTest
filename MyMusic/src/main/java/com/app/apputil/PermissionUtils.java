package com.app.apputil;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.app.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 * Created by qianxiaoai on 2016/7/7.

 */

public class PermissionUtils {



    private static final String TAG = PermissionUtils.class.getSimpleName();

    public static final int CODE_RECORD_AUDIO = 0;

    public static final int CODE_GET_ACCOUNTS = 1;

    public static final int CODE_READ_PHONE_STATE = 2;

    public static final int CODE_CALL_PHONE = 3;

    public static final int CODE_CAMERA = 4;

    public static final int CODE_ACCESS_FINE_LOCATION = 5;

    public static final int CODE_ACCESS_COARSE_LOCATION = 6;

    public static final int CODE_READ_EXTERNAL_STORAGE = 7;

    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;

    public static final int CODE_MULTI_PERMISSION = 100;



    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;

    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;

    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;

    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;



    private static final String[] requestPermissions = {

            PERMISSION_RECORD_AUDIO,

            PERMISSION_GET_ACCOUNTS,

            PERMISSION_READ_PHONE_STATE,

            PERMISSION_CALL_PHONE,

            PERMISSION_CAMERA,

            PERMISSION_ACCESS_FINE_LOCATION,

            PERMISSION_ACCESS_COARSE_LOCATION,

            PERMISSION_READ_EXTERNAL_STORAGE,

            PERMISSION_WRITE_EXTERNAL_STORAGE

    };



    interface PermissionGrant {

        void onPermissionGranted(int requestCode);

    }







    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionGrant permissionGrant) {



        if (activity == null) {

            return;

        }



        //TODO

        Log.d(TAG, "onRequestPermissionsResult permissions length:" + permissions.length);

        Map<String, Integer> perms = new HashMap<>();



        ArrayList<String> notGranted = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {

            Log.d(TAG, "permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);

            perms.put(permissions[i], grantResults[i]);

            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                notGranted.add(permissions[i]);

            }

        }



        if (notGranted.size() == 0) {

            Toast.makeText(activity, "all permission success" + notGranted, Toast.LENGTH_SHORT)

                    .show();

            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);

        } else {

            openSettingActivity(activity, "those permission need granted!");

        }



    }





    /**

     * 一次申请多个权限

     */

    public static void requestMultiPermissions(final Activity activity, PermissionGrant grant) {



        final List<String> permissionsList = getNoGrantedPermission(activity, false);

        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);



        //TODO checkSelfPermission

        if (permissionsList == null || shouldRationalePermissionsList == null) {

            return;

        }

        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());



        if (permissionsList.size() > 0) {

            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),

                    CODE_MULTI_PERMISSION);

            Log.d(TAG, "showMessageOKCancel requestPermissions");



        } else if (shouldRationalePermissionsList.size() > 0) {

            showMessageOKCancel(activity, "should open those permission",

                    new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),

                                    CODE_MULTI_PERMISSION);

                            Log.d(TAG, "showMessageOKCancel requestPermissions");

                        }

                    });

        } else {

            grant.onPermissionGranted(CODE_MULTI_PERMISSION);

        }



    }









    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context)

                .setMessage(message)

                .setPositiveButton("OK", okListener)

                .setNegativeButton("Cancel", null)

                .create()

                .show();



    }








    private static void openSettingActivity(final Activity activity, String message) {



        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent();

                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                Log.d(TAG, "getPackageName(): " + activity.getPackageName());

                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);

                intent.setData(uri);

                activity.startActivity(intent);

            }

        });

    }





    /**

     * @param activity

     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale

     * @return

     */

    public static ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {



        ArrayList<String> permissions = new ArrayList<>();



        for (int i = 0; i < requestPermissions.length; i++) {

            String requestPermission = requestPermissions[i];





            //TODO checkSelfPermission

            int checkSelfPermission = -1;

            try {

                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);

            } catch (RuntimeException e) {

                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)

                        .show();

                Log.e(TAG, "RuntimeException:" + e.getMessage());

                return null;

            }



            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);



                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {

                    Log.d(TAG, "shouldShowRequestPermissionRationale if");

                    if (isShouldRationale) {

                        permissions.add(requestPermission);

                    }



                } else {



                    if (!isShouldRationale) {

                        permissions.add(requestPermission);

                    }

                    Log.d(TAG, "shouldShowRequestPermissionRationale else");

                }



            }

        }



        return permissions;

    }



}




