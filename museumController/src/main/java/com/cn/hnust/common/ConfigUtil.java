package com.cn.hnust.common;

public class ConfigUtil {
	/**
	 * 鏈嶅姟鍙风浉鍏充俊鎭�
	 */
	 public final static String APPID = "";//鏈嶅姟鍙风殑搴旂敤鍙�
	 public final static String APP_SECRECT = "";//鏈嶅姟鍙风殑搴旂敤瀵嗙爜
	 public final static String TOKEN = "weixinCourse";//鏈嶅姟鍙风殑閰嶇疆token
	 public final static String MCH_ID = Constant.MCH_ID;
	 public final static String API_KEY = "1919fe92e76b4d35aff4e26ff6389354";//API瀵嗛挜
	 public final static String SIGN_TYPE = "MD5";//绛惧悕鍔犲瘑鏂瑰紡
	 public final static String CERT_PATH = "D:/apiclient_cert.p12";//寰俊鏀粯璇佷功瀛樻斁璺緞鍦板潃
	//寰俊鏀粯缁熶竴鎺ュ彛鐨勫洖璋僡ction
	 public final static String NOTIFY_URL = "http://14.117.25.80:8016/wxweb/config/pay!paySuccess.action";
	//寰俊鏀粯鎴愬姛鏀粯鍚庤烦杞殑鍦板潃
	 public final static String SUCCESS_URL = "http://14.117.25.80:8016/wxweb/contents/config/pay_success.jsp";
	 //oauth2鎺堟潈鏃跺洖璋僡ction
	 public final static String REDIRECT_URI = "http://14.117.25.80:8016/GoMyTrip/a.jsp?port=8016";
	/**
	 * 寰俊鍩虹鎺ュ彛鍦板潃
	 */
	 //鑾峰彇token鎺ュ彛(GET)
	 public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	 //oauth2鎺堟潈鎺ュ彛(GET)
	 public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	 //鍒锋柊access_token鎺ュ彛锛圙ET锛�
	 public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// 鑿滃崟鍒涘缓鎺ュ彛锛圥OST锛�
	 public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 鑿滃崟鏌ヨ锛圙ET锛�
	 public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 鑿滃崟鍒犻櫎锛圙ET锛�
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * 寰俊鏀粯鎺ュ彛鍦板潃
	 */
	//寰俊鏀粯缁熶竴鎺ュ彛(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//寰俊閫�娆炬帴鍙�(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//璁㈠崟鏌ヨ鎺ュ彛(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//鍏抽棴璁㈠崟鎺ュ彛(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//閫�娆炬煡璇㈡帴鍙�(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//瀵硅处鍗曟帴鍙�(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//鐭摼鎺ヨ浆鎹㈡帴鍙�(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//鎺ュ彛璋冪敤涓婃姤鎺ュ彛(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
}
