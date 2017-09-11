package com.cn.hnust.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.hnust.kotlinPro.bean.InQueryHistoricalRelicCornetBen;
import com.cn.hnust.kotlinPro.bean.InQueryHistoricalRelicCornetBen.DataBean.RelicListBean;
import com.cn.hnust.kotlinPro.bean.PicAndTextBean;
import com.cn.hnust.kotlinPro.net.DataUtil;
import com.cn.hnust.pojo.IntroduceBean;
import com.cn.hnust.pojo.languageBean;
import com.google.gson.Gson;


public  class ToolClass {
	
	
	public static String getMuseumBean(String usrs, String...objs) throws UnsupportedEncodingException {
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < objs.length; i++) {
			params.add(objs[i]);
		}
		String urlParam = Constant.URL + usrs;
		String charset = "utf-8";
		String fruit = URLConnectionGET.sendGet2(urlParam, params, charset);
		return fruit;
	}
	
	public static String getWeixin(String usrs, String...objs) throws UnsupportedEncodingException {
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < objs.length; i++) {
			params.add(objs[i]);
		}
		String urlParam = usrs;
		String charset = "utf-8";
		String fruit = URLConnectionGET.sendGet2(urlParam, params, charset);
		return fruit;
	}
	
	/**
	 * 获取到JsApi_ticket后的json数据
	 * @param token
	 * @return  带有ticket 的json数据
	 */
	public static String getJsApi_ticket(String token){
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
		return getText(url);
	}
	
	/**
	 * 获取到access_token后的json数据
	 * @return  带有 access_token 的json数据
	 */
	public static String getToken(){
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constant.APPID+"&secret="+Constant.appsecret;
		return getText(url);
	}
	
	
	/**
	 * reGet JsApi_ticket
	 * @param refresh_token
	 * @return
	 */
	public static String reGetJsApi_ticket(String refresh_token){
		
		String url="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+Constant.APPID+"&grant_type=refresh_token&refresh_token="+refresh_token;
		JSONObject obj=JSONObject.parseObject(getText(url));
		if(obj.containsKey("access_token")){
			return getJsApi_ticket(obj.getString("access_token"));
		}else{
			return "";
		}
		
	}
	
	
	/**
	 * get请求获取字符串
	 * @param url
	 * @return  返回字符串
	 */
	public static String getText(String url){
		StringBuffer resultBuffer = null;
		BufferedReader br = null; 
		
		try {
			HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
			conn.connect();
			resultBuffer = new StringBuffer();  
            InputStream aa = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(aa, "utf-8"));  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                resultBuffer.append(temp);  
            } 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return resultBuffer.toString();
		
		
	}
	
	
	
	/** 
     * SHA1 安全加密算法 
     * @param maps 参数key-value map集合 
     * @return 
     * @throws DigestException  
     */  
    public static String SHA1(String str)  {  
        //获取信息摘要 - 参数字典排序后字符串  
        
        try {  
            //指定sha1算法  
            MessageDigest digest = MessageDigest.getInstance("SHA-1");  
            digest.update(str.getBytes());  
            //获取字节数组  
            byte messageDigest[] = digest.digest();  
            // Create Hex String  
            StringBuffer hexString = new StringBuffer();  
            // 字节数组转换为 十六进制 数  
            for (int i = 0; i < messageDigest.length; i++) {  
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
                if (shaHex.length() < 2) {  
                    hexString.append(0);  
                }  
                hexString.append(shaHex);  
            }  
            return hexString.toString().toUpperCase();  
  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;
        }  
    }
    
    
    /**
     * 获取博物馆信息
     * @param museumId  博物馆id
     * @param languageId 语言id
     * @param languageName 语言名字
     * @param modelView  
     * @throws Exception
     */
    public static void getMuseumIntroduceData(String museumId,  String languageId, String languageName,ModelAndView modelView) throws Exception{
    	
   
    		String str=DataUtil.init().addUrl(Constant.URL+"museumIntroduce/"+
    				museumId+"/"+languageId).get();
		IntroduceBean introduceBean = new Gson().fromJson(str, IntroduceBean.class);
		String str1=DataUtil.init().addUrl(Constant.URL+"retrievalLanguage/"+
				museumId).get();
		languageBean languageBean = new Gson().fromJson(str1, languageBean.class);
		if ("100".equals(introduceBean.getCode())) {
			StringBuffer buf = new StringBuffer();
			for (IntroduceBean.DataBean dataBean : introduceBean.getData()) {
				buf.append(dataBean.getPictureAddress() + ",");
			}
			modelView.addObject("languaList", languageBean.getData());
			modelView.addObject("describe", introduceBean.getData().get(0).getDescribe());
			modelView.addObject("museumName", introduceBean.getData().get(0).getMuseumName());
			modelView.addObject("pictureAddress", buf.substring(0, buf.length() - 1));
			modelView.addObject("soundAddress", introduceBean.getData().get(0).getSoundAddress());
			modelView.addObject("status", "YES");
			modelView.addObject("languageName", languageName);
			modelView.addObject("museumId",museumId);
			modelView.addObject("languageId", languageId);
			modelView.setViewName("app_detial");
			System.out.println(languageName);
			
		} else {
			modelView.setViewName("showUser");
		}
		
    }
    
    
    
    /**
     * 获取博物馆文物短号信息
     * @param museumName 博物馆名称
     * @return 数组 index 0 状态码   index 1 返回值
     */
    public static Object[] getQueryHistoricalCornetRes(String museumName){
    		Object [] objects=new Object[2];
    		String str = DataUtil.init().addUrl(Constant.URL+"inquiryHistoricalRelicCornet/"+museumName).get();
		JSONObject json=JSONObject.parseObject(str);
		String code=json.getString("code");
		if (code.equals("100")) {
			objects[0]=100;
			objects[1]=json;
		}else if (code.equals("101")) {
			objects[0]=101;
			objects[1]="";
		}else if (code.equals("203")) {
			objects[0]=203;
			objects[1]="";
		}else{
			objects[0]=203;
			objects[1]="";
		}
		System.out.println(objects);
    	
    		return objects;
    }
    
    /**
     * 获取用户从界面输入的信息检索出来的博物馆list
     * @param museumName 模糊查询的博物馆名字 ，必须包含博物馆三个字
     * @return
     */
    @SuppressWarnings("deprecation")
	public static List<PicAndTextBean> getQueryHistoricalCornetUrl(String openid,String search){
    	List<PicAndTextBean> urlStrings=new ArrayList<PicAndTextBean>();
		Object[] objects=ToolClass.getQueryHistoricalCornetRes(search);
		int code=(int) objects[0];
		if (code==100) {
			String str=((JSONObject) objects[1]).toJSONString();
			InQueryHistoricalRelicCornetBen ben=new Gson().fromJson(str, InQueryHistoricalRelicCornetBen.class);
			List<InQueryHistoricalRelicCornetBen.DataBean> relicListBean=ben.getData();
			for (InQueryHistoricalRelicCornetBen.DataBean bean : relicListBean) {
				RelicListBean relicListBean2 = bean.getRelicList().get(0);
				PicAndTextBean picAndTextBean=new PicAndTextBean();
				picAndTextBean.setDesc(relicListBean2.getMuseumName()+"短号查询...");
				picAndTextBean.setTitle(relicListBean2.getMuseumName());
				picAndTextBean.setPicUrl(Constant.ResouseUrl+relicListBean2.getPictureAddress());
				String name=URLEncoder.encode(relicListBean2.getMuseumName());
				String userId = getUserId(openid);
				picAndTextBean.setJumpUrl(Constant.CurrentURL+"queryHistoricalCornet/"+name+"?userId="+userId);
				urlStrings.add(picAndTextBean);
			}
			return urlStrings;
		}else if (code==203) {
			return urlStrings;
		}else {
			return urlStrings;
		}
    		
    }
    
    
    
    /**
     * 获取线下博物馆文物详细信息
     * @param modelAndView
     * @param object
     * @param museumName
     */
    public static void  getXXHistoricalInfo(ModelAndView modelAndView,JSONObject object,String museumName){
    	
    	JSONArray array=JSONArray.parseArray(object.getString("data"));
		JSONObject item=JSONObject.parseObject(array.get(0).toString());
		modelAndView.addObject("name",item.getString("historical_relic_name"));
		String id=item.getString("historical_relic_id");
		modelAndView.addObject("id",id);
		String lgString=DataUtil.init().addUrl(Constant.URL+"historicalRelicLanguage/"+id).get();
		JSONObject jsonObject=JSONObject.parseObject(lgString);
		if (jsonObject.getString("code").equals("100")) {
			JSONArray lgs=JSONArray.parseArray(jsonObject.getString("data"));
			modelAndView.addObject("languaList",lgs);
			System.out.println(lgs);
			modelAndView.addObject("level",item.getString("level"));
	    	modelAndView.addObject("dynasty",item.getString("dynasty"));
	    	if (museumName.length()>0) {
    			modelAndView.addObject("museumName",museumName);
			}else {
				modelAndView.addObject("museumName",item.getString("museumName"));
			}	
	    		modelAndView.addObject("museumId",item.getString("museum_id"));
	    		if(item.getString("describe1").length()>45){
	    			modelAndView.addObject("describe",item.getString("describe1").substring(0, 45)+"...");
	    		}else{
	    			modelAndView.addObject("describe",item.getString("describe1")+"...");
	    		}
	    		
	    		JSONArray picList=JSONArray.parseArray(item.getString("pictureList"));
	    		StringBuilder stringBuilder=new StringBuilder();
	    		for (int i = 0; i < picList.size(); i++) {
					JSONObject it=JSONObject.parseObject(picList.get(i).toString());
					String pic=it.getString("pictureAddress");
					stringBuilder.append(pic+",");
			}
    		String pics=stringBuilder.toString().substring(0, stringBuilder.toString().length()-1);
    		modelAndView.addObject("picList",pics);
    		modelAndView.setViewName("richscan_index");
		}
    }
    
    /**
     * 获取用户吐槽数据的历史记录
     * @param userId
     * @return
     */
    public static  JSONObject getUserFeedBackHistory(String userId) {
		String res=DataUtil.init().addUrl(Constant.URL+"userFeedbackHistory/"+userId).get();
		JSONObject jsonObject=JSONObject.parseObject(res);
		if (jsonObject.getString("code").equals("100")) {
			return jsonObject;
		}else if (jsonObject.getString("code").equals("211")) {
			jsonObject.put("type", "211");
			return jsonObject;
		}else {	
			return null;
		}
    }
    
    /**
     * 获取用户不同类型角色的收入信息
     * @param userId  用户id
     * @param type 用户角色类型
     * @return
     */
    public static JSONObject getUserIncomeType(String userId,String type){
		String string=DataUtil.init().addUrl(Constant.URL+"userIncomeType/"+userId+"/"+type).get();
		JSONObject jsonObject=JSONObject.parseObject(string);
		if (jsonObject.containsKey("code")) {
			return jsonObject;
		}else {
			return new JSONObject();
		}
    		
    }
    
    /**
     * 用户二维码地址
     * @param userId 用户id
     * @return 返回 二维码url  访问成功返回路径  失败返回 nil
     */
    public static String getQRCode(String userId){
    	String string = DataUtil.init().addUrl(Constant.URL+"myQRCode/"+userId).get();
    	JSONObject object=JSONObject.parseObject(string);
    	if (object.containsKey("code") && object.getString("code").equals("100")) {
			return object.getString("data");
		}else{
			return null;
		}
    }

    /**
     * 处理用户在公众号界面发送的消息和按钮点击事件，并进行相应的处理
     * @param request
     * @param response 
     */
	public static void praseMsg(HttpServletRequest request, HttpServletResponse response) {
		
		 SAXReader reader=new SAXReader();
		 PrintWriter pWriter=null;
		   try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Document document=reader.read(request.getInputStream());
			Element element=document.getRootElement();
			String msgTypeString=element.elementText("MsgType");
			String from=element.elementText("ToUserName");
			String to=element.elementText("FromUserName");
			pWriter=response.getWriter();
			switch (msgTypeString) {
				//当前接收到的消息是文本类型。
			case "text":
				String content=element.elementText("Content");
				int status= parseText(element);
				if (status==0) {
					String a="";
					List<PicAndTextBean> urls = getQueryHistoricalCornetUrl(to,content);
					a = textMsg(to, from,""+urls.isEmpty()).asXML();
					if(urls.size() > 0){
						a = PicAndTextMsg(to, from,urls).asXML();
					}else{
						a = textMsg(to, from,"此博物馆暂未开放").asXML();
					}
					pWriter.append(a);
					
				}else if (status==1) {
					List<PicAndTextBean> urlbean=new ArrayList<>();
					String[] split = new String[2];
					if(content.split("\\.").length ==2){
						split = content.split("\\.");
					}else if(content.split("\\s+").length ==2){
						split = content.split("\\s+");
					}else if(content.split("\\d+$").length ==1){
						split[0] = content.split("\\d+$")[0];
						split[1] = content.replace(split[0], "");
					}
					
					String userId = getUserId(to);
					String info;
					if(userId.equals("-1")){
						info = textMsg(to, from,"当前用户被禁用！").asXML();
					}else{
						String  urlString=Constant.CurrentURL+"nameAndCornet?museumName="+URLEncoder.encode(split[0])+"&cornette="+split[1]+"&userId="+userId;
						PicAndTextBean ben =new PicAndTextBean();
						ben.setDesc("当前文物短号为：【"+split[1]+"】");
						ben.setJumpUrl(urlString);
						String str=DataUtil.init().addUrl(Constant.URL+"nameAndCornet/"+split[0]+"/"+split[1]).get();
						JSONObject object=JSONObject.parseObject(str);
						if(object.containsKey("code") && object.getString("code").equals("100")){
							JSONArray array=JSONArray.parseArray(object.getString("data"));
							JSONObject item=JSONObject.parseObject(array.get(0).toString());
							JSONArray picList=JSONArray.parseArray(item.getString("pictureList"));
							JSONObject it=JSONObject.parseObject(picList.get(0).toString());
							String pic=it.getString("pictureAddress");
							ben.setTitle(split[0]+ " - "+item.getString("historical_relic_name") );
							
							ben.setPicUrl(Constant.ResouseUrl+pic);
							urlbean.add(ben);
							if(null !=pic && pic.length() >0){
								info = PicAndTextMsg(to, from,urlbean).asXML();
							}else{
								info = textMsg(to, from,"此文物还未录入，请在“语音导览”模块内收听其他文物介绍").asXML();
							}
						}else{
							info = textMsg(to, from,"此文物还未录入，请在“语音导览”模块内收听其他文物介绍").asXML();
						}
						
					}
					pWriter.append(info);
					
				}else if (status==2) {
					pWriter.append(textMsg(to, from, "我不清楚你要让我干嘛！\n查询博物馆短号：请输入博物馆名字\n例如：大唐博物馆\n\n查询文物详情：请输入“博物馆名字”.“文物短号”\n例如：大唐博物馆.1123").asXML());
				}else {
					pWriter.append(textMsg(to, from, "我不清楚你要让我干嘛！\n查询博物馆短号：请输入博物馆名字\n例如：大唐博物馆\n\n查询文物详情：请输入“博物馆名字”.“文物短号”\n例如：大唐博物馆.1123").asXML());
				}
				break;
				//当前接收到的消息是图片类型的。
			case "image":
				//pWriter.append(textMsg(to, from, "别发图片，要发发裸照！").asXML());		
				break;
				//当前接收倒得消息是语音类型。
			case "voice":
				//pWriter.append(textMsg(to, from, "再发语音打死你！").asXML());
				break;
				//当前接收到的消息是视频类型。
			case "video":
				//pWriter.append(textMsg(to, from, "别发视频，再发报警！").asXML());
				break;
				//当前接收到的消息是短视频类型。
			case "shortvideo":
				//pWriter.append(textMsg(to, from, "别发短视频，再发打死你！").asXML());
				break;
				//当前接收到的消息是地理位置类型。
			case "location":
				//pWriter.append(textMsg(to, from, "发位置信息干嘛？嗯哼").asXML());
				break;
				//当前接收到的消息是链接类型。
			case "link":
				//pWriter.append(textMsg(to, from, "发链接我进不去啊！").asXML());
				break;
				//当前接收倒得消息是菜单事件类型。
			case "event":
				String key = element.elementText("EventKey");
				//TODO:需要添加二维码菜单的点击事件key
				if (key.equals("V1001_ID")) {
					pWriter.append(textMsg(to, from, to).asXML());
				}
				if(element.elementText("Event").equals("subscribe")){
					String id=getUserId(to);
					String info;
					if(id.equals("-1")){
						info = textMsg(to, from, "当前用户被禁用！").asXML();
					}else{
						info = textMsg(to, from, Constant.SUBSCRIBE_INFO).asXML();
					}
					pWriter.append(info);
					
				}
				break;	

			default:
				break;
			}
			} catch (Exception e) {
				System.out.println("dom4j解析数据异常");
				e.printStackTrace();
			} finally {
				pWriter.close();
			}
		
	}

	
	/**
	 * 判断用户数据文本信息的类型 
	 * @param element 微信返回的xml数据
	 * @return  int类型  0：需要检索 博物馆短号 1：需要检索文物详情 2：非法操作
	 */
	private static int parseText(Element element) {
		String content = element.elementText("Content");
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
	
	
	

	/**
	 * 构建图文消息
	 * @param to 发送给哪个用户
	 * @param from  开发者的openid
	 * @param map 图文消息list
	 */
	private static Element  PicAndTextMsg(String to,String from,List<PicAndTextBean> map) {
		Element root=DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addCDATA(to);
		root.addElement("FromUserName").addCDATA(from);
		root.addElement("CreateTime").addText(System.currentTimeMillis()/1000+"");
		root.addElement("MsgType").addCDATA("news");
		//添加图文消息个数
		root.addElement("ArticleCount").addText(map.size()+"");
		//添加图文父节点
		Element articles = root.addElement("Articles");
		if (!map.isEmpty()) {
			for (PicAndTextBean picAndTextBean : map) {
				Element item = articles.addElement("item");
				item.addElement("Title").addCDATA(picAndTextBean.getTitle());
				item.addElement("Description").addCDATA(picAndTextBean.getDesc());
				item.addElement("PicUrl").addCDATA(picAndTextBean.getPicUrl());
				item.addElement("Url").addCDATA(picAndTextBean.getJumpUrl());
			}
		}
		return root;
	}
	
	/**
	 * 构建 文本消息和连接消息
	 * @param to 发送给哪个用户
	 * @param from 填写开发者openid
	 * @param msg 发送的文本
	 */
	private static Element textMsg(String to,String from,String msg) {
		
		Element root=DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addCDATA(to);
		root.addElement("FromUserName").addCDATA(from);
		root.addElement("CreateTime").addCDATA(System.currentTimeMillis()/1000+"");
		root.addElement("MsgType").addCDATA("text");
		root.addElement("Content").addCDATA(msg);
		
		return root;
		
	}
	
	/**
	 * 通过oppenid获取用户userId
	 * @param openid 用户在微信公众号的唯一标识
	 * @return 用户id
	 */
	
	public static String  getUserId(String openid){
		
		String clientAddress = Constant.CLIENT_ADDRESS;
		
		String userinfo = DataUtil.init().addUrl(Constant.URL+"myUserId/"+openid+"/"+clientAddress).get();
		JSONObject jsonObject=JSONObject.parseObject(userinfo);
		if (jsonObject.containsKey("code") && jsonObject.getString("code").equals("100")) {
			return jsonObject.getString("data");
		}else if(jsonObject.containsKey("code") && jsonObject.getString("code").equals("218")){
			return "-1";
		}else {
			return "";
		}
	}
	
	
	
	/**
	 * 解码base64
	 * @param str
	 * @return
	 */
	public static String  decodeBase64(String str){
		
		 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		 try {
			byte[] decodeBuffer = decoder.decodeBuffer(str);
			String decode=new String(decodeBuffer);
			return decode;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		 
	}
	
	/**
	 * 加密base64
	 * @param str
	 * @return
	 */
	public static String encodeBase64(String str){
		byte[] bytes = str.getBytes();
		 return new sun.misc.BASE64Encoder().encode(bytes);
	}

	
	
	
	/**
	 * 获取统一支付的xml 
	 * @param argsK
	 * @param args
	 * @param sign
	 * @return
	 */
	public static Element getTXML(SortedMap<Object, Object> parameters, String sign){
		Element root=DocumentHelper.createElement("xml");
		
		
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = entry.getKey().toString();
			String v = entry.getValue().toString();
			root.addElement(k).addText(v);
		}
		root.addElement("sign").addText(sign);
		
		return root;
		
	}
	
	/**
	 * 判断当前用户是否已经订阅公众号
	 * @param openid
	 * @return
	 */
	public static int isSubscribe(String openid){
		
		JSONObject obj=JSONObject.parseObject(getToken());
		if(obj.containsKey("access_token")){
			String access_token = obj.getString("access_token");
			//9h-TOMzfpV53d_3SQJvQtpFIRL6TsuSNIO2sX4tK_p3Ro1hEklLHlIh-LR9u9c-6K0lQSnOmhHF_SHcvKe6z8NapLcf4eRQIwU5tAfRlsoKwijyAp9ToPTvrKAUQ0YKQZFAgAFAIOJ
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
			
			JSONObject subscribe = JSONObject.parseObject(getText(url));
			if(subscribe.containsKey("subscribe")){
				if(subscribe.getString("subscribe").equals("0")){
					return 0;
				}else if(subscribe.getString("subscribe").equals("1")){
					return 1;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 获取当前公众号的accessToken并保活
	 * @return
	 */
	 public static String getAccessToken(){
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
	 
	 /**
	  * 获取当前公众号的getJsApi_ticket并保活
	  * @return
	  */
	 public static String getJsApiTicket(){
	    	if (Constant.JSAPI_TICKET_EXPIRES_IN == -1 || System.currentTimeMillis() - Constant.JSAPI_TICKET_EXPIRES_IN > 7000000) {
				JSONObject json =JSONObject.parseObject(ToolClass.getJsApi_ticket(getAccessToken()));
				if(json.containsKey("errmsg") && json.getString("errmsg").equals("ok")){
					Constant.JSAPI_TICKET = json.getString("ticket");
					Constant.JSAPI_TICKET_EXPIRES_IN = System.currentTimeMillis();
					return Constant.JSAPI_TICKET;
				}else{
					return "";
				}
			}else{
				return Constant.JSAPI_TICKET;
			}
	    }
	 
	 
	 
	 
	
	
    
    
    


}
