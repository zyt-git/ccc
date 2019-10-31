package com.fh.shop.api.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    public static String getHttpClient(String url, Map<String,String> header,Map<String,String> params){
        CloseableHttpClient httpClient =null;
        HttpPost httpPost=null;
        CloseableHttpResponse execute =null;
        String result ="";
        try {
             httpClient = HttpClientBuilder.create().build();
             httpPost=new HttpPost(url);
            //设置头信息
            if(header!=null && header.size()>0){
                Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> next = iterator.next();
                    String key = next.getKey();
                    String value = next.getValue();
                    httpPost.addHeader(key,value);
                }
            }
            if(params!=null && params.size()>0){
                List<BasicNameValuePair> pairList=new ArrayList<>();
                Iterator<Map.Entry<String, String>> paramIter = params.entrySet().iterator();
                while (paramIter.hasNext()){
                    Map.Entry<String, String> next = paramIter.next();
                    String key = next.getKey();
                    String value = next.getValue();
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    pairList.add(basicNameValuePair);
                }
                //设置请求参数   转码
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "utf-8");
                httpPost.setEntity(urlEncodedFormEntity);

            }
            //执行请求
            execute = httpClient.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            result = EntityUtils.toString(entity,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(execute!=null){
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpPost!=null){
                httpPost.releaseConnection();
            }
            if(httpClient!=null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        return result;
    }
}
