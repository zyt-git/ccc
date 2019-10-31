package com.fh.shop.api.util;

import java.math.BigDecimal;

public class BigDecimailUtil {

    public static BigDecimal mul(String b1,String b2){
        BigDecimal big1=new BigDecimal(b1);
        BigDecimal big2=new BigDecimal(b2);
        return big1.multiply(big2).setScale(2);
    }

    public static BigDecimal add(String b1,String b2){
        BigDecimal big1=new BigDecimal(b1);
        BigDecimal big2=new BigDecimal(b2);
        return big1.add(big2).setScale(2);
    }
}
