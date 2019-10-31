package com.fh.shop.api.util;

import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component

public class Timing {//@EnableScheduling
    @Autowired
    private IShopMapper shopMapper;

    //@Scheduled(cron="0 0 0 * * ?")   //每5分钟执行一次
    public void sendmail(){
        List<Shop> shopList = shopMapper.selectList(null);
        StringBuffer str=new StringBuffer();
        for (Shop shop : shopList) {
            if(shop.getStock()<=5){
                String shopname = shop.getShopname();
                BigDecimal price = shop.getPrice();
                Long stock = shop.getStock();
                str.append("商品的名字"+shopname+"</br>");
                str.append("商品价格"+price+"</br>");
                str.append("商品剩余库存"+stock+"</br>");
            }
        }
        String s = str.toString();
        EmailUtil.sendMail("532028476@qq.com","张亚婷",s);
        System.out.println("发送成功");
    }



}
