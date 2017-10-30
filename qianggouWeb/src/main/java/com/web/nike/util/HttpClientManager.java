package com.web.nike.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientManager {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientManager.class);

	private PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
	private DefaultHttpClient httpClient;

	private static final String defaultCharsetStr = "UTF-8";
	private static final Charset defaultCharset = Charset.forName(defaultCharsetStr);
	private static final String JsonContentType = "application/json; charset=UTF-8";

	private static final Integer BUFFER_SIZE = 4048;

	private ScheduledExecutorService idleConnectionCloseExecutor = Executors.newSingleThreadScheduledExecutor();

	public HttpClientManager(int connectionTimeOut, int soTimeOut) {
		HttpParams params = new SyncBasicHttpParams();
		DefaultHttpClient.setDefaultHttpParams(params);
		HttpConnectionParamBean paramsBean = new HttpConnectionParamBean(params);
		paramsBean.setConnectionTimeout(connectionTimeOut);
		paramsBean.setSoTimeout(soTimeOut);

		httpClient = new DefaultHttpClient(cm, params);

		try {

			X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
				}

				public void verify(String arg0, X509Certificate arg1) throws SSLException {
				}

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}
			};

			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, hostnameVerifier);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
		} catch (Exception e) {
			e.printStackTrace();
		}

		httpClient.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepalive = super.getKeepAliveDuration(response, context);
				if (keepalive == -1) {
					keepalive = 5000;
				}
				return keepalive;
			}
		});
		httpClient.setReuseStrategy(new DefaultConnectionReuseStrategy() {
			@Override
			public boolean keepAlive(final HttpResponse response, final HttpContext context) {
				boolean keekAlive = false;
				if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
					keekAlive = super.keepAlive(response, context);
				}
				return keekAlive;
			}
		});

		java.security.Security.setProperty("networkaddress.cache.ttl", "10");
	}

	public void closeIdleStart() {
		idleConnectionCloseExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				logger.trace("start to close expire and idle connections");
				cm.closeExpiredConnections();
				cm.closeIdleConnections(30, TimeUnit.SECONDS);
			}
		}, 30, 30, TimeUnit.SECONDS);
		logger.info("HttpManager close idel every 30 seconds");
	}

	public void setMaxTotal(int maxTotal) {
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(maxTotal);
	}

	public void destory() {
		logger.trace("destory");
		idleConnectionCloseExecutor.shutdown();
		cm.shutdown();
	}

	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	public static String getDefaultcharsetstr() {
		return defaultCharsetStr;
	}

	public static Charset getDefaultcharset() {
		return defaultCharset;
	}

	public static Integer getBufferSize() {
		return BUFFER_SIZE;
	}

	public Map<String, Object> execHttpGet(URI uri, Map<String, String> headers, String cookie, String ip,
			String port_, String username, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = "";
		Map<String, String> setCookies = new HashMap<String, String>();

		HttpResponse response;
		HttpGet request = new HttpGet(uri);

//		if (!StringUtil.isEmpty(ip) && port != null) {
//			// HttpHost proxyHost = new HttpHost("127.0.0.1", 8888);
//			HttpHost proxyHost = new HttpHost(ip, port);
//			request.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
//		}

		if (!StringUtil.isEmpty(ip) && port_ != null) {
			int port = Integer.parseInt(port_);
			HttpHost proxyHost = new HttpHost(ip, port);

			if (!StringUtil.isEmpty(username)) {
				httpClient.getCredentialsProvider().setCredentials(
						new AuthScope(ip, port),
						new UsernamePasswordCredentials(username, password));
			}

			request.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
		}

		if (!StringUtil.isEmpty(cookie)) {
			request.addHeader(new BasicHeader("Cookie", cookie));
		}

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}

		httpClient.getCookieStore().clear();
		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				setCookies = getCookie(response);
				HttpEntity entity = response.getEntity();
				String charset = "UTF-8";
				if (entity != null) {

					Header[] resHeas = response.getHeaders("Content-Encoding");
					boolean isGzip = false;
					if (resHeas != null && resHeas.length > 0) {
						for (Header resHea : resHeas) {
							if (resHea.toString().indexOf("gzip") > 0) {
								isGzip = true;
								break;
							}
						}
					}

					if (isGzip) {
						InputStream is = entity.getContent();
						is = new GZIPInputStream(is);
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line = null;
						StringBuffer sb = new StringBuffer();
						while ((line = br.readLine()) != null) {
							if (!StringUtil.isEmpty(line.trim())) {
								sb.append(line + "\n");
							}
						}
						result = sb.toString();
					} else {
						charset = getContentCharSet(entity);
						result = EntityUtils.toString(entity, charset);
					}
				}

				resultMap.put("cookie", setCookies);
				resultMap.put("json", result);
			} else {
				System.out.println("http GET error.");
				HttpEntity entity = response.getEntity();
				String charset = "UTF-8";
				if (entity != null) {

					Header[] resHeas = response.getHeaders("Content-Encoding");
					boolean isGzip = false;
					if (resHeas != null && resHeas.length > 0) {
						for (Header resHea : resHeas) {
							if (resHea.toString().indexOf("gzip") > 0) {
								isGzip = true;
								break;
							}
						}
					}

					if (isGzip) {
						InputStream is = entity.getContent();
						is = new GZIPInputStream(is);
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line = null;
						StringBuffer sb = new StringBuffer();
						while ((line = br.readLine()) != null) {
							if (!StringUtil.isEmpty(line.trim())) {
								sb.append(line + "\n");
							}
						}
						result = sb.toString();
					} else {
						charset = getContentCharSet(entity);
						result = EntityUtils.toString(entity, charset);
					}
				}

				throw new RuntimeException("http get error. url: " + uri + result);
			}

			return resultMap;
		} catch (IOException e) {
			throw new HttpConnectionException("http get error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new NikeShoppingRuntimeException("http get error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	public Map<String, Object> execHttpPost(URI uri, Map<String, Object> map, Map<String, String> headers,
			String cookie, String ip, String port_, String username, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = "";
		Map<String, String> setCookies = new HashMap<String, String>();

		HttpPost request = new HttpPost(uri);
		addContentType(request, JsonContentType);

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}

		if (cookie != null) {
			request.addHeader(new BasicHeader("Cookie", cookie));
		}

		String json = JsonUtil.getJsonFromObject(map);
		try {
			StringEntity myEntity = new StringEntity(json, defaultCharsetStr);
			request.setEntity(myEntity);

			if (!StringUtil.isEmpty(ip) && port_ != null) {
				int port = Integer.parseInt(port_);
				HttpHost proxyHost = new HttpHost(ip, port);
//				request.get
				if (!StringUtil.isEmpty(username)) {
					httpClient.getCredentialsProvider().setCredentials(
							new AuthScope(ip, port),
							new UsernamePasswordCredentials(username, password));
				}
				request.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
			}

			HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				setCookies = getCookie(response);
				HttpEntity entity = response.getEntity();
				String charset = "UTF-8";
				if (entity != null) {
					charset = getContentCharSet(entity);
					result = EntityUtils.toString(entity, charset);
				}

				resultMap.put("cookie", setCookies);
				resultMap.put("json", result);
			} else {
				HttpEntity entity = response.getEntity();
				String charset = "UTF-8";
				String reason = null;
				if (entity != null) {

					Header[] resHeas = response.getHeaders("Content-Encoding");
					boolean isGzip = false;
					if (resHeas != null && resHeas.length > 0) {
						for (Header resHea : resHeas) {
							if (resHea.toString().indexOf("gzip") > 0) {
								isGzip = true;
								break;
							}
						}
					}

					if (isGzip) {
						InputStream is = entity.getContent();
						is = new GZIPInputStream(is);
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line = null;
						StringBuffer sb = new StringBuffer();
						while ((line = br.readLine()) != null) {
							if (!StringUtil.isEmpty(line.trim())) {
								sb.append(line + "\n");
							}
						}
						result = sb.toString();
					} else {
						charset = getContentCharSet(entity);
						result = EntityUtils.toString(entity, charset);
					}
				}
				throw new NikeShoppingRuntimeException("http get error. url: " + reason);
			}

			return resultMap;

		} catch (IOException e) {
			throw new HttpConnectionException("http post error. url: " + uri, e);
		} catch (RuntimeException e) {
			throw new NikeShoppingRuntimeException("http post error. url: " + uri, e);
		} finally {
			request.abort();
		}
	}

	private void addContentType(HttpRequestBase request, String contentType) {
		request.addHeader("Content-Type", contentType);
	}

	public Map<String, String> getCookie(HttpResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		Header[] headers = response.getHeaders("Set-Cookie");
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				String cookieStr = header.getValue();
				String[] cookiesStr = cookieStr.split(";");
				for (String str : cookiesStr) {
					int index0 = str.indexOf("=");
					if (index0 > 0) {
						String key = str.substring(0, index0);
						String value = str.substring(index0 + 1);
						map.put(key, value);
					}
					
					
//					String[] strs = str.split("=");
//					if (strs.length == 2) {
//						String key = strs[0].trim();
//						String value = strs[1].trim();
//						map.put(key, value);
//					}
				}
			}
		}
		return map;
	}

	public String getResult(HttpResponse response) throws ParseException, IOException {
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, defaultCharsetStr);
	}

	public <T> T getResultObject(HttpResponse response, Class<T> cls) {
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				T t = JsonUtil.getObjectFromJson(instream, cls);
				return t;
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new HttpConnectionException("", e);
		} catch (RuntimeException e) {
			throw new NikeShoppingRuntimeException("", e);
		}
	}

	public static String getContentCharSet(final HttpEntity entity) throws ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		String charset = null;
		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}

		if (StringUtil.isEmpty(charset)) {
			charset = "UTF-8";
		}
		return charset;
	}

}
