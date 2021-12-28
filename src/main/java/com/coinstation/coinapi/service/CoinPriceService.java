package com.coinstation.coinapi.service;
import com.coinstation.coinapi.CoinApiConstants;
import com.coinstation.coinapi.config.CommonValues;
import com.coinstation.coinapi.vo.CoinNickVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoinPriceService {
	
	private static final Logger logger = LoggerFactory.getLogger(CoinPriceService.class);

	@Autowired
	private CommonValues commonValues;

	@Autowired
	private CommonService commonService;

	private SqlSession sqlSession;

	/**
	 * 코인 별명 가져오기
	 * @param
	 */
	public CoinNickVo getCoinNick(String nick) {
		return sqlSession.selectOne("coin_nick.getCoinNick", nick);
	}

}
