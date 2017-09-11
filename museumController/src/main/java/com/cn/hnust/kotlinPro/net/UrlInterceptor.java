package com.cn.hnust.kotlinPro.net;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;

import com.cn.hnust.common.Constant;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class UrlInterceptor implements Interceptor {
	
	private String url;
	
	public UrlInterceptor(@NotNull String url) {
		// TODO Auto-generated constructor stub
		this.url=url;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		// TODO Auto-generated method stub
		Request request = chain.request();
		request.method();
		System.out.println(request.method());
		Builder re=new Request.Builder();
		if(url!=null && !url.isEmpty()){
			re.url(url);	
		}else{
			throw new IllegalArgumentException("url isEmpty");
		}
		re.header("key", Constant.APPKEY);
		if (request.method().equals("POST")) {
			re.post(request.body());
		}
		request=re.build();
		return chain.proceed(request);
	}

}
