package com.cn.hnust.kotlinPro.net;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;


public class NetUtil {
	
	private static NetUtil INSTANCE;
	
	private static final long DEFAULT_TIMEOUT=5;
	
	private Sevice apiService;
	
	private NetUtil(){}
	
	public static NetUtil init(){
		if(INSTANCE==null){
			synchronized (NetUtil.class) {
				if(INSTANCE==null){
					INSTANCE=new NetUtil();
				}
			}
		}
		return INSTANCE;
	}
	
	public NetUtil config(String url){
		OkHttpClient ok=new OkHttpClient.Builder()
			.addInterceptor(new UrlInterceptor(url))	
			.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
			.build();
		apiService=new Retrofit.Builder()
				.client(ok)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl("http://123.123.123:8080")
				.build().create(Sevice.class);
		
		return this;
	}
	
	/**
	 * GET请求普通数据
	 * @param api
	 * @param subscriber
	 */
	public  void  get( Subscriber<JSONObject> subscriber){
		apiService.executeGet("").subscribe(subscriber);
    }
	

    /**
     * post请求数据
     * @param api
     * @param body
     * @param subscriber
     */
    public  void post(String api,JSONObject body,Subscriber<JSONObject> subscriber){
        apiService.executePost(api,body).subscribe(subscriber);
    }

    /**
     * post请求带heade的数据
     * @param api
     * @param body
     * @param header
     * @param subscriber
     */
    public  void post(JSONObject body,Subscriber<JSONObject> subscriber){
        apiService.executePost("",body).subscribe(subscriber);
    }

    /**
     * post提交表单
     * @param api
     * @param form
     * @param subscriber
     */
    public  void psot(String api,Map<String,String> form,Subscriber<JSONObject> subscriber){
        apiService.executePost(api,form).subscribe(subscriber);
    }
    
    /**
     * put请求提交数据
     * @param api
     * @param body
     * @param header
     * @param subscriber
     */
    public void put(JSONObject body,Subscriber<JSONObject> subscriber){
    		apiService.executePut("", body).subscribe(subscriber);
    }

}
