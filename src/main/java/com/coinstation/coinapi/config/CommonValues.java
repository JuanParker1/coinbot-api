package com.coinstation.coinapi.config;

import com.coinstation.coinapi.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommonValues {	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private Environment env;	
	
	private static Map<String, String> DB_MAP = new HashMap<>();
	private static Map<String, String> PROP_MAP = new HashMap<>();
	
	public void removeCache() {
		DB_MAP.clear();
	}
	
	public void removeCacheForProp() {
		PROP_MAP.clear();
	}	

    /**
     * 프러퍼티 파일에서 정보를 읽는 경우
     * @param key
     * @return
     */
    public String getValue(String key) {
    	if (PROP_MAP.get(key) == null) {
        	String val = env.getProperty(key);
        	if (val != null) {
        		PROP_MAP.put(key, val);
        	}
        }
        return PROP_MAP.get(key);
    }

}