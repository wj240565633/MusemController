package com.cn.hnust.kotlinPro.net;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import rx.Subscriber;

public class DataUtil {
	
	private static DataUtil INSTANCE;
	
	private String mUrl;
	
	private DataUtil(){}
	
	public static DataUtil init(){
		if(INSTANCE==null){
			synchronized (DataUtil.class) {
				if(INSTANCE==null){
					INSTANCE=new DataUtil();
				}
			}
		}
		return INSTANCE;
	}
	
	public DataUtil addUrl(@NotNull String url){
		this.mUrl=url;
		return this;
	}
	
	
	
	
	
	
	public String get(){
		 final StringBuilder str=new StringBuilder();
		NetUtil.init().config(mUrl).get(new Subscriber<JSONObject>(){

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				System.out.println("访问完成");
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				System.out.println(e);
			}

			@Override
			public void onNext(JSONObject t) {
				// TODO Auto-generated method stub
				System.out.println("dataUtil获取到的原生数据："+t);
				if (t.containsKey("data")) {
					str.append(t.toString());
				}else {
					throw new RuntimeException("服务器挂了");
				}
				
				
			}
			
		});
		
		if(str.toString().length()>0){
			return str.toString();
		}else{
			throw new NullPointerException("获取数据失败");
		}
		
	}
	
	
public String post(JSONObject body){
		
		final StringBuilder str=new StringBuilder();
		NetUtil.init().config(mUrl).post(body,new Subscriber<JSONObject>(){

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNext(JSONObject t) {
				// TODO Auto-generated method stub
				if (t.containsKey("data")) {
					str.append(t.toString());
				}else {
					throw new RuntimeException("服务器挂了");
				}
				
				
			}
			
		});
		
		if(str.toString().length()>0){
			return str.toString();
		}else{
			throw new NullPointerException("获取数据失败");
		}
		
	}
	
	
	public String put(JSONObject body){
		final StringBuilder str=new StringBuilder();
		NetUtil.init().config(mUrl).put(body,new Subscriber<JSONObject>(){

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				System.out.println("获取数据成功");
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				System.out.println(e);
			}

			@Override
			public void onNext(JSONObject t) {
				// TODO Auto-generated method stub
				System.out.println(t);
				if (t.containsKey("data")) {
					str.append(t.toString());
				}else {
					throw new RuntimeException("服务器挂了");
				}
				
				
			}
			
		});
		
		if(str.toString().length()>0){
			return str.toString();
		}else{
			throw new NullPointerException("获取数据失败");
		}
	}

}
