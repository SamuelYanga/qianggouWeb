package com.web.nike.add;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.web.nike.bean.HtmlProductData;
import com.web.nike.bean.HtmlSkuInfo;
import com.web.nike.bean.MainPageInfo;
import com.web.nike.bean.ParseProductBean;
import com.web.nike.bean.ProductPageInfo;
import com.web.nike.bean.UserLoginInfo;
import com.web.nike.util.JsonUtil;
import com.web.nike.util.PreData;
import com.web.nike.util.StringUtil;

public class OpenProduct {

	public static ParseProductBean productInfo;

	public static String productUrl;

	public OpenProduct() {

	}

	public OpenProduct(String url) {
		productUrl = url;
	}

	public boolean initProductPageInfo() {
		ProductPageInfo pageInfo = parseProductFromHtml();
		PreData.productPageInfo.copy(pageInfo);
		System.out.println("initProductPageInfo end.");
		return pageInfo.getOpenFlag();
	}

	public ProductPageInfo parseProductFromHtml() {
		ProductPageInfo productPageInfo = new ProductPageInfo();

		Document doc = null;

		try {
			doc = Jsoup.connect(productUrl).get();

			Element productDataScript = doc.getElementById("product-data");

			String json = productDataScript.html();
			HtmlProductData html1 = JsonUtil.getObjectFromJson(json, HtmlProductData.class);
			productPageInfo.setProductId(html1.getProductId());
			productPageInfo.setCatalogId(html1.getCatalogId());
			String price = html1.getRawPrice();
			if (price.indexOf(".") != -1) {
				price = price.substring(0, price.indexOf("."));
			}
			productPageInfo.setPrice(price);
			productPageInfo.setLine1(html1.getProductTitle());
			productPageInfo.setLine2(html1.getProductSubTitle());
			for (HtmlSkuInfo skuInfo : html1.getSkuContainer().getProductSkus()) {
				String size = skuInfo.getDisplaySize();
				String skuId = skuInfo.getId();
				productPageInfo.getSkuMap().put(size, skuId);
			}
			productPageInfo.setCutDownTime(Long.parseLong(html1.getStartDate()));
			productPageInfo.setOpenFlag(true);
		} catch (Exception e) {
			doc = null;
			return productPageInfo;
		}

		return productPageInfo;
	}
	
	
	
	public ParseProductBean parseProductHtml() {
		String pidAndPgid = getPidAndPgid(productUrl);

		Document doc = null;

		ParseProductBean producthtml = null;

		long t4 = System.currentTimeMillis();

		try {
			doc = Jsoup.connect(productUrl).get();
		} catch (Exception e) {
			doc = null;
			return null;
		}

		try {

			long t5 = System.currentTimeMillis();
			System.out.println("DOM装载时间:" + (t5 - t4));
			producthtml = new ParseProductBean();

			Element addButton = doc.getElementById("buyingtools-add-to-cart-button");
			if (addButton != null) {
				String addButtonClass = addButton.attr("class");
				if ("js-add-to-cart add-to-cart nsg-button nsg-grad--nike-orange".equals(addButtonClass)) {
					producthtml.setStartflag(true);
				}
			}

			String baseUrl = doc.baseUri();
			String pidAndPgid_ = getPidAndPgid(baseUrl);
			if (!pidAndPgid.equals(pidAndPgid_)) {
				System.out.println(producthtml.toString());
				return null;
			}
			producthtml.setProductEsitsflag(true);

			long t10 = System.currentTimeMillis();
			Elements addForm = doc.select("form.add-to-cart-form");
			long t11 = System.currentTimeMillis();
			System.out.println("selector form:" + (t11 - t10));

			Elements inputs = addForm.select("input");
			for (Element temp : inputs) {
				String nameStr = temp.attr("name");
				String valueStr = temp.attr("value");

				if (!StringUtil.isEmpty(nameStr)) {
					if ("action".equals(nameStr)) {
						producthtml.setAction(valueStr);
					} else if ("lang_locale".equals(nameStr)) {
						producthtml.setLang_locale(valueStr);
					} else if ("country".equals(nameStr)) {
						producthtml.setCountry(valueStr);
					} else if ("catalogId".equals(nameStr)) {
						producthtml.setCatalogId(valueStr);
					} else if ("productId".equals(nameStr)) {
						producthtml.setProductId(valueStr);
					} else if ("price".equals(nameStr)) {
						producthtml.setPrice(valueStr);
					} else if ("siteId".equals(nameStr)) {
						producthtml.setSiteId(valueStr);
					} else if ("line1".equals(nameStr)) {
						producthtml.setLine1(valueStr);
					} else if ("line2".equals(nameStr)) {
						producthtml.setLine2(valueStr);
					} else if ("passcode".equals(nameStr)) {
						producthtml.setPasscode(valueStr);
					} else if ("sizeType".equals(nameStr)) {
						producthtml.setSizeType(valueStr);
					}
				}
			}

			long t6 = System.currentTimeMillis();
			Elements skuSelect = addForm.select("select.two-column-dropdown");
			long t7 = System.currentTimeMillis();
			System.out.println("selector select:" + (t7 - t6));

			Elements skuOption = skuSelect.get(0).getElementsByTag("option");

			Map<String, String> skuMap = new HashMap<String, String>();
			for (Element temp : skuOption) {
				String valueStr = temp.attr("value");
				if (!StringUtil.isEmpty(valueStr)) {
					String[] strs = valueStr.split(":");
					skuMap.put(strs[1], strs[0]);
				}
			}
			if (skuMap.size() == 0) {
				return producthtml;
			}
			producthtml.setSkuIdEsitsFlag(true);
			producthtml.setSkuMap(skuMap);
			// producthtml.setSkuId(skuMap.get(size));
			// producthtml.setSize(size);

			Element productData = doc.getElementById("product-data");
			String productDataValue = productData.html();
			int index0 = productDataValue.indexOf("\"startDate\":");
			String startDate = productDataValue.substring(index0 + 12, index0 + 25);
			producthtml.setStartDate(startDate);
			long t12 = System.currentTimeMillis();
			System.out.println("time:" + (t12 - t4));
			System.out.println(producthtml.toString());
		} catch (Exception e) {
			return null;
		}

		return producthtml;
	}

	private String getPidAndPgid(String url) {
		int index = url.indexOf("pid-");
		return url.substring(index);
	}

	public static MainPageInfo openMainpage() {
		try {
			String url = "http://www.nike.com/cn/zh_cn/";
			Document doc;

			doc = Jsoup.connect(url).get();

			Element els = doc.getElementById("nike-unite");
			String uxid = els.attr("data-uxid");
			String locale = els.attr("data-locale");
			String clientid = els.attr("data-clientid");
			String backendEnvironment = els.attr("data-backendenvironment");
			String appVersion = els.attr("data-app-version");
			String experienceVersion = els.attr("data-experience-version");

			MainPageInfo mainPageInfo = new MainPageInfo();
			mainPageInfo.setAppVersion(appVersion);
			mainPageInfo.setExperienceVersion(experienceVersion);
			mainPageInfo.setClient_id(clientid);
			mainPageInfo.setUx_id(uxid);
			mainPageInfo.setUxid(uxid);
			mainPageInfo.setBackendEnvironment(backendEnvironment);
			mainPageInfo.setLocale(locale);
			mainPageInfo.setSetFlag(true);
			return mainPageInfo;
		} catch (IOException e) {
			return new MainPageInfo();
		}
	}

	public void initMainPage() {
		PreData.mainPageInfo.copy(openMainpage());
		System.out.println("initMainPage end.");
	}

	public static void initUserData() {
		UserLoginInfo info1 = new UserLoginInfo("240541655@qq.com", "Kkffcc123", "47.5", "127.0.0.1", "8888", "123456", "123456");
		UserLoginInfo info2 = new UserLoginInfo("1679016257@qq.com", "Kkffcc123", "47.5", "", "", "", "");
//		UserLoginInfo info3 = new UserLoginInfo("ass0118@163.com", "Liulu521", "44", "43.249.221.2", "8080", "admin", "admin");
//		UserLoginInfo info4 = new UserLoginInfo("ass0119@163.com", "Liulu521", "44", "43.249.221.3", "8080", "admin", "admin");
		UserLoginInfo info5 = new UserLoginInfo("ass0120@163.com", "Liulu521", "37.5", "43.249.221.4", "8080", "admin", "admin");
		UserLoginInfo info6 = new UserLoginInfo("ass0121@163.com", "Liulu521", "37.5", "43.249.221.5", "8080", "admin", "admin");
		UserLoginInfo info7 = new UserLoginInfo("ass0122@163.com", "Liulu521", "37.5", "43.249.221.6", "8080", "admin", "admin");
		UserLoginInfo info8 = new UserLoginInfo("ass0123@163.com", "Liulu521", "37.5", "43.249.221.7", "8080", "admin", "admin");
		UserLoginInfo info9 = new UserLoginInfo("ass0124@163.com", "Liulu521", "37.5", "43.249.221.8", "8080", "admin", "admin");
		UserLoginInfo info10 = new UserLoginInfo("ass0125@163.com", "Liulu521", "37.5", "43.239.165.83", "8080", "123456", "123456");
//		UserLoginInfo info11 = new UserLoginInfo("ass0126@163.com", "Liulu521", "45", "43.239.165.84", "8080", "123456", "123456");
//		UserLoginInfo info12 = new UserLoginInfo("ass0127@163.com", "Liulu521", "45", "43.239.165.85", "8080", "123456", "123456");
//		UserLoginInfo info13 = new UserLoginInfo("ass0128@163.com", "Liulu521", "45", "43.239.165.86", "8080", "123456", "123456");
//		UserLoginInfo info14 = new UserLoginInfo("ass0129@163.com", "Liulu521", "45", "43.239.165.87", "8080", "123456", "123456");
//		UserLoginInfo info15 = new UserLoginInfo("ass0130@163.com", "Liulu521", "45", "43.239.165.88", "8080", "123456", "123456");
//		UserLoginInfo info16 = new UserLoginInfo("ass0131@163.com", "Liulu521", "45", "43.239.165.89", "8080", "123456", "123456");
//		UserLoginInfo info17 = new UserLoginInfo("ass0132@163.com", "Liulu521", "45", "43.239.165.90", "8080", "123456", "123456");
//		UserLoginInfo info18 = new UserLoginInfo("ass0133@163.com", "Liulu521", "45", "43.239.165.93", "8080", "123456", "123456");
//		UserLoginInfo info19 = new UserLoginInfo("ass0134@163.com", "Liulu521", "45", "43.239.165.92", "8080", "123456", "123456");
//		UserLoginInfo info20 = new UserLoginInfo("ass0135@163.com", "Liulu521", "45", "43.239.165.91", "8080", "123456", "123456");
		PreData.addUserInfo(info1);
		PreData.addUserInfo(info2);
//		PreData.addUserInfo(info3);
//		PreData.addUserInfo(info4);
//		PreData.addUserInfo(info5);
//		PreData.addUserInfo(info6);
//		PreData.addUserInfo(info7);
//		PreData.addUserInfo(info8);
//		PreData.addUserInfo(info9);
//		PreData.addUserInfo(info10);
//		PreData.addUserInfo(info11);
//		PreData.addUserInfo(info12);
//		PreData.addUserInfo(info13);
//		PreData.addUserInfo(info14);
//		PreData.addUserInfo(info15);
//		PreData.addUserInfo(info16);
//		PreData.addUserInfo(info17);
//		PreData.addUserInfo(info18);
//		PreData.addUserInfo(info19);
//		PreData.addUserInfo(info20);
		System.out.println("initUserData end.");
	}
	public void init() {
		System.out.println("init start.");
		initUserData();
		initMainPage();
		initProductPageInfo();
//		System.out.println(PreData.mainPageInfo);
//		System.out.println(PreData.productPageInfo);
//		System.out.println(PreData.userMap);
		System.out.println("init end");
	}
	
	public void initPage() {
		initMainPage();
		initProductPageInfo();
	}

	
//	public static void main(String[] args) {
//		OpenProduct openProduct = new OpenProduct("http://store.nike.com/cn/zh_cn/pd/air-jordan-13-retro-%E5%A4%8D%E5%88%BB%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B/pid-11252850/pgid-11446368");
//		init();
//	
//	}
}
