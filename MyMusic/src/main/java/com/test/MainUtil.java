package com.test;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;

import com.test.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 501000704 on 2017/12/1.
 */

public class MainUtil {









    public static class RamUtil{
        public static  StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        public static final  long  tcounts=statFs.getBlockCountLong();
        public static final long blackSize=statFs.getBlockSizeLong();
        public static final String sd_path=Environment.getExternalStorageDirectory().getPath();
        public static long currentTime=0;
        public static String getTcountsSize(){

            return ((int)((tcounts*blackSize*1.0/1024/1024/1024)*1000))*0.001+"GB";
        }
        public static String getAvailableSize(){
            return ((int)(new StatFs(Environment.getDataDirectory().getPath()).getAvailableBlocksLong()*blackSize*1.0/1024/1024/1024*1000))*0.001+"GB";
        }
        public static String getPercentageAvaialableRAM(){
            double pre=new StatFs(Environment.getDataDirectory().getPath()).getAvailableBlocksLong()*1.0/tcounts;
            int pre2=(int)pre*100;
            return pre2+"%";
        }
        public static int getUserSizeByInt(){

            return (int)((tcounts-new StatFs(Environment.getDataDirectory().getPath()).getAvailableBlocksLong())/(tcounts/100));
        }
        public static void deleteWritedData(){
            File  file=new File(sd_path+"/ram_test/test.ram");
            file.delete();
        }
        public static String getCurrentTime(){
            return TimeUtil.formatDuring(System.currentTimeMillis()-currentTime);
        }
        public static void writeRam(int size){
            FileOutputStream out=null;
            File file=new File(sd_path+"/ram_test");
            if(file.exists()){
                file=new File(sd_path+"/ram_test/test.ram");
            }else{
                Log.e("zkk",file.mkdir()+":"+sd_path);
                file=new File(sd_path+"/ram_test/test.ram");
            }
            try {
                out=new FileOutputStream(file,true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte [] bytes=new byte[1024*1024];
            try {
                for(int i=0;i<size;i++) {
                    out.write(bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static abstract class UpdateViewUtil{
        private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {

                handleMessage1(msg);
            }
        };
        private Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                executeBody(handler);
            }
        });
        public abstract void executeBody(Handler handler);
        public abstract void handleMessage1(Message msg);
        public void start(){
            thread.start();
        }
        public boolean isAlive(){
            return thread.isAlive();
        }
        public void stop(){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
