package com.cn.hnust.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.text.StrBuilder;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.cn.hnust.common.AlipayCore;
import com.cn.hnust.common.CommonUtil;
import com.cn.hnust.common.ConfigUtil;
import com.cn.hnust.common.Constant;
import com.cn.hnust.common.InfilterHTMLTag;
import com.cn.hnust.common.MD5Util;
import com.cn.hnust.common.PayCommonUtil;
import com.cn.hnust.common.SignUtil;
import com.cn.hnust.common.ToolClass;
import com.cn.hnust.common.URLConnectionPOST;
import com.cn.hnust.common.URLConnectionPUT;
import com.cn.hnust.common.XMLUtil;
import com.cn.hnust.kotlinPro.bean.BatchDataFetch;
import com.cn.hnust.kotlinPro.bean.InQueryHistoricalRelicCornetBen;
import com.cn.hnust.kotlinPro.bean.InQueryHistoricalRelicCornetBen.DataBean.RelicListBean;
import com.cn.hnust.kotlinPro.bean.PurchaseRecordBean;
import com.cn.hnust.kotlinPro.bean.UserFeedBackBean;
import com.cn.hnust.kotlinPro.bean.UserInComeTypeBean;
import com.cn.hnust.kotlinPro.net.DataUtil;
import com.cn.hnust.model.MuseumListModel;
import com.cn.hnust.model.SearchModel;
import com.cn.hnust.pojo.AccessTokenBean;
import com.cn.hnust.pojo.AlipayDefrayBean;
import com.cn.hnust.pojo.BatchBean;
import com.cn.hnust.pojo.BatchListenInBean;
import com.cn.hnust.pojo.DataBean;
import com.cn.hnust.pojo.FruitBean;
import com.cn.hnust.pojo.HistoricalBean;
import com.cn.hnust.pojo.HomePageBean;
import com.cn.hnust.pojo.ListeningBean;
import com.cn.hnust.pojo.MuseumBean;
import com.cn.hnust.pojo.MyIntroduce;
import com.cn.hnust.pojo.UserBean;
import com.cn.hnust.pojo.languageBean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@Controller
public class controller {
   private static Logger logger = Logger.getLogger(controller.class);
   
   private static ObjectMapper objectMapper = null;
   
   
   
 //-------------------------------------------------------------------
   //微信接口
   /**
    * 接入js-sdk所需的配置信息
    * jsConfig
    */
   @RequestMapping(value="/jsConfig",method=RequestMethod.GET)
   public JSONObject configJSSDK(@RequestHeader String url){  
	   String jsAPi_ticket =ToolClass.getJsApiTicket();
	 //获取Unix时间戳(java时间戳为13位,所以要截取最后3位,保留前10位)
       String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
       //签名字符串
       String str="jsapi_ticket="+jsAPi_ticket+
    		   "&noncestr="+Constant.nonceStr+
    		   "&timestamp="+timeStamp+"&url="+url;
       JSONObject json=new JSONObject();
       json.put("appid",Constant.APPID);
       json.put("timeStamp",timeStamp);
       json.put("nonceStr",Constant.nonceStr);
       json.put("signature",ToolClass.SHA1(str));
       json.put("jsapi_ticket",jsAPi_ticket);
       return json;
	}
       

   
   /**
    * 微信授权,拉起授权页面url
    * wechatCode
    */
   @RequestMapping(value="/wechatCode")
   public String wechatCode(HttpServletRequest request) {
	   String urls = null;
	   
   	try {
   		String parameter = request.getParameter("state");
   		urls = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
   		              +Constant.APPID
   		              +"&redirect_uri="
   		              +URLEncoder.encode(Constant.REDIRECT_URI, "utf-8")
   		              +"&response_type="
   		              +Constant.RESPONSE_TYPE
   		              +"&scope="
   		              +Constant.SCOPE
   		              +"&state="
   		              +parameter
   		              +Constant.WECHAT_REDIRECT;
   		//正常
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
		}
   	return "redirect:"+urls;
   }
   
   
  
   /**
    * 微信授权，验证
    * @param request
    * @param response
    * @return
    * @throws ServletException
    * @throws IOException
    */
   @RequestMapping(value="/haoge",method={RequestMethod.GET,RequestMethod.POST})
   public String museumLanguage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
	   String url="";
	   
	   try{
		   
		   if (request.getParameter("echostr") != null) {
				// 微信加密签名  
			       String signature = request.getParameter("signature");  
			       // 时间戳  
			       String timestamp = request.getParameter("timestamp");  
			       // 随机数  
			       String nonce = request.getParameter("nonce");  
			       // 随机字符串  
			       String echostr = request.getParameter("echostr");  
			       
			       PrintWriter out = response.getWriter();  
			       // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
			       if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
			           out.print(echostr);  
			       }  
			       out.close();  
			       out = null;  
			}
		 //判断当前服务请求方式
		   boolean isPost = request.getMethod().toLowerCase().equals("post");
		   if (isPost) {
			   //如果当前请求方式是post方式说明有用户向公众号发送消息，则去解析当前消息进行处理
			   ToolClass.praseMsg(request,response);
		   }
		   if (request.getParameter("code") != null) {
			   String parameter = request.getParameter("state");
			   String decodeBase64 = ToolClass.decodeBase64(parameter);
			   
			   String tokenurl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" 
					             + Constant.APPID + "&secret=" + Constant.appsecret + "&code=" + request.getParameter("code") 
					             + "&grant_type=authorization_code";
			   //token
			   String str = ToolClass.getWeixin(tokenurl);
			   AccessTokenBean accessTokenBean = new Gson().fromJson(str, AccessTokenBean.class);
			   
			   String userurl = "https://api.weixin.qq.com/sns/userinfo?access_token=" 
					             + accessTokenBean.getAccess_token() + "&openid=" 
					             + accessTokenBean.getOpenid() + "&lang=zh_CN";
			   //用户信息
			   String str1 = ToolClass.getWeixin(userurl);
			   
			   UserBean userBean = new Gson().fromJson(str1, UserBean.class);
			   Map<String, String> params = new HashMap<String, String>();
			   params.put("openid", userBean.getOpenid());
			   params.put("nickname", userBean.getNickname());
			   params.put("sex", userBean.getSex());
			   params.put("state", userBean.getCountry());
			   params.put("province", userBean.getProvince());
			   params.put("city", userBean.getCity());
			   params.put("headImageUrl", userBean.getHeadimgurl());
			   if((decodeBase64.contains(",") && decodeBase64.split(",")[0].equals("xianshang"))){
				   params.put("refereeId", decodeBase64.split(",")[1]);
			   }else{
				   params.put("refereeId", ""); 
			   }
			   
			   //设置当前平台域名 
			   params.put("clientAddress", Constant.CLIENT_ADDRESS); 
			   //当获取用户在微信的唯一标识为空，返回错误页面
			   if(userBean.getOpenid() == null || userBean.getOpenid().trim().length() ==0){
				   return "redirect:"+Constant.CurrentURL+"error";
			   }
			   
			   
			   String strr = URLConnectionPOST.httpUrlConnectionPost(Constant.URL + "userInfoSubmitTo", params);
			   JSONObject json =JSONObject.parseObject(strr);
			   if(json.containsKey("code") && json.getString("code").equals("218")){
				   return "redirect:"+Constant.CurrentURL+"error?msg=unpass"; 
			   }
			   
			   //判断用户是否已经关注了公众号，如果没有关注公众号就让用户先去关注公众号
			   if(ToolClass.isSubscribe(userBean.getOpenid()) == 0){
				   //用户没有关注,进入关注界面
				   url = Constant.SUBSCRIBE;
			   }else if(ToolClass.isSubscribe(userBean.getOpenid()) == 1){
				  
				   
				   DataBean dataBean = new Gson().fromJson(strr, DataBean.class);  
				   if (decodeBase64.equals("team")) {
					   //返回我的团队界面 
					   url="redirect:"+Constant.CurrentURL+"goCenter/"+dataBean.getData();
				   }else if ((decodeBase64.contains(",") && decodeBase64.split(",")[0].equals("xianshang"))
						   ||decodeBase64.equals("xianshang")) {
					   
						//返回线上的主页（用户已经关注）
						url="redirect:"+Constant.CurrentURL+"post_data/"+dataBean.getData()+"?openid="+userBean.getOpenid();
					  
					  
				   }else if (decodeBase64.equals("indent")) {
					   //返回我的订单界面
					   url="redirect:"+Constant.CurrentURL+"purchaseRecord/"+dataBean.getData();
				   }else if (decodeBase64.equals("tucao")) {
					   //返回我要吐槽界面
					   url="redirect:"+Constant.CurrentURL+"getUserFeedBack/"+dataBean.getData();
				   }else if (decodeBase64.equals("qrcode")) {
					   //返回我的二维码界面
					   url="redirect:"+Constant.CurrentURL+"getQRCode/"+dataBean.getData();
				   }else  {
					   //判断当前是否是扫描二维码获取到的结果
					   String[] split = decodeBase64.split(",");
					   if (split[0].equals("scan")) {
						   //是扫描二维码返回文物详情界面
						   ///twoDimensionCode/{museumId}/{historicalRelicId}/{userId}/{openid}
						   url="redirect:"+Constant.CurrentURL+"twoDimensionCode?museumId="+split[1]+"&historicalRelicId="+split[2]+"&userId="+dataBean.getData()+"&openid="+userBean.getOpenid()+"&shareId="+split[3];
						   
					   }else {
						url="redirect:"+Constant.CurrentURL+"error";
					   }
				   } 
			   }else{
				   
				   //（获取用户关注时异常）
				   url="redirect:"+Constant.CurrentURL+"error"; 
			   }
			   
			   
			   
			   
			}  
		   
	   }catch (Exception e) {
		   url="redirect:"+Constant.CurrentURL+"error";
	   }
	   
	   
       return url;
   }
   
   /**
    * 错误界面
    * @return
    */
   @RequestMapping("/error")
   public ModelAndView goError(HttpServletRequest request) {
	   ModelAndView modelAndView =new ModelAndView();
	   if(request.getParameter("msg")!=null && request.getParameter("msg").equals("unpass")){
		   modelAndView.addObject("msg", "当前用户被禁用！");
	   }
	   modelAndView.setViewName("showUser");
	return modelAndView;
}
   
   
   /**
    * 无用的测试口
    * @param body
    */
   @RequestMapping(value="/userInfo",method=RequestMethod.POST)
   public @ResponseBody void aaa(@RequestBody String body) {
	   
	   objectMapper = new ObjectMapper();
		 UserBean userBean = new Gson().fromJson(body, UserBean.class);
		   Map<String, String> params = new HashMap<String, String>();
		   params.put("openid", userBean.getOpenid());
		   params.put("nickname", userBean.getNickname());
		   params.put("sex", userBean.getSex());
		   params.put("state", userBean.getCountry());
		   params.put("province", userBean.getProvince());
		   params.put("city", userBean.getCity());
		   params.put("headImageUrl", userBean.getHeadimgurl());
		   String strr = URLConnectionPOST.httpUrlConnectionPost(Constant.URL + "userInfoSubmitTo", params);
		   System.out.println("-----------------------------");
		   System.out.println(strr);
   }
   
 //------------------------------------------------------------------------
   //博物馆接口
   
   /**
    * 无用的接口
    * museumEntrance
    */
   
   @RequestMapping(value="/index",method=RequestMethod.GET)
   public @ResponseBody ModelAndView login(HttpServletRequest request) { 	
   	ModelAndView modelAndView = new ModelAndView();
   	
	modelAndView.setViewName("login");
   	
   	return modelAndView;
   }
   
   

   
   
   /**
    * 博物馆首页
    * museumIndex
    */
   
   @RequestMapping(value="/post_data/{userId}",method=RequestMethod.GET)
   public @ResponseBody ModelAndView index(@PathVariable String userId , HttpServletRequest request) { 	
   	ModelAndView modelAndView = new ModelAndView();
   	modelAndView.addObject("userId", userId);   	
   	modelAndView.addObject("openid", request.getParameter("openid"));
   	modelAndView.setViewName("index");
   	return modelAndView;
   }
   
   

    /**
     * 博物馆首页加载语种
     * museumLangage
     */
    @RequestMapping(value="/museumLangage",method=RequestMethod.GET)
    public @ResponseBody JSONArray museumLanguage() { 	
    	String str=DataUtil.init().addUrl(Constant.URL+"museumLanguage").get();
		JSONObject jsonObject=JSONObject.parseObject(str);
		if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("100")) {
			return JSONArray.parseArray(jsonObject.getString("data"));
		}else {
			return new JSONArray();
		}
    }
    
    /**
     * 博物馆首页子页面加载数据
     * museumHomePage
     */
    @RequestMapping(value="/museumHomePage/{languageId}/{limit}/{offset}",method=RequestMethod.GET)
    public @ResponseBody ModelAndView museumHomePage(@PathVariable int languageId, @PathVariable int limit, @PathVariable int offset) {

    	ModelAndView modelView = null;
    	try {
    		modelView = new ModelAndView();
    		String str=DataUtil.init().addUrl(Constant.URL+"museumList/"+
    		languageId+"/"+limit+"/"+offset).get();
    		Gson gson=new Gson();
    		HomePageBean homePageBean = gson.fromJson(str, HomePageBean.class);
    		if ("100".equals(homePageBean.getCode())) {
    			MuseumListModel museumListModel = null;
    			List<MuseumListModel> museumList = new ArrayList<MuseumListModel>();
    			for (int i = 0; i < homePageBean.getData().size(); i++) {
					museumListModel = new MuseumListModel();
					museumListModel.setMuseumId(homePageBean.getData().get(i).getMuseumId());
					museumListModel.setMuseumName(homePageBean.getData().get(i).getMuseumName());
					museumListModel.setPictureAddress(homePageBean.getData().get(i).getPictureAddress());
					museumList.add(museumListModel);
				}
    			System.out.println(str);
				modelView.addObject("museumList", museumList);
				modelView.setViewName("app_context");
			} else {
				modelView.setViewName("showUser");
			}
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    }
    
    /**
     * 博物馆首页搜索
     * museumSearch
     */
    @RequestMapping(value="/museumSearch/{museumName}/{languageId}/{limit}/{offset}",method=RequestMethod.GET)
    public @ResponseBody ModelAndView museumSearch(@PathVariable String museumName, @PathVariable int languageId, @PathVariable int limit, @PathVariable int offset) {
    	System.out.println(museumName);
    	ModelAndView modelView = null;
    	try {
    		modelView = new ModelAndView();
    		Gson gson=new Gson();
    		String str=DataUtil.init().addUrl(Constant.URL+"museumSearch/"+
    	    		museumName+"/"+languageId+"/"+limit+"/"+offset).get();
    		MuseumBean museumBean = gson.fromJson(str, MuseumBean.class);
    		List<SearchModel> modelList = new ArrayList<SearchModel>();
    		SearchModel searchModel = null;
    		if ("100".equals(museumBean.getCode())) {
    			for (MuseumBean.SearchBean bean : museumBean.getData()) {
    				searchModel = new SearchModel();
    				
    				searchModel.setMuseumId(bean.getMuseumId());
    				searchModel.setMuseumName(bean.getMuseumName());
    				searchModel.setPictureAddress(bean.getPictureAddress());
    				searchModel.setStatus("YES");
    				modelList.add(searchModel);
				}
    			modelView.addObject("modelList", modelList);
			} else if ("201".equals(museumBean.getCode())) {
				searchModel = new SearchModel();
				searchModel.setStatus("NO");
				modelList.add(searchModel);
				modelView.addObject("modelList", modelList);
			} else {
				modelView.setViewName("showUser");
			}
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    }
    
    
    
    
    /**
     * 博物馆介绍
     * museumIntroduce
     */
    @RequestMapping(value="/museumIntroduce",method=RequestMethod.GET)
    public @ResponseBody ModelAndView museumIntroduce(HttpServletRequest request) {
    	
    	String museumId=request.getParameter("id");
    	String languageId=request.getParameter("languageId");
    	String languageName=request.getParameter("languageName");
    	System.out.println(languageName);
    	String userId=request.getParameter("userId");
    	ModelAndView modelView = null;
    	try {
    		modelView = new ModelAndView();
    		ToolClass.getMuseumIntroduceData(museumId, languageId, languageName, modelView);
    		modelView.addObject("userId", userId);
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    }
    
    /**
     * 博物馆文物列表
     * historicalRelicTabulation
     */
    @RequestMapping(value="/historicalRelicTabulation/{museumId}/{languageId}/{userId}/{limit}/{offset}",method=RequestMethod.GET)
    public @ResponseBody ModelAndView historicalRelicTabulation(HttpServletRequest request ,@PathVariable int museumId, @PathVariable int languageId, @PathVariable int userId, @PathVariable int limit, @PathVariable int offset) {
    	ModelAndView modelView = null;
    	try {
    		modelView = new ModelAndView();
    		String str=DataUtil.init().addUrl(Constant.URL+"historicalRelicTabulation/"+
    				museumId+"/"+languageId+"/"+userId+"/"+limit+"/"+offset).get();
    		HistoricalBean historicalBean = new Gson().fromJson(str, HistoricalBean.class);

    		 if ("100".equals(historicalBean.getCode())) {
				modelView.addObject("historicalList", historicalBean.getData());
				modelView.setViewName("app_detial_context");
				modelView.addObject("userId", userId);
			} else {
				modelView.setViewName("showUser");
			}
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    }
    
    /**
     * 文物搜索
     * heritageSearch
     */
    @RequestMapping(value="/heritageSearch/{museumId}/{search}/{languageId}/{userId}/{limit}/{offset}",method=RequestMethod.GET)
    public @ResponseBody ModelAndView heritageSearch(@PathVariable String museumId,@PathVariable String search, @PathVariable int languageId, @PathVariable int userId, @PathVariable int limit, @PathVariable int offset) {
    	
    	ModelAndView modelView = null;
    	try {
    		modelView = new ModelAndView();
    		String str=DataUtil.init().addUrl(Constant.URL+"heritageSearch/"+
    				museumId+"/"+search+"/"+languageId+"/"+userId+"/"+limit+"/"+offset).get();
    		HistoricalBean historicalBean = new Gson().fromJson(str, HistoricalBean.class);
    		if ("100".equals(historicalBean.getCode())) {
				modelView.addObject("searchList",historicalBean.getData());
				modelView.addObject("status", "YES");
				modelView.setViewName("app_detial_context");
			} else if ("203".equals(historicalBean.getCode())) {
				modelView.addObject("status", "NO");
				modelView.setViewName("app_detial_context");
			} else {
				modelView.setViewName("showUser");
			}
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    }
    
    /**
     * 单个文物收听
     * singleHeritageListening 
     */
    @RequestMapping(value="/singleHeritageListening/{museumId}/{historicalRelicId}/{userId}/{languageId}",method=RequestMethod.GET)
    public  ModelAndView singleHeritageListening(@PathVariable int museumId, 
    		@PathVariable int historicalRelicId, @PathVariable int userId, @PathVariable String languageId) {
    	
    	if(languageId.contains(".")){
    		languageId = languageId.split(".")[0];
    	}
    	ModelAndView modelView = null;
    		try {
	    		modelView = new ModelAndView();
	    		String str=DataUtil.init().addUrl(Constant.URL+"singleHeritageListening/"+
	    				museumId+"/"+historicalRelicId+"/"+userId+"/"+languageId).get();
	    		System.out.println("接口获取的数据："+str);
	    		JSONObject obj=JSONObject.parseObject(str);
	    		if(obj.getString("code").equals("204")){
	    				//免费收听次数用完，需要购买
					System.out.println("需要购买");
					modelView.addObject("code","204");
	    		}else if (obj.getString("code").equals("205") ||obj.getString("code").equals("206")) {
	    			ListeningBean listeningBean = new Gson().fromJson(str, ListeningBean.class);
		    		ListeningBean.DataBean dataBean=listeningBean.getData().get(0);
		    		StringBuilder sBuilder=new StringBuilder();
		    		if ("206".equals(listeningBean.getCode())) {
		    			//文物已购买，直接收听
		    			modelView.addObject("freeNum",-1);
		    			modelView.addObject("title",dataBean.getHistoricalRelicName());
		    			modelView.addObject("museumId", museumId);
		    			modelView.addObject("historicalRelicId", historicalRelicId);
		    			modelView.addObject("soundAddr",dataBean.getSoundAddress());
		    			modelView.addObject("content",dataBean.getDescribe());
		    			modelView.addObject("userId", userId);
		    			for (int i = 0; i < dataBean.getPictureAddressList().size(); i++) {
							sBuilder.append(dataBean.getPictureAddressList().get(i).getPictureAddress()+",");
					}
		    			modelView.addObject("picList",sBuilder.substring(0, sBuilder.length()-1).toString());
		    			modelView.setViewName("app_detial_context_item");
				} else if ("205".equals(listeningBean.getCode())) {
					//显示免费收听次数，在收听
					modelView.addObject("freeNum",dataBean.getFreeNumber());
		    			modelView.addObject("title",dataBean.getHistoricalRelicName());
		    			modelView.addObject("soundAddr",dataBean.getSoundAddress());
		    			modelView.addObject("content",dataBean.getDescribe());
		    			modelView.addObject("userId", userId);
		    			for (int i = 0; i < dataBean.getPictureAddressList().size(); i++) {
							sBuilder.append(dataBean.getPictureAddressList().get(i).getPictureAddress()+",");
					}
		    			modelView.addObject("picList",sBuilder.substring(0, sBuilder.length()-1).toString());
					modelView.setViewName("app_detial_context_item");
				}
	    			
			}else {
				modelView.setViewName("showUser");	
			}
			
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    		return modelView;
    }
    
    
    /**
     * 判断文物是否需要购买
     * @param museumId
     * @param historicalRelicId
     * @param userId
     * @param languageId
     * @return
     */
    @RequestMapping("/needPay/{museumId}/{historicalRelicId}/{userId}/{languageId}")
    @ResponseBody
    public String needPay(@PathVariable String museumId ,@PathVariable String historicalRelicId ,
    		@PathVariable String userId ,@PathVariable String languageId){
    	String str=DataUtil.init().addUrl(Constant.URL+"singleHeritageListeningType/"+
				museumId+"/"+historicalRelicId+"/"+userId+"/"+languageId).get();
    	JSONObject json =JSONObject.parseObject(str);
    	String code = json.getString("code");
    	if("206".equals(code) || "205".equals(code)){
    		return "100";
    	}else if("204".equals(code)){
    		return "204";
    	}else{
    		return "200";
    	}
    	
    }
    
    
    /**
     * 批量收听取数据
     * batchDataFetch
     */
    @RequestMapping(value="/batchDataFetch/{museumId}/{languageId}/{userId}",method=RequestMethod.GET)
    public ModelAndView batchDataFetch(@PathVariable int museumId,@PathVariable int languageId,@PathVariable int userId){
    	ModelAndView modelAndView=new ModelAndView();
    	try {
    		String string=DataUtil.init().addUrl(Constant.URL+"batchDataFetch/"+museumId+"/"+languageId+"/"+userId).get();
		Gson gson=new Gson();
		JSONObject jsonObject=JSONObject.parseObject(string);
		BatchDataFetch dataBean=gson.fromJson(string, BatchDataFetch.class);
			if (dataBean.getCode().equals("100")) {
				modelAndView.addObject("batchDataFetchList",dataBean.getData());
				modelAndView.addObject("json",jsonObject.getJSONArray("data"));
				modelAndView.setViewName("app_detial_context_pay");
			}else {
				modelAndView.setViewName("showUser");
			}
		} catch (Exception e) {
			modelAndView.setViewName("showUser");
		}
	    	
    	
    	return modelAndView;
    }
    
    /**
     * 批量收听搜索获取数据
     */
    @RequestMapping(value="/batchDataFetchSearch/{search}/{museumId}/{languageId}/{userId}",method=RequestMethod.GET)
    public ModelAndView batchDataFetchSearch(@PathVariable String search,@PathVariable int museumId,@PathVariable int languageId,@PathVariable int userId) {
		ModelAndView modelAndView=new ModelAndView();
		String str=DataUtil.init().addUrl(Constant.URL+"/batchDataFetchSearch"+"/"+search+"/"+museumId+"/"+languageId+"/"+userId).get();
		JSONObject jsonObject=JSONObject.parseObject(str);
		modelAndView.addObject("json",jsonObject.getJSONArray("data"));
    		
		return modelAndView;
	}
    
    /**
     * 单个文物收听购买
     * americanaListener
     */
    @RequestMapping(value="/singleListening/{languageId}",method=RequestMethod.PUT)
    public @ResponseBody ModelAndView singleListening(@PathVariable int languageId, @RequestBody String body) {
    	ModelAndView modelView = null;
    	String id=body.split("=")[1];
    	try {
    		JSONObject object=new JSONObject();
    		JSONArray array=new JSONArray();
    		JSONObject item=new JSONObject();
			item.put("historicalRelicId", id);
			array.add(item);
    		object.put("historicals", array);
    		modelView = new ModelAndView();
    		BatchBean batchBean = new BatchBean();
    		objectMapper = new ObjectMapper();
    		batchBean = objectMapper.readValue(object.toString(), BatchBean.class);
    		List<String> list = new ArrayList<String>();
    		batchBean.getHistoricals();
			for (BatchListenInBean bean : batchBean.getHistoricals()) {
				list.add(String.valueOf(bean.getHistoricalRelicId()));
			}
			String urls = Constant.URL + "batchListenIn/" + String.valueOf(languageId);
			String str = URLConnectionPUT.httpUrlConnectionPut(urls, list);
			System.out.println(str);
			FruitBean fruitBean = new Gson().fromJson(str, FruitBean.class);
			if ("100".equals(fruitBean.getCode())) {
				modelView.addObject("total", fruitBean.getData().get(0).getTotal());
				modelView.addObject("number", fruitBean.getData().get(0).getNumber());
				if (fruitBean.getData().get(0).getDiscountId()!=null &&
					fruitBean.getData().get(0).getSaletotal()!=null) {
					modelView.addObject("discountId",fruitBean.getData().get(0).getDiscountId());
					modelView.addObject("saletotal",fruitBean.getData().get(0).getSaletotal());
				}else{
					modelView.addObject("discountId","");
					modelView.addObject("saletotal","");
				}
				
				modelView.addObject("lng", fruitBean.getData().get(0).getLanguageType());
			} else {
				modelView.setViewName("showUser");
			}
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    	
    }
    
    
    
    
    /**
     * 批量收听
     * batchListenIn
     * 
     * 测试的时候再修改，目前还没有添加密钥
     */
    @RequestMapping(value="/batchListenIn/{languageId}",method=RequestMethod.PUT)
    public @ResponseBody ModelAndView singleHeritageListening(@PathVariable int languageId, @RequestBody String body) {
    	ModelAndView modelView = null;
    	String string=body.split("=")[1];
    	string=string.substring(0, string.length()-1);
    	String [] a=string.split("\\+");
    	String ids="";
    	try {
    		JSONObject object=new JSONObject();
    		JSONArray array=new JSONArray();
    		for (int i = 0; i < a.length; i++) {
    			JSONObject item=new JSONObject();
    			item.put("historicalRelicId", a[i]);
    			array.add(item);
    			ids+=a[i]+",";
			}
    		
    		object.put("historicals", array);
    		modelView = new ModelAndView();
    		BatchBean batchBean = new BatchBean();
    		objectMapper = new ObjectMapper();
    		batchBean = objectMapper.readValue(object.toString(), BatchBean.class);
    		List<String> list = new ArrayList<String>();
    		batchBean.getHistoricals();
			for (BatchListenInBean bean : batchBean.getHistoricals()) {
				list.add(String.valueOf(bean.getHistoricalRelicId()));
			}
			String urls = Constant.URL + "batchListenIn/" + String.valueOf(languageId);
			String str = URLConnectionPUT.httpUrlConnectionPut(urls, list);
			FruitBean fruitBean = new Gson().fromJson(str, FruitBean.class);
			if ("100".equals(fruitBean.getCode())) {
				modelView.addObject("total", fruitBean.getData().get(0).getTotal());
				modelView.addObject("number", fruitBean.getData().get(0).getNumber());
				modelView.addObject("ids", ids.substring(0, ids.length()-1));
				if (fruitBean.getData().get(0).getDiscountId()!=null &&
						fruitBean.getData().get(0).getDiscountId().length()>0 &&
						fruitBean.getData().get(0).getSaletotal()!=null &&
						fruitBean.getData().get(0).getSaletotal().length()>0) {
					modelView.addObject("discountId",fruitBean.getData().get(0).getDiscountId());
					modelView.addObject("saletotal",fruitBean.getData().get(0).getSaletotal());
				}else{
					modelView.addObject("discountId","-1");
					modelView.addObject("saletotal","-1");
				}
				modelView.addObject("lng",fruitBean.getData().get(0).getLanguageType());
				
			} else {
				modelView.setViewName("showUser");
			}
		} catch (Exception e) {
			//异常
			logger.info(e.getMessage());
			modelView.setViewName("showUser");
		}
    	return modelView;
    	
    }
    
    /**
     * 文物详细界面
     * @return  文物详细界面
     */
    @RequestMapping(value="/listenerContent",method=RequestMethod.GET)
    public ModelAndView listenerContent(){
    		return new ModelAndView("app_detial_context_item_context");
    }
    
    /**
     * 我要讲解接口
     * @param userId 用户id
     * @param email 邮箱
     * @return 返回 ModelAndView对象
     */
    @RequestMapping(value="/myExplain",method=RequestMethod.POST)
    public ModelAndView myExplain(@RequestBody String body) {
    	    ModelAndView modelAndView=new ModelAndView();
		try {
			try {
				body = URLDecoder.decode(body, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		
	    		String[] strings=body.split("\\&");
			JSONObject bodys=new JSONObject();
			bodys.put("userId", strings[0].split("=")[1]);
			bodys.put("mail", strings[1].split("=")[1]);
			String str=DataUtil.init().addUrl(Constant.URL+"myExplain").post(bodys);
			JSONObject jsonObject=JSONObject.parseObject(str);
			if (jsonObject.getString("code").equals("100")) {
				//提交成功
			modelAndView.addObject("code","提交成功");	
			}else if (jsonObject.getString("code").equals("101")) {
				//服务器出错
				modelAndView.setViewName("showUser");
			}else if (jsonObject.getString("code").equals("208")) {
				//提交失败
				modelAndView.addObject("code","提交成功");	
			}
		} catch (Exception e) {
					
				}
		return modelAndView;
	}
    
   
    
    /**
     * 用户点击界面返回的查询博物馆文物短号链接进入文物短号界面
     * @param museumName
     * @return
     */
    @RequestMapping(value="/queryHistoricalCornet/{museumName}",method=RequestMethod.GET)
    public ModelAndView queryHistoricalCornet(HttpServletRequest request,@PathVariable String museumName){
		Object[] objects=ToolClass.getQueryHistoricalCornetRes(museumName);
		String userId = request.getParameter("userId");
		String str=objects[1].toString();
		ModelAndView modelAndView=new ModelAndView();
		int code=(int) objects[0];
		if (code==100) {
			modelAndView.setViewName("cornet");
			InQueryHistoricalRelicCornetBen ben=new Gson().fromJson(str, InQueryHistoricalRelicCornetBen.class);
			InQueryHistoricalRelicCornetBen.DataBean.RelicListBean relicListBean=ben.getData().get(0).getRelicList().get(0);
			modelAndView.addObject("museumName",relicListBean.getMuseumName());
			modelAndView.addObject("cornetList",relicListBean.getCornetList());
			modelAndView.addObject("userId",userId);
			return modelAndView;
		}else {
			modelAndView.setViewName("showUser");
			return modelAndView;
		}
    }
    
    
    
    
    
    
    /**
     * 无用
     * 文物短号查询文物
     * @param cornette
     * @return 
     */
    @RequestMapping(value="/singleInquiryC/{cornette}",method=RequestMethod.GET)
    public ModelAndView singleInquiryCornnette(@PathVariable String cornette) {
    		String str=DataUtil.init().addUrl(Constant.URL+"singleInquiry/"+cornette+"/null").get();
    		System.out.println();
    		return null;
	}
    
    /**
     * 无用
     * 文物二维码url查询文物
     * @param twoDimensionUrl
     * @return
     */
    @RequestMapping(value="/singleInquiryT/{twoDimensionUrl}",method=RequestMethod.GET)
    public ModelAndView singleInquiryTwoDimensionUrl(@PathVariable String twoDimensionUrl) {
    	String str=DataUtil.init().addUrl(Constant.URL+"singleInquiry/0/"+twoDimensionUrl).get();
    		
    		return null;
	}
    
    
    /**
     * 输入博物馆名称和文物短号查询文物
     * @param twoDimensionUrl
     * @return
     */
    @RequestMapping(value="/nameAndCornet",method=RequestMethod.GET)
    public ModelAndView queryCornetWithMuseumNameAndCornet(HttpServletRequest request) {
		String museumName;
		ModelAndView modelAndView=new ModelAndView();
		try {
			museumName = URLDecoder.decode(request.getParameter("museumName"), "UTF-8");
			String cornette = request.getParameter("cornette");
			String userId = request.getParameter("userId");
	    	System.out.println(museumName);
			System.out.println(cornette);
	    	String str=DataUtil.init().addUrl(Constant.URL+"nameAndCornet/"+museumName+"/"+cornette).get();
			
			JSONObject object=JSONObject.parseObject(str);
			String code=object.getString("code");
			if (code.equals("100")) {
				ToolClass.getXXHistoricalInfo(modelAndView, object, museumName);
				modelAndView.addObject("userId", userId);
				System.out.println("准备获取openid");
				String string=DataUtil.init().addUrl(Constant.URL+"userIdentityAccount/"+userId).get();
	    		JSONObject jsonObject=JSONObject.parseObject(string);
	    		if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("100")) {
					modelAndView.addObject("openid",jsonObject.getString("data"));
				}else {
					modelAndView.setViewName("showUser");
				}
				
			}else {
				modelAndView.setViewName("showUser");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelAndView.setViewName("showUser");
		}
		return modelAndView;
	}
    

    /**
     * 扫一扫，二维码查询文物（授权登陆后的）
     */
    @RequestMapping("/twoDimensionCode")
    public ModelAndView twoDimensionCode(HttpServletRequest request) {
    	String museumId = request.getParameter("museumId");
    	String historicalRelicId = request.getParameter("historicalRelicId");
    	String userId = request.getParameter("userId");
    	String openid = request.getParameter("openid");
		ModelAndView modelAndView=new ModelAndView();
		String lgStr=DataUtil.init().addUrl(Constant.URL+"twoDimensionCode/"+museumId+"/"+historicalRelicId).get();
		JSONObject object=JSONObject.parseObject(lgStr);
		if (object.getString("code").equals("100")) {
			ToolClass.getXXHistoricalInfo(modelAndView, object, "");	
			modelAndView.addObject("userId", userId);
			modelAndView.addObject("openid",openid);
			if(request.getParameter("shareId") != null ){
				if(Integer.parseInt(request.getParameter("shareId"))>0){
					int a = Integer.parseInt(request.getParameter("shareId"));
					String str = ToolClass.getQRCode(""+a);
					if(str != null){
						JSONObject json = JSONArray.parseArray(str).getJSONObject(0);
						modelAndView.addObject("shareQRCode",json.getString("twoDimensionAddress") );
					}
					
				}else{
					modelAndView.addObject("shareQRCode", "");
				}
			}else{
				modelAndView.addObject("shareQRCode", "");
			}
		}else {
			modelAndView.setViewName("showUser");
		}
		return modelAndView;
	}
    
    
    /**
     * 扫一扫，二维码查询文物(授权前)
     */
    @RequestMapping("/xxQRCodeQueryHistorical/{museumId}/{historicalRelicId}")
    public ModelAndView xxQRCodeQueryHistorical(HttpServletRequest request, @PathVariable String museumId,@PathVariable String historicalRelicId){
    	ModelAndView modelAndView=new ModelAndView();
		String lgStr=DataUtil.init().addUrl(Constant.URL+"twoDimensionCode/"+museumId+"/"+historicalRelicId).get();
		JSONObject object=JSONObject.parseObject(lgStr);
		if (object.getString("code").equals("100")) {
			ToolClass.getXXHistoricalInfo(modelAndView, object, "");
			if(request.getParameter("shareId") != null ){
				if(Integer.parseInt(request.getParameter("shareId"))>0){
					int a = Integer.parseInt(request.getParameter("shareId"));
					String str = ToolClass.getQRCode(""+a);
					System.out.println(str);
					if(null !=str){
						JSONObject json = JSONArray.parseArray(str).getJSONObject(0);
						String url = json.getString("twoDimensionAddress");
						modelAndView.addObject("shareQRCode", url);
					
					}else{
						modelAndView.setViewName("showUser");
					}
					
					
				}else{
					modelAndView.addObject("shareQRCode", "");
				}
			}else{
				modelAndView.addObject("shareQRCode", "");
			}
			
		}else {
			modelAndView.setViewName("showUser");
		}
		return modelAndView;
    	
    }
    
    //----------------------------------以上界面都添加了userId，，下边的需要在微信里面配置号以后 通过微信登陆进行判断---------------------------------
    
    /**
     * 购买记录（我的宝贝）
     */
    @RequestMapping(value="/purchaseRecord/{userId}")
    public ModelAndView purchaseRecord(@PathVariable String userId) {
    	ModelAndView modelAndView=new ModelAndView();
    	modelAndView.addObject("userId", userId);
    	modelAndView.setViewName("personal_indent");
		return modelAndView;
	}
    
    /**
     * 购买记录详细界面
     */
    @RequestMapping(value="/purchaseRecordContext/{userId}/{type}",method=RequestMethod.GET)
    public ModelAndView purchaseRecordContext(@PathVariable String userId,@PathVariable String type) {
		ModelAndView modelAndView=new ModelAndView();
		String str=DataUtil.init().addUrl(Constant.URL+"purchaseRecord/"+userId+"/"+type).get();
		JSONObject jsonObject=JSONObject.parseObject(str);
		if (jsonObject.getString("code").equals("100")) {
			PurchaseRecordBean bean=new Gson().fromJson(str, PurchaseRecordBean.class);
			System.out.println(bean.getData().size());
			System.out.println(bean.getData().isEmpty());
			if(bean.getData()==null || bean.getData().isEmpty()){
				modelAndView.setViewName("showUser");
			}else{
				modelAndView.addObject("list",bean.getData());
				modelAndView.addObject("languageId",bean.getData().get(0).getLanguageId());
				modelAndView.addObject("userId", userId);
				modelAndView.setViewName("personal_indent_context");
			}
			
		}else {
			if(jsonObject.getString("code").equals("209")){
				modelAndView.addObject("msg", "暂无宝贝");
			}
			
			modelAndView.setViewName("showUser");
		}
		
    		return modelAndView;
	}
    
    
    
    @RequestMapping("/hasRecordSearch/{userId}/{search}")
    @ResponseBody
    public String hasRecordSearch(@PathVariable String userId,@PathVariable String search){
    	String string;
    	if (search.equals("All")) {
		string=DataUtil.init().addUrl(Constant.URL+"purchaseRecord/"+userId+"/All").get();
		}else {
			string=DataUtil.init().addUrl(Constant.URL+"purchaseRecordSearch/"+userId+"/"+search).get();
		}

    	JSONObject jsonObject=JSONObject.parseObject(string);
		if (jsonObject.getString("code").equals("100")) {
			PurchaseRecordBean bean=new Gson().fromJson(string, PurchaseRecordBean.class);
			if(bean.getData() !=null || !bean.getData().isEmpty()){
				return "100";
			}else{
				return "101";
			}
			
		}else {
			return "101";
		}
    	
    }
    
    /**
     * 购买记录搜索
     */
    @RequestMapping("/purchaseRecordSearch/{userId}/{search}")
    @ResponseBody
    public ModelAndView getIndentSearchValue(@PathVariable String userId,@PathVariable String search) {
		ModelAndView modelAndView=new ModelAndView();
		String string;
		if (search.equals("All")) {
		string=DataUtil.init().addUrl(Constant.URL+"purchaseRecord/"+userId+"/All").get();
		}else {
			string=DataUtil.init().addUrl(Constant.URL+"purchaseRecordSearch/"+userId+"/"+search).get();
		}
		JSONObject jsonObject=JSONObject.parseObject(string);
		if (jsonObject.getString("code").equals("100")) {
			PurchaseRecordBean bean=new Gson().fromJson(string, PurchaseRecordBean.class);
			modelAndView.addObject("list",bean.getData());
			modelAndView.addObject("museumId",bean.getData().get(0).getMuseumId());
			modelAndView.addObject("languageId",bean.getData().get(0).getLanguageId());
			modelAndView.setViewName("personal_indent_context");	
		}else {
			modelAndView.setViewName("showUser");
		}
    		
    		
		return modelAndView;
	}
    
    
    
    /**
     * 用户吐槽提交数据
     * 
     */
    @RequestMapping(value="userFeedBack/{userId}",method=RequestMethod.POST)
    @ResponseBody public JSONArray userFeedBack(@PathVariable String userId,@RequestBody String text) throws UnsupportedEncodingException{
    	System.out.println("吐槽提交数据："+text);	
    	text=text.split("=")[1];
		text = URLDecoder.decode(text, "UTF-8");
		text = InfilterHTMLTag.delHTMLTag(text);
		System.out.println("吐槽提交数据："+text);
		JSONObject body=new JSONObject();
		body.put("userId", userId);
		body.put("content", text);
		String str=DataUtil.init().addUrl(Constant.URL+"userFeedback").post(body);
		JSONObject res=JSONObject.parseObject(str);
		System.out.println(str);
		if (res.getString("code").equals("100")) {
			//吐槽成功获取所有数据
			JSONObject jsonObject=ToolClass.getUserFeedBackHistory(userId);
			System.out.println("吐槽成功："+jsonObject.getString("data"));
			return (JSONArray) JSONArray.parse(jsonObject.getString("data"));
    			
		}else {
			return new JSONArray();
		}
    		
    }
    
    
    /**
     * 用户吐槽获取数据
     */
    @RequestMapping("getUserFeedBack/{userId}")
    public ModelAndView getUserFeedBackData(@PathVariable String userId) {
    		ModelAndView modelAndView=new ModelAndView();
    		modelAndView.addObject("userId", userId);
    		modelAndView.setViewName("personal_complain");
		return modelAndView;
	}
    
    /**
     * 用户吐槽数据展示
     */
    @RequestMapping("getUserFeedBackContent/{userId}")
    public ModelAndView getUserFeedBackContent(@PathVariable String userId){
    	ModelAndView modelAndView=new ModelAndView();
    	JSONObject jsonObject=ToolClass.getUserFeedBackHistory(userId);
		if (jsonObject!=null) {
			if (jsonObject.containsKey("type")) {
				modelAndView.addObject("msg", "无历史纪录");
				modelAndView.setViewName("showUser");
				return modelAndView;
    				
			}else {
				UserFeedBackBean ben=new Gson().fromJson(jsonObject.toJSONString(), UserFeedBackBean.class);
				JSONArray arr=JSONArray.parseArray(jsonObject.getString("data"));
				modelAndView.addObject("list", ben.getData());
				modelAndView.setViewName("personal_complain_content");
				return modelAndView;
			}
		}else {
			modelAndView.setViewName("showUser");
			return modelAndView;
		}
		
    }
    
    /**
     * 获取身份账号
     */
    @RequestMapping("/getUserAccount/{userId}")
    @ResponseBody
    public String getUserAccount(@PathVariable String userId) {
    		String account;
    		String string=DataUtil.init().addUrl(Constant.URL+"userIdentityAccount/"+userId).get();
    		JSONObject jsonObject=JSONObject.parseObject(string);
    		if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("100")) {
				account=jsonObject.getString("data");
			}else {
				account="";
			}
		return account;
	}
    
    /**
     * 用户收益界面（我的团队）
     * @param userId
     * @return
     */
    @RequestMapping("/goCenter/{userId}")
    public ModelAndView enterPersonalCenter(@PathVariable String userId) {
    		//总收益
		String str=DataUtil.init().addUrl(Constant.URL+"userIncomeAll/"+userId).get();
		JSONObject jsonObject=JSONObject.parseObject(str);
		ModelAndView modelAndView=new ModelAndView();
		if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("100")) {
			JSONArray arr= JSONArray.parseArray(jsonObject.getString("data"));
			JSONObject item=arr.getJSONObject(0);
			System.out.println(item);
			modelAndView.addObject("total",item.getString("total"));
			modelAndView.addObject("total1",item.getString("total1"));
			modelAndView.addObject("total2",item.getString("total2"));
			modelAndView.addObject("total3",item.getString("total3"));
			modelAndView.addObject("total4",item.getString("total4"));
			modelAndView.addObject("total5",item.getString("total5"));
			modelAndView.addObject("userId", userId);
			modelAndView.setViewName("personal_team");
			return modelAndView;
		}else {
			modelAndView.setViewName("showUser");
			return modelAndView;
		}
	}
    
    /**
     * 用户收益tab页面
     */
    @RequestMapping("/getEarnings/{userId}/{type}")
    public ModelAndView userIncomeType(@PathVariable String userId,@PathVariable String type) {
    		ModelAndView modelAndView=new ModelAndView();
    		JSONObject jsonObject=ToolClass.getUserIncomeType(userId, type);
    		if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("100")) {
			UserInComeTypeBean bean=new Gson().fromJson(jsonObject.toString(), UserInComeTypeBean.class);
			modelAndView.addObject("total",bean.getData().get(0).getSubtotal());
			if (bean.getData().get(0).getNameList().size()>0) {
				modelAndView.addObject("list",bean.getData().get(0).getNameList());
			}else {
				modelAndView.addObject("list",null);
			}
    			
    			modelAndView.setViewName("personal_team_content");
			return modelAndView;
		}else if(jsonObject.containsKey("code") && jsonObject.getString("code").equals("214")){
			modelAndView.addObject("list",null);
			modelAndView.setViewName("personal_team_content");
			return modelAndView;
		}else {
			modelAndView.setViewName("showUser");
			return modelAndView;
		}
		
	}
    
   /**
     * 进入用户申请结算界面
     */
    @RequestMapping("/goSettlement/{userId}/{total}/{total1}/{total2}/{total3}/{c}")
    public ModelAndView goSettlement(@PathVariable String userId ,
    		@PathVariable String total,@PathVariable String total1,@PathVariable String total2,@PathVariable String total3) {
    	ModelAndView modelAndView=new ModelAndView();
    	modelAndView.addObject("userId", userId);
    	modelAndView.addObject("total", total);
    	modelAndView.addObject("total1", total1);
    	modelAndView.addObject("total2", total2);
    	modelAndView.addObject("total3", total3);
    	System.out.println(total3);
    	modelAndView.setViewName("personal_center");
		return modelAndView;
	}
    
    
    
    /**
     * 用户申请提现
     */
    @RequestMapping(value="/postSettlement",method=RequestMethod.POST)
    @ResponseBody
    public String postSettlement(@RequestBody String body){
    	System.out.println(body);
    	try {
			body = URLDecoder.decode(body, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(body);
    		
    		String[] strings=body.split("\\&");
    		JSONObject postData=new JSONObject();
    		postData.put("userId", strings[0].split("=")[1]);
    		postData.put("phone", strings[1].split("=")[1]);
    		postData.put("bankAccount", strings[2].split("=")[1]);
    		postData.put("belongBank", strings[3].split("=")[1]);
    		postData.put("name", strings[4].split("=")[1]);
			postData.put("total", strings[5].split("=")[1]);
    		postData.put("total1", strings[6].split("=")[1]);
    		postData.put("total2", strings[7].split("=")[1]);
    		postData.put("total3", strings[8].split("=")[1]);
    		System.out.println(postData);
    		String str=DataUtil.init().addUrl(Constant.URL+"userApplicationSettlement").post(postData);
    		JSONObject jsonObject=JSONObject.parseObject(str);
    		if (jsonObject.containsKey("code") ) {
    			String code=jsonObject.getString("code");
				if (code.equals("100")) {
					return "100";
				}else if(code.equals("215")){
					return "215";
				}else if(code.equals("212")){
					return "212";
				}else if(code.equals("217")){
					return "217";
				}else {
					return "101";
				}
		}else {
			return "申请失败";
		}
    }
    
    
    
    
    /**
     * 我的二维码
     * @param userId 用户id
     * @return
     */
    
    @RequestMapping("/getQRCode/{userId}")
    public ModelAndView getQRCode(@PathVariable String userId) {
    	ModelAndView modelAndView=new ModelAndView();
    	String qrCode = ToolClass.getQRCode(userId);
    	if (qrCode!=null ) {
    		JSONObject json = JSONArray.parseArray(qrCode).getJSONObject(0);
    		String url = json.getString("twoDimensionAddress");
    		String nick = json.getString("nickname");
    		modelAndView.addObject("url",url);
    		modelAndView.addObject("nick",nick);
			modelAndView.setViewName("showQRCode");
		}else {
			modelAndView.setViewName("showUser");
		}
    	
    	return modelAndView;
	}
    
    
    /**
     * 打开扫一扫界面
     */
    @RequestMapping("/openScan")
    public ModelAndView openScan(){
    	return new ModelAndView("personal_scan");
    }
    
    
    
    
    
//---------------------------------------------------------
//支付接口       
    /**
     *微信支付
     *WechatDefray 
     */
    @RequestMapping(value="/WechatDefray/{total}/{ip}/{openId}",method=RequestMethod.GET)
    public JSONObject WechatDefray(@PathVariable String total, @PathVariable String ip,@PathVariable String openId) {
    	//获取用户id
    	String userId = ToolClass.getUserId(openId); 
    	JSONObject json =new JSONObject();
    	if(userId.equals("-1")){
    		return json;
    	}
    	//获取所有参数
    	
    	//商户的公众号id
    	String appid = Constant.APPID;
    	//微信支付的商户号
    	String mch_id = ConfigUtil.MCH_ID;
    	//指定支付设备
    	String device_info ="WEB";
    	//随机字符串
    	String nonce_str = UUID.randomUUID().toString().replace("-", "");
    	//商品描述
    	String body = "文物语音购买";
    	//商户订单号
    	String out_trade_no = System.currentTimeMillis()/1000+"";
    	//总价格  分
    	int total_fee = Integer.parseInt(total.trim().replace(",", "").replace(".", ""));
    	System.out.println(total_fee);
    	//用户终端 ip
    	String spbill_create_ip = ip;
    	//异步通知url
    	String notify_url = "http://www.baidu.com";
    	//交易类型
    	String trade_type = "JSAPI";
    	//用户唯一标识  
    	String openid = openId;
    	
    	//将数据进行签名
    	SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
    	parameters.put("appid", appid);
    	parameters.put("body", body);
    	parameters.put("device_info", device_info);
    	parameters.put("mch_id", mch_id);
    	parameters.put("nonce_str", nonce_str);
    	parameters.put("notify_url", notify_url);
    	parameters.put("out_trade_no", out_trade_no);
    	parameters.put("total_fee", total_fee);
    	parameters.put("spbill_create_ip", spbill_create_ip);
    	parameters.put("trade_type", trade_type);
    	parameters.put("openid", openid);
    	//通过签名算法计算出的签名值
    	String sign = PayCommonUtil.createSign("UTF-8", parameters);
    	System.out.println(sign);
    	//发送给微信的签名后的xml
    	String t_xml = ToolClass.getTXML(parameters, sign).asXML();
    	System.out.println(t_xml);
    	//获取统一下单结果
    	String res_WX = CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", t_xml);
    	System.out.println("----"+res_WX);
    	
    	Map<String, String> map;
		try {
			map = XMLUtil.doXMLParse(res_WX);
			String str_times = String.valueOf(System.currentTimeMillis()/1000);
			String prepay_id = map.get("prepay_id");
			parameters = new TreeMap<Object, Object>();
			parameters.put("appId", appid);
			parameters.put("timeStamp", str_times);
			parameters.put("nonceStr", nonce_str);
			parameters.put("package", "prepay_id="+prepay_id);
			parameters.put("signType", "MD5");
			
			String reSign = PayCommonUtil.createSign("UTF-8", parameters);
			json.put("appId", appid);
			json.put("timeStamp", str_times);
			json.put("nonceStr", nonce_str);
			json.put("pkg", "prepay_id="+prepay_id);
			System.out.println(prepay_id);
			json.put("signType", "MD5");
			json.put("paySign", reSign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return json;
		}
		return json;
    	
    }
    
    /**
     * 微信支付结果异步回调
     */
    @RequestMapping(value="/reWXPostRes",method= RequestMethod.POST)
    public void rePostRes(HttpServletRequest request){
    	SAXReader reader=new SAXReader();
		   try {
			request.setCharacterEncoding("UTF-8");
			Document document=reader.read(request.getInputStream());
			Element element=document.getRootElement();
			String msgTypeString=element.elementText("MsgType");
		   }catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
    
    /**
     * 支付成功向后台提交支付物品数据
     */
    @RequestMapping(value="/postPayRes",method =RequestMethod.POST)
    @ResponseBody
    public String postPayRes(@RequestBody String bodys){
    	System.out.println("进来了");
    	System.out.println("bodys是：" + bodys);
    	//TODO:total=0.97&saletotal=0&hisId=9&num=1&lid=1&userId=22&museumId=41
    	JSONObject body =new JSONObject();
    	String[] split = bodys.split("\\&");
    	for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
			String[] s= split[i].split("=");
			if(s[0].equals("item")){
				JSONArray arr=new JSONArray();
				s[1] =URLDecoder.decode(s[1]);
				if(s[1].contains(",")){
					String[] split2 = s[1].split(",");
					for (int j = 0; j < split2.length; j++) {
						JSONObject item =new JSONObject();
						item.put("historicalRelicId", split2[j]);
						arr.add(item);
					}
				}else{
					JSONObject item =new JSONObject();
					item.put("historicalRelicId", s[1]);
					arr.add(item);
				}
				body.put("historicals",arr);
			}else{
				if(s[1].equals("-1")){
					body.put(s[0], "");
				}else{
					body.put(s[0], s[1]);
				}
			}
			
		}
    	
    	System.out.println("向后台传的参数是：" + body);
    	String post = DataUtil.init().addUrl(Constant.URL+"payFruitSubmitTo").post(body);
    	System.out.println("获取到的数据："+post);
    	JSONObject res=JSONObject.parseObject(post);
    	int code =Integer.parseInt(res.getString("code"));
    	if(100 == code){
    		//支付成功
    		return "100";
    	}else if(207 == code){
    		//支付失败
    		return "200";
    	}else{
    		//运行异常
    		return "101";
    	}
    	
    	
    }
    
    /**
     * 短号指南
     * @return
     */
    @RequestMapping("/guide_Cornet")
    public ModelAndView getGuideCornetView(){
    	return new ModelAndView("guide_cornet");
    }
    
    
    //-------------------------------------------测试token有效性-------------------------------------------、
    
    
    
    @RequestMapping("/getAccess_token")
    @ResponseBody
    public String getAccessToken(){
    	if (Constant.ACCESS_TOKEN_EXPIRES_IN == -1 || System.currentTimeMillis() - Constant.ACCESS_TOKEN_EXPIRES_IN > 7000000) {
			JSONObject json =JSONObject.parseObject(ToolClass.getToken());
			if(json.containsKey("access_token")){
				Constant.ACCESS_TOKEN = json.getString("access_token");
				Constant.ACCESS_TOKEN_EXPIRES_IN = System.currentTimeMillis();
				return Constant.ACCESS_TOKEN;
			}else{
				return "";
			}
		}else{
			return Constant.ACCESS_TOKEN;
		}
    }
    
    
    
    
    
    
}
