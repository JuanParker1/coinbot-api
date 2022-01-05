package com.coinstation.coinapi.service;
import com.coinstation.coinapi.CoinApiConstants;
import com.coinstation.coinapi.config.CommonValues;
import com.coinstation.coinapi.vo.CoinNickVo;
import com.coinstation.coinapi.vo.CoinPriceResponseVo;
import com.coinstation.coinapi.vo.ExchangesVo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class CoinService {
	
	private static final Logger logger = LoggerFactory.getLogger(CoinService.class);

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

	/**
	 * 코인 거래소리스트를 순서와 가져오고 가격 api url을 가져옴
	 * @return
	 */
	public List<ExchangesVo> getExchangeList() {
		return sqlSession.selectList("coin_exchange.getExchanges");
	}

	/**
	 * 바이낸스의 가격 정보를 가져옴
	 */
	public CoinPriceResponseVo getBinancePrice(String symbol){
		symbol = symbol.toUpperCase(Locale.ROOT);
		HttpGet http = new HttpGet(CoinApiConstants.API_BINANCE_COIN_PRICE + symbol + "USDT");
		commonService.setHeadersWithoutKey(http);

		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();
		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);
			DecimalFormat form = new DecimalFormat("###,###.####");
			String price = form.format( Float.parseFloat(map.get("lastPrice").toString()) );
			CoinPriceResponseVo result = new CoinPriceResponseVo("BINANCE", price, map.get("priceChangePercent").toString());
			logger.info("result : ", result);
			return result;
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}
}
