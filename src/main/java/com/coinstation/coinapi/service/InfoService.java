package com.coinstation.coinapi.service;
import com.coinstation.coinapi.CoinApiConstants;
import com.coinstation.coinapi.config.CommonValues;
import com.coinstation.coinapi.vo.CoinInfoVo;
import com.coinstation.coinapi.vo.CoinPriceResponseVo;
import com.coinstation.coinapi.vo.ExchangesVo;
import com.coinstation.coinapi.vo.ForexVo;
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

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class InfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

	@Autowired
	private CommonValues commonValues;

	@Autowired
	private CommonService commonService;

	@Autowired
	@Qualifier(CoinApiConstants.DB_SOURCE_COIN_STATION)
	private SqlSession sqlSession;

	DecimalFormat form = new DecimalFormat("###,###.#");


	/**
	 * 환율정보
	 */
	public ForexVo getForexInfo(String currency){

		try{
			return sqlSession.selectOne("forex.getForex");
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}
}
