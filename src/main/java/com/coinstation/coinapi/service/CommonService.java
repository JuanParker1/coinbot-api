package com.coinstation.coinapi.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CommonService {

    @Autowired
    private SqlSessionTemplate sqlSession;
    /**
     *	API Key가 있는 GET 요청의 헤더 설정
     * @param http
     * @return
     */
    public HttpGet setHeadersHasKey(HttpGet http, String apikey, String apiKeysValue) {
        Header contentType1 = new BasicHeader("Content-Type", "application/json;charset=utf-8");
        if(apikey!=null){
            http.addHeader(new BasicHeader(apikey, apiKeysValue));
        }
        http.addHeader(contentType1);
        return http;
    }

    /**
     *	API Key가 없는 GET 요청의 헤더 설정
     * @param http
     * @return
     */
    public HttpGet setHeadersWithoutKey(HttpGet http) {
        Header contentType1 = new BasicHeader("Content-Type", "application/json;charset=utf-8");
        http.addHeader(contentType1);
        return http;
    }

    public static Map<String, Object> jsonToMap(String jsonData) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (Map)mapper.readValue(jsonData, Map.class);
    }

}
