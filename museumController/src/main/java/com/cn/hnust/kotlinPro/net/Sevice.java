package com.cn.hnust.kotlinPro.net;



import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface Sevice {
	
	/**
     * get请求
     */

    @GET("{url}")
    Observable<JSONObject> executeGet(@Path("url") String url);

    @GET("{url}")
    Observable<JSONObject> executeGet(@Path("url") String url,
                   @HeaderMap Map<String, String> head);

    @GET("{url}")
    Observable<JSONObject> executeGet(@Path("url") String url,
                   @HeaderMap  Map<String, String> head,
                   @QueryMap Map<String,String> map);

    /**
     * post请求
     */
    @POST("{url}")
    Observable<JSONObject> executePost(@Path("url")String url,
                    @Body JSONObject body);

    @POST("{url}")
    Observable<JSONObject> executePost(@Path("url")String url,
                    @Body JSONObject body,
                    @HeaderMap Map<String, String> head);

    @POST("{url}")
    Observable<JSONObject> executePost(@Path("url") String url,
    							@FieldMap Map<String, String> form);

    /**
     * put请求
     */
    @PUT("{url}")
    Observable<JSONObject> executePut(@Path("url")String url,
                   @Body JSONObject body);

}
