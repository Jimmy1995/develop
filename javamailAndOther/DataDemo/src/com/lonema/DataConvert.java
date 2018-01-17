package com.lonema;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.BeanMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @DESC 数据类型转换工具类
 * @AUTHOR JIANGCW
 * @DATE 2018-1-16上午10:01:27
 */
public class DataConvert {
	
	
	/**
	 * 
	 * @DESC 使用fastjson将对象转成json字符串
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午05:33:42
	 */
	public static String toJsonString(Object obj){
		String jsonString = JSON.toJSONString(obj);
		return jsonString;
	}
	
	/**
	 * 
	 * @DESC 使用fastjson将json字符串转成对象
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午05:35:08
	 */
	public static Object jsonStringToObj(String jsonString){
		Object obj = JSON.parseObject(jsonString);
		//JSONObject jsonObj = (JSONObject) JSONObject.parse(jsonString);
		return obj;
	}
	
	/**
	 * 
	 * @DESC 使用 org.apache.commons.beanutils.BeanUtils将map中的数据转换至对象相应的属性上
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午05:37:38
	 */
	public static Object mapToObj(Map map){
		Object bean = new Object();
		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 
	 * @DESC 将bean对象转换为Map对象(map中可能会有些属性是不需要的)
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午05:40:06
	 */
	public static Map objToMap(Object obj){
		BeanMap beanMap = new BeanMap(obj);
		return beanMap;
	}
	
	
	/**
	 * 
	 * @DESC Bean 转换成map
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-11下午05:06:32
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean(Object bean) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					// returnMap.put(propertyName, "");
				}
			}
		}
		returnMap.remove("empty");
		return returnMap;
	}
	
	/**
	 * 
	 * @DESC JSON 字符串 与 java 对象的转换
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午05:49:44
	 */
	public static Object convertObj(){
		/*
		1. 把java 对象列表转换为json对象数组，并转为字符串
		JSONArray array = JSONArray.fromObject(list);
		String jsonstr = array.toString();

		2. 把java对象转换成json对象，并转化为字符串
		JSONObject object = JSONObject.fromObject(user);
		Log4jInit.ysulogger.debug(object.toString());

		3.把JSON字符串转换为JAVA 对象数组
		JSONArray json = JSONArray.fromObject(userStr);//userStr是json字符串
		List<User> users= (List<User>)JSONArray.toCollection(json, User.class);

		4.把JSON字符串转换为JAVA 对象
		JSONObject jsonobject = JSONObject.fromObject(jsonStr);
		User user= (User)JSONObject.toBean(object,User.class);
		*/
		return null;
	}

	
	
	/**
	 * 
	 * @DESC Gsonc与数组类型之间的类型转换
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午07:03:55
	 */
	public static void GsonAndArray(){
		Gson gson = new Gson();
		int[] ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };  
		String[] strings= { "Onw", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };  
		String intsJson = gson.toJson(ints);  
		String stringsJson = gson.toJson(strings);  
		
		System.out.println("intsJson --->>> " + intsJson);  
		System.out.println("stringsJson --->>> " + stringsJson);
	}
	
	/**
	 * 
	 * @DESC 对象转json字符串
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午07:10:49
	 */
	public static void GsonAndBean(){
		Gson gson = new Gson();
		Person person = new Person(22, "小明");
		String json = gson.toJson(person);
		System.out.println(json);
	}
	
	/**
	 * 
	 * @DESC list、map转json字符串之间的互转
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-16下午07:12:26
	 */
	public static void GsonAndList(){
		List<Person> list = new ArrayList<Person>();  
        for(int i = 0 ; i < 5 ; i++){  
            list.add(new Person(i, "Hong" + i + i));  
        }
        Gson gson = new Gson();
        String listJson = gson.toJson(list);  
        System.out.println("List >>--" + listJson );  
        
        //json字符串转list集合
        Type type = new TypeToken<List<Person>>(){}.getType();  
        List<Person> personList = gson.fromJson(listJson , type);  
        for (Person person : personList) {  
            System.out.println("person age  = " + person.getAge());  
        }  
        
        //map-----------
        Map<String, String> colours = new HashMap<String, String>();  
        colours.put("BLACK", "#000000");  
        colours.put("RED", "#FF0000");  
        colours.put("GREEN", "#008000");  
        String mapJson = gson.toJson(colours);  
        System.out.println("MapJson = " + mapJson);
        
        //json字符串转换为list集合
        Type mapType = new TypeToken<Map<String, String>>(){}.getType();  
        Map<String, String> map = gson.fromJson(mapJson, mapType);  
        for (String key : map.keySet()) {  
            System.out.println("map.get = " + map.get(key));  
        }
	}
	
	public static void main(String[] args) {
		GsonAndList();
	}
	
	/*
	public static String getIP(HttpServletRequest request){
		 String ip = request.getHeader("x-forwarded-for");
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getHeader("Proxy-Client-IP");
		  }
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getHeader("WL-Proxy-Client-IP");
		  }
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getRemoteAddr();
		  }
		  return ip;
	}
	
	public static boolean isAJAXRequest(HttpServletRequest request) {
		if (request == null) {
			return false;
		}
		String header = request.getHeader("x-requested-with");
		if (StringUtils.isEmpty(header)) {
			return false;
		}
		return "XMLHttpRequest".equalsIgnoreCase(header.trim());
	}
	
	public static boolean isWxPay(HttpServletRequest request){
		boolean validation=false;
		String ua = request.getHeader("user-agent")  
		        .toLowerCase();  
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器  
		    validation = true;  
		}  
		return validation;
	}
	
	public static boolean isAliPay(HttpServletRequest request){
		boolean validation=false;
		String ua = request.getHeader("user-agent")  
		        .toLowerCase();  
		if (ua.indexOf("alipayclient") > 0) {// 是支付宝浏览器  
		    validation = true;  
		}  
		return validation;
	}
	*/
	
	
}
