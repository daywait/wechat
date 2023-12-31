package com.tencent.wxcloudrun.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RestUtil
 */
public class RestUtil {

    /**
     * RestAPI 调用器
     */
    private final static RestTemplate RT = new RestTemplate();

    public static RestTemplate getRestTemplate() {
        return RT;
    }

    /**
     * 发送 get 请求
     */
    public static JSONObject get(String url) {
        return getNative(url, null, null).getBody();
    }

    /**
     * 发送 get 请求
     */
    public static JSONObject get(String url, JSONObject variables) {
        return getNative(url, variables, null).getBody();
    }

    /**
     * 发送 Post application/json请求
     */
    public static JSONObject getJson(String url, HttpHeaders headers) {
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        return rt.exchange(url, HttpMethod.GET, entity, JSONObject.class).getBody();
    }

    /**
     * 发送 get 请求
     */
    public static JSONObject get(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return getNative(url, variables, params).getBody();
    }

    /**
     * 发送 get 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> getNative(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return request(url, HttpMethod.GET, variables, params);
    }

    /**
     * 发送 Post 请求
     */
    public static JSONObject post(String url) {
        return postNative(url, null, null).getBody();
    }

    /**
     * 发送 Post 请求
     */
    public static JSONObject post(String url, MultiValueMap<String, Object> params) {
        return postNative(url, null, params).getBody();
    }

    /**
     * 发送 Post 请求
     */
    public static JSONObject post(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return postNative(url, variables, params).getBody();
    }

    /**
     * 发送 Post application/json请求
     */
    public static JSONObject postJson(String url, JSONObject variables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(variables.toString(), headers);

        return RT.exchange(url, HttpMethod.POST, entity, JSONObject.class).getBody();
    }

    /**
     * 发送 Post application/json请求
     */
    public static JSONObject postJson(String url, JSONObject variables, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(variables.toString(), headers);
        return RT.exchange(url, HttpMethod.POST, entity, JSONObject.class).getBody();
    }

    public static JSONObject getJson(String url, JSONObject variables, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(variables.toString(), headers);
        return RT.exchange(url, HttpMethod.GET, entity, JSONObject.class).getBody();
    }

    /**
     * 发送 POST 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> postNative(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return request(url, HttpMethod.POST, variables, params);
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url) {
        return putNative(url, null, null).getBody();
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url, MultiValueMap<String, Object> params) {
        return putNative(url, null, params).getBody();
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return putNative(url, variables, params).getBody();
    }

    /**
     * 发送 put 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> putNative(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return request(url, HttpMethod.PUT, variables, params);
    }

    /**
     * 发送 delete 请求
     */
    public static JSONObject delete(String url) {
        return deleteNative(url, null, null).getBody();
    }

    /**
     * 发送 delete 请求
     */
    public static JSONObject delete(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return deleteNative(url, variables, params).getBody();
    }

    /**
     * 发送 delete 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> deleteNative(String url, JSONObject variables, MultiValueMap<String, Object> params) {
        return request(url, HttpMethod.DELETE, null, variables, params, JSONObject.class);
    }

    /**
     * 发送请求
     */
    public static ResponseEntity<JSONObject> request(String url, HttpMethod method, JSONObject variables,
                                                     MultiValueMap<String, Object> params) {
        return request(url, method, getHeaderApplicationJson(), variables, params, JSONObject.class);
    }

    /**
     * 发送请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param headers      请求头  可空
     * @param variables    请求url参数 可空
     * @param params       请求body参数 可空
     * @param responseType 返回类型
     * @return ResponseEntity<responseType>
     */
    public static <T> ResponseEntity<T> request(String url, HttpMethod method, HttpHeaders headers,
                                                JSONObject variables, MultiValueMap<String, Object> params, Class<T> responseType) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url 不能为空");
        }
        if (method == null) {
            throw new RuntimeException("method 不能为空");
        }
        if (headers == null) {
            headers = new HttpHeaders();
        }
        // 拼接 url 参数
        if (variables != null) {
            url += ("?" + asUrlVariables(variables));
        }

        // 发送请求
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        return RT.exchange(url, method, request, responseType);
    }

    /**
     * 获取JSON请求头
     */
    private static HttpHeaders getHeaderApplicationJson() {
        return getHeader(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * 获取请求头
     */
    private static HttpHeaders getHeader(String mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mediaType));
        headers.add("Accept", mediaType);
        return headers;
    }

    /**
     * 将 JSONObject 转为 a=1&b=2&c=3...&n=n 的形式
     */
    public static String asUrlVariables(JSONObject variables) {
        Map<String, Object> source = variables.getInnerMap();
        Iterator<String> it = source.keySet().iterator();
        StringBuilder urlVariables = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            String value = "";
            Object object = source.get(key);
            if (object != null) {
                if (!StringUtils.isEmpty(object.toString())) {
                    value = object.toString();
                }
            }
            urlVariables.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&
        return urlVariables.substring(1);
    }

    public static class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WxMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持
            setSupportedMediaTypes(mediaTypes);// tag6
        }
    }


    /**
     * 发送请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param headers      请求头  可空
     * @param variables    请求url参数 可空
     * @param params       请求body参数 可空
     * @param responseType 返回类型
     * @return ResponseEntity<responseType>
     */
    public static <T> ResponseEntity<T> request(String url, HttpMethod method, HttpHeaders headers,
                                                JSONObject variables, String params, Class<T> responseType) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url 不能为空");
        }
        if (method == null) {
            throw new RuntimeException("method 不能为空");
        }
        if (headers == null) {
            headers = new HttpHeaders();
        }
        // 拼接 url 参数
        if (variables != null) {
            url += ("?" + asUrlVariables(variables));
        }

        // 发送请求

        HttpEntity<String> request = new HttpEntity<>(params, headers);
        RT.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return RT.exchange(url, method, request, responseType);
    }
}
