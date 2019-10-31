package com.fh.shop.api.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String Y_M_D="yyyy-MM-dd";
    public static final String Y_M_D_H_S="yyyy-MM-dd HH:mm";
    public static final String FULL_YEAR="yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS="yyyyMMddHHmmss";

    public  static String date2str(Date date,String param){
        if(date==null){
            return "";
        }
        SimpleDateFormat sim=new SimpleDateFormat(param);
        String format = sim.format(date);
        return format;
    }

    public static Date str2date(String date,String param){
        if(StringUtils.isEmpty(date)){
            return null;
        }
        SimpleDateFormat sim=new SimpleDateFormat(param);
        Date datel=null;
        try {
             datel = sim.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datel;
    }

}
