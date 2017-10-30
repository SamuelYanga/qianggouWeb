package com.web.nike.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonUtil {

	private static final ObjectMapper objectMapper;
	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String getPropertiesFromJson(String str, String[] keys) {
		String allKeys = Arrays.toString(keys);
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(str);
			JsonNode node = parser.readValueAsTree();
			for (String key : keys) {
				if (node.has(key)) {
					node = node.findValue(key);
				} else {
					throw new NikeShoppingRuntimeException("key not exist in json str, keys:" + allKeys);
				}
			}
			return node.toString();
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error, json=" + str + ", keys:" + allKeys, e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error, json=" + str + ", keys:" + allKeys, e);
		}
	}

	public static <T> T getObjectFromJson(InputStream instream, Class<T> cls) {
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(instream);
			T t = objectMapper.readValue(parser, cls);
			return t;
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error", e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error", e);
		} finally {
			try {
				instream.close();
			} catch (Exception ignore) {

			}
		}
	}

	public static <T> T getObjectFromJson(String str, Class<T> cls) {
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(str);
			T t = objectMapper.readValue(parser, cls);
			return t;
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error, json=" + str + ", class=" + cls.getName(), e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error, json=" + str + ", class=" + cls.getName(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static String getValueByFieldFromJson(String json, String field) {
		Map<String, String> mapValue = getObjectFromJson(json, HashMap.class);
		return mapValue.get(field);
	}

	public static String getJsonFromObject(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new NikeShoppingRuntimeException("get json error", e);
		} catch (JsonMappingException e) {
			throw new NikeShoppingRuntimeException("get json error", e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("get json error", e);
		}
	}

	public static <T> List<T> parserJsonList(InputStream instream, Class<T> clsT) {
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(instream);

			JsonNode nodes = parser.readValueAsTree();

			List<T> list = new LinkedList<T>();
			for (JsonNode node : nodes) {
				list.add(objectMapper.readValue(node, clsT));
			}
			return list;
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error", e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error", e);
		} finally {
			try {
				instream.close();
			} catch (Exception ignore) {

			}
		}
	}

	public static <T> List<T> parserJsonList(String str, Class<T> clsT) {
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(str);

			JsonNode nodes = parser.readValueAsTree();
			List<T> list = new LinkedList<T>();
			for (JsonNode node : nodes) {
				list.add(objectMapper.readValue(node, clsT));
			}
			return list;
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error str:" + str, e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error str:" + str, e);
		}
	}

	public static <T> LinkedHashMap<String, T> parserJsonMap(String str, Class<T> clsT) {
		LinkedHashMap<String, T> map = new LinkedHashMap<String, T>();
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(str);

			JsonToken current;

			current = parser.nextToken();
			if (current != JsonToken.START_OBJECT) {
				throw new NikeShoppingRuntimeException("parse json error: root should be object, quiting.");
			}

			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String fieldName = parser.getCurrentName();
				current = parser.nextToken();
				T obj = parser.readValueAs(clsT);
				map.put(fieldName, obj);
			}

			return map;
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error str:" + str, e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error str:" + str, e);
		}
	}

	public static <T extends Enum<T>> EnumSet<T> parserJsonEnum(String str, Class<T> clsT) {
		try {
			JsonParser parser = objectMapper.getJsonFactory().createJsonParser(str);

			JsonNode nodes = parser.readValueAsTree();

			EnumSet<T> enumSet = EnumSet.noneOf(clsT);
			for (JsonNode node : nodes) {
				enumSet.add(objectMapper.readValue(node, clsT));
			}
			return enumSet;
		} catch (JsonParseException e) {
			throw new NikeShoppingRuntimeException("parse json error str:" + str, e);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("parse json error str:" + str, e);
		}
	}

	/**
	 * 当格式不对，抛出异常422
	 * 
	 * @param request
	 * @param cls
	 * @return
	 */
	public static Object jsonConvert(HttpServletRequest request, Class<?> cls) {
		try {
			return getObjectFromJson(request.getInputStream(), cls);
		} catch (IOException e) {
			throw new NikeShoppingRuntimeException("json io error", e);
		} catch (Exception e) {
			throw new NikeShoppingRuntimeException("json error", e);
		}
	}
}
