package com.coinstation.coinapi.service;
import com.coinstation.coinapi.CoinApiConstants;
import com.coinstation.coinapi.config.CommonValues;
import com.coinstation.coinapi.vo.CoinNickVo;
import org.apache.http.client.methods.HttpGet;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TestService {
	
	private static final Logger logger = LoggerFactory.getLogger(TestService.class);

	@Autowired
	private CommonValues commonValues;

	@Autowired
	private CommonService commonService;

	@Autowired
	@Qualifier(CoinApiConstants.DB_SOURCE_COIN_STATION)
	private SqlSession SqlSession;

	/**
	 * 코인 닉네임 삽입
	 * @param vo 코인방이름, 코인별명이 중복으로 들어갈 경우 삽입되지 않음
	 * @return
	 * @throws IOException
	 */
	public String insertCoinNick(CoinNickVo vo) throws IOException {

		String result = "success";

		try{
			//방 이름, 닉네임이 일치하지 않으면 1 / 중복되면 0으로 결과값이 나옴
			if(SqlSession.insert("coin_nick.insertCoinNick", vo) < 1){
				result = "dup"; // 닉네임 중복
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	/**
	 * 코인 닉네임 삽입(벌크용)
	 * @param
	 */
	public String insertCoinNick_test(CoinNickVo vo) throws IOException {
		HttpGet http = new HttpGet(CoinApiConstants.API_COIN_MARKET_CAP_LIST);

		String result = "SUCCESS";
		String dataStr = "";
		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();

		List<Map<String,Object>> map = (List<Map<String,Object>>)commonService.jsonToMap(dataStr);

		SqlSession.insert("coin_nick.insertCoinNick");

		return result;
	}

	/**
	 * 코인 가격 조회
	 * @param
	 */
//	public String insertCoinNick(){
//		HttpGet http = new HttpGet(CoinApiConstants.API_COIN_MARKET_CAP_LIST);
//
//		String result = "SUCCESS";
//
//		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();
//		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
//			response.getStatusLine().getStatusCode();
//			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//
//			Map<String,Object> map = commonService.jsonToMap(dataStr);
//			cMapList = (List<Map<String,Object>>)map.get("data");
//		} catch(Exception e) {
//			logger.error("error", e);
//			result = "fail";
//		}
//
//		return result;
//	}

}
