package com.web.nike.add;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.web.nike.bean.AddProductResultBean;
import com.web.nike.bean.ProductPageInfo;
import com.web.nike.bean.UserLoginInfo;
import com.web.nike.util.HttpClientManager;
import com.web.nike.util.JsonUtil;
import com.web.nike.util.StringUtil;

public class AddProductThread implements Runnable {

	private HttpClientManager httpClientManager;
	private UserLoginInfo userInfo;
	private ProductPageInfo productInfo;
	private String productUrl = "";
	private String addUrl = "https://secure-store.nike.com/ap/services/jcartService";

	private Map<String, String> getUserCookies;
	private AddProductResultBean addResultBean;

	public HttpClientManager getHttpClientManager() {
		return httpClientManager;
	}

	public AddProductThread(UserLoginInfo userInfo, ProductPageInfo productInfo, String url) {
		this.productUrl = url;
		this.userInfo = userInfo;
		this.productInfo = productInfo;
		httpClientManager = new HttpClientManager(5000, 30000);
	}

	public void add() {
		String skuid = productInfo.getSkuMap().get(userInfo.getAutoSize());
		if (StringUtil.isEmpty(skuid)) {
			return;
		}
		productInfo.setSkuAndSize(skuid + ":" + userInfo.getAutoSize());
		productInfo.setSkuId(skuid);
		productInfo.setDisplaySize(userInfo.getAutoSize());

		add_();
	}

	@SuppressWarnings("unchecked")
	public void add_() {
		try {

			productInfo.setTime_(String.valueOf(System.currentTimeMillis()));
			String accessUrl = addUrl + productInfo.getAddProductUrl();

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			headers.put("Accept", "*/*");
			headers.put("Referer", productUrl);
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.8");

			StringBuilder cookie = new StringBuilder("slsw=" + (userInfo.getSlsw() == null ? "N:w" : userInfo.getSlsw()));
			cookie.append(";NIKE_COMMERCE_CCR=" + System.currentTimeMillis()).append(";nike_locale=cn/zh_cn")
				.append(";NIKE_COMMERCE_COUNTRY=CN")
				.append(";NIKE_COMMERCE_LANG_LOCALE=zh_CN")
				.append(";slCheck=" + userInfo.getSlCheck())
				.append(";llCheck=" + userInfo.getLlCheck())
				.append(";lls=" + userInfo.getLls())
				.append(";sls=" + userInfo.getSls())
				.append(";APID=" + userInfo.getAPID())
				.append(";AnalysisUserId=" + userInfo.getAnalysisUserId())
				.append(";guidS=" + "")
				.append(";guidU=" + "")
				;
			
			if (addResultBean != null) {
				if ("wait".equals(addResultBean.getStatus())) {
					accessUrl = accessUrl + "&pil=" + addResultBean.getPil();
//					if (getUserCookies != null && getUserCookies.get("ak_bmsc") != null) {
//						cookie.append(";ak_bmsc=" + getUserCookies.get("ak_bmsc"));
//					}
				} else if ("waitOutOfStock".equals(addResultBean.getStatus())) {
					accessUrl = accessUrl + "&pil=" + addResultBean.getPil() + "&psh=" + addResultBean.getPsh();
					cookie.append(";akavpau_SSVP=" + getUserCookies.get("akavpau_SSVP"));
//					if (getUserCookies != null && getUserCookies.get("ak_bmsc") != null) {
//						cookie.append(";ak_bmsc=" + getUserCookies.get("ak_bmsc"));
//					}
				}
			}

			URI uri = new URI(accessUrl);

			Map<String, Object> resultMap = httpClientManager.execHttpGet(uri, headers, cookie.toString(),
					userInfo.getProxyIp(), userInfo.getProxyPort(), userInfo.getProxyUser(), userInfo.getProxyPwd());
			long start = System.currentTimeMillis();

			headers = null;
			cookie = null;
			uri = null;

			addResultBean = null;
			String json = (String)resultMap.get("json");
			json = json.replace("nike_Cart_handleJCartResponse(", "").replace(");", "");

			addResultBean = JsonUtil.getObjectFromJson(json, AddProductResultBean.class);
			getUserCookies = null;
			getUserCookies = (Map<String, String>)resultMap.get("cookie");

			if ("wait".equals(addResultBean.getStatus())
					|| "waitOutOfStock".equals(addResultBean.getStatus())) {
				System.out.println("user=" + userInfo.getUserName() + " " + addResultBean.toString());
				if (!StringUtil.isEmpty(addResultBean.getEwt())) {
					long wait = Long.parseLong(addResultBean.getEwt());

					// TODO
					if (wait > 2) {
						wait = 2;
					}

					while(true) {
						if (System.currentTimeMillis() > (start + wait*1000)) {
							break;
						}
					}
				}
				add_();
			} else if ("failure".equals(addResultBean.getStatus())) {
				System.out.println(resultMap.toString());
				productInfo.getSkuMap().remove(productInfo.getDisplaySize());
				if (productInfo.getSkuMap().size() <= 1) {
					System.out.println("Sold out!!!!!!!!!");
					return;
				}
				for (Map.Entry<String, String> e : productInfo.getSkuMap().entrySet()) {
					String sizeTemp = e.getKey();
					String skuIdTemp = e.getValue();

					productInfo.setSkuAndSize(skuIdTemp + ":" + sizeTemp);
					productInfo.setSkuId(skuIdTemp);
					productInfo.setDisplaySize(sizeTemp);
					break;
				}
				add_();
			} else if ("success".equals(addResultBean.getStatus())) {
				System.out.println("Add product success! user=" + userInfo.getUserName());
				return;
			} else {
				System.out.println("Add product failture! user=" + userInfo.getUserName());
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			add();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
