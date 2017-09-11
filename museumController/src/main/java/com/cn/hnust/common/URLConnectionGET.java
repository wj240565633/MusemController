package com.cn.hnust.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class URLConnectionGET {
	
	 /** 
     * @Description:使用URLConnection发送get请求 
     * @author:liuyc 
	 * @throws UnsupportedEncodingException 
     * @time:2016年5月17日 下午3:27:58 
     */  
    @SuppressWarnings("deprecation")
	public static String sendGet2(String urlParam, List<String> params, String charset) throws UnsupportedEncodingException {  
        StringBuffer resultBuffer = null;  
        // 构建请求参数  
        StringBuffer sbParams = new StringBuffer();  
        for (String val : params) {
			sbParams.append(URLEncoder.encode(val,"utf-8") + "/");
//        	sbParams.append(val + "/");
		}
        BufferedReader br = null;  
        try {  
            URL url = null;  
            if (sbParams != null && sbParams.length() > 0) {  
                url = new URL(urlParam + "/" + sbParams.substring(0, sbParams.length() - 1));  
//                url = new URL(urlParam + "/" + "0" + "/" + "1");
            } else {  
                url = new URL(urlParam);  
            }  
//            String urlstr=new String(url.toString().getBytes(),"utf-8");
//            url=new URL(urlstr);
            
            
            HttpURLConnection con = (HttpURLConnection) url.openConnection();  
            // 设置请求属性  
            con.setRequestProperty("accept", "*/*");  
            con.setRequestProperty("connection", "Keep-Alive");  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");  
//            con.setRequestProperty("Content-Language", "zh-CN"); 
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 设置 HttpURLConnection的字符编码
            con.setRequestProperty("Accept-Charset", "UTF-8");
            // 建立连接  
            con.connect();  
            resultBuffer = new StringBuffer();  
            InputStream aa = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(aa, charset));  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                resultBuffer.append(temp);  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
            if (br != null) {  
                try {  
                    br.close();  
                } catch (IOException e) {  
                    br = null;  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return resultBuffer.toString();  
    }  

}
