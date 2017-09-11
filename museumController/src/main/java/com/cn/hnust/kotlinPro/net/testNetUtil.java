package com.cn.hnust.kotlinPro.net;

import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cn.hnust.common.ToolClass;
import com.cn.hnust.kotlinPro.bean.PicAndTextBean;

public class testNetUtil {
	
	public static void main(String[] args) {
		
		//System.err.println(URLDecoder.decode("userId=44&phone=123&bankCode=6161651615616&bankName=%E9%95%BF%E5%AE%89%E9%93%B6%E8%A1%8C&name=%E6%9E%97%E8%B1%AA&total=0.0000&total1=0.0000&total2=0&total3=0"));
			
//		final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(200);
//		
//		new Thread(){
//			public void run() {
//				for(; ;){
//					try {
//						newFixedThreadPool.execute(new Runnable() {
//							
//							@Override
//							public void run() {
//								String token =ToolClass.getText("http://192.168.0.119:8080/museumController/getAccess_token");
//								System.out.println("Thread:"+Thread.currentThread().getName()+"获取的token："+token);
//								
//							}
//						});
//						sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			};
//		}.start();
		
		String content = "西安博物院 100004";
		int a = parseText(content);
		String[] split = new String [2];
		if(a == 1){
			
			
			
			if(content.split("\\.").length ==2){
				split = content.split("\\.");
			}else if(content.split("\\s+").length ==2){
				split = content.split("\\s+");
			}else if(content.split("\\d+$").length ==1){
				split[0] = content.split("\\d+$")[0];
				split[1] = content.replace(split[0], "");
			}
		}
		
		for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}
	}
	
	
	private static int parseText(String content) {
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D]+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.matches()) {
			return 0;
		}
		Pattern pattern2=Pattern.compile("^[0-9]*$");
		Pattern pattern3 = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D]+\\d+$");
		Matcher matcher2 = null;
		if(content.split("\\.").length == 2){
			matcher2=pattern2.matcher(content.split("\\.")[1]);
			if (matcher2!= null && matcher2.matches() ) {
				return 1;
				
			}
		}
		
		if(content.split("\\s+").length == 2){
			matcher2=pattern2.matcher(content.split("\\s+")[1]);
			if (matcher2!= null && matcher2.matches() && 
					pattern.matcher(content.split("\\s+")[0].trim()).matches()) {
				return 1;
				
			}
		}
		
		if(content.split("\\d+$").length == 1){
			matcher2=pattern3.matcher(content);
			if (matcher2!= null && matcher2.matches() ) {
				return 1;
				
			}
		}
		
		return 2;

}
	
	

}
