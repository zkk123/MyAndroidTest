package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;





public class TimeUtil {

    public static String formatTime(long l){
    	SimpleDateFormat formatter = new SimpleDateFormat("HH.mm.ss");
		return formatter.format(new Date(l));
    }
    public static String formatTime(){
    	SimpleDateFormat formatter = new SimpleDateFormat("HH.mm.ss");
		return formatter.format(new Date(System.currentTimeMillis()));
    }
    public static String formatTime(String str){
    	SimpleDateFormat formatter = new SimpleDateFormat("HH.mm.ss");
		return formatter.format(new Long(str));
    }
    /**
     *
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        long ms = mss % 1000;
        if(days==0){
            if(hours==0){
                if(minutes==0){
                    if(seconds==0){
                        return ms+" ms";
                    }else{
                        return seconds+" s "+ms+" ms";
                    }
                }else{
                    return minutes+" m "+seconds+" s "+ms+" ms";
                }
            }else{
                return hours+" h "+minutes+" m "+seconds+" s "+ms+" ms";
            }
        }else{
            return days+" d "+hours+" h "+minutes+" m "+seconds+" s "+ms+" ms";
        }

    }
    /**
     *
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return  输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }

}
