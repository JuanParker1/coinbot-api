package com.coinstation.coinapi.service;
import com.coinstation.coinapi.CoinApiConstants;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoinPriceService {
	
	private static final Logger logger = LoggerFactory.getLogger(CoinPriceService.class);

	@Autowired
	private CommonService commonService;

	@Autowired
    private Environment environment;
	
	@Autowired
	@Qualifier(CoinApiConstants.DB_SOURCE_COIN_STATION)
	private SqlSession SqlSession;

	/**
	 * 코인 정보 업데이트
	 * @param
	 */
	public void getCoinPrice(String Exchange, String symbol){

		HttpGet http = new HttpGet(CoinApiConstants.API_COIN_MARKET_CAP_LIST);
		commonService.setHeadersWithoutKey(http);

		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();
		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);
			cMapList = (List<Map<String,Object>>)map.get("data");
		} catch(Exception e) {
			logger.error("Cmc Coin List Get Request error", e);
		}
	}

	/**
	 * 코인 정보 업데이트
	 * @param
	 */
	public void updateCoinInfo(){
		HttpGet http = new HttpGet(CoinApiConstants.API_COIN_MARKET_CAP_LIST);
		commonService.setHeadersHasKey(http, CoinApiConstants.API_COIN_MARKET_CAP_HEADER_API_KEY, CoinApiConstants.API_COIN_MARKET_CAP_HEADER_API_KEY_VALUE);

		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();
		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);
			cMapList = (List<Map<String,Object>>)map.get("data");
		} catch(Exception e) {
			logger.error("Cmc Coin List Get Request error", e);
		}
	}

}
