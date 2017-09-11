package com.cn.hnust.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class URLConnectionPUT {
	
	public static String httpUrlConnectionPut(String httpUrl, List<String> list) {  
        String result = "";  
        URL url = null;  
        try {  
            url = new URL(httpUrl);  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        }  
        if (url != null) {  
            try {  
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();  
                urlConn.setRequestProperty("content-type", "application/json");  
                urlConn.setDoInput(true);  
                urlConn.setDoOutput(true);  
                urlConn.setConnectTimeout(5 * 1000);  
                //设置请求方式为 PUT  
                urlConn.setRequestMethod("PUT");  
                  
                urlConn.setRequestProperty("Content-Type", "application/json");  
                urlConn.setRequestProperty("Accept", "application/json");  
                 urlConn.setRequestProperty("key", Constant.APPKEY);
                urlConn.setRequestProperty("Charset", "UTF-8");  
                  
      
                DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());  
                //写入请求参数  
                //这里要注意的是，在构造JSON字符串的时候，实践证明，最好不要使用单引号，而是用“\”进行转义，否则会报错  
                 // 关于这一点在上面给出的参考文章里面有说明  
//                String jsonParam = "{\"appid\":6,\"appkey\":\"0cf0vGD/ClIrVmvVT/r5hEutH5M=\",\"openid\":200}"; 
                StringBuffer strbf = new StringBuffer();
                strbf.append("{\"historicals\":[");
                for (int i = 0; i < list.size(); i++) {
					strbf.append("{\"historicalRelicId\":\"" + list.get(i) + "\"},");
				}
                String a = strbf.substring(0, strbf.length() - 1);
                String jsonParam = a + "]}";
                
                dos.writeBytes(jsonParam);  
                dos.flush();  
                dos.close();  
                  
                if (urlConn.getResponseCode() == 200) {  
                    InputStreamReader isr = new InputStreamReader(urlConn.getInputStream(), "utf-8"); 
                    BufferedReader br = new BufferedReader(isr);  
                    String inputLine = null;  
                    while ((inputLine = br.readLine()) != null) {  
                        result += inputLine;  
                    }  
                    isr.close();  
                    urlConn.disconnect();  
                }  
  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
  
        return result;  
  
    }
}
