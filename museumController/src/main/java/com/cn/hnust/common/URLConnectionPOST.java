package com.cn.hnust.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class URLConnectionPOST {
	
    /**
     * 使用HttpURLConnection post 
     * @param httpUrl
     * @param params
     * @return
     */
	public static String httpUrlConnectionPost(String httpUrl, Map<String, String> params) {  
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
                urlConn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                urlConn.setRequestProperty("key", Constant.APPKEY);
                urlConn.setDoInput(true);  
                urlConn.setDoOutput(true);  
                urlConn.setConnectTimeout(5 * 1000);  
                //设置请求方式为 POST  
                urlConn.setRequestMethod("POST");
               
                  
//                urlConn.setRequestProperty("Content-Type", "application/json");  
//                urlConn.setRequestProperty("Accept", "application/json");  
//                  
//                urlConn.setRequestProperty("Charset", "UTF-8");  
                  
      
                DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());  
                //写入请求参数  
                //这里要注意的是，在构造JSON字符串的时候，实践证明，最好不要使用单引号，而是用“\”进行转义，否则会报错  
                 // 关于这一点在上面给出的参考文章里面有说明  
//                String jsonParam = "{\"appid\":6,\"appkey\":\"0cf0vGD/ClIrVmvVT/r5hEutH5M=\",\"openid\":200}"; 
              
                // 构建请求参数  
                StringBuffer sbParams = new StringBuffer();  
                
                if (params != null && params.size() > 0) {  
                    for (Entry<String, String> e : params.entrySet()) {  
                        sbParams.append("\"" + e.getKey() + "\"");  
                        sbParams.append(":");
        				sbParams.append("\"" + e.getValue() + "\""); 
//        				sbParams.append("\"" + URLEncoder.encode(e.getValue(),"UTF-8") + "\"");
                        sbParams.append(",");  
                    }  
                } 
                String strParams = "{" + sbParams.substring(0, sbParams.length() - 1) + "}";
                
//                dos.writeBytes(strParams);  
                dos.write(strParams.getBytes("UTF-8"));
                dos.flush();  
                dos.close();  
                  
                if (urlConn.getResponseCode() == 200) {  
                    InputStreamReader isr = new InputStreamReader(urlConn.getInputStream(), "UTF-8"); 
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
