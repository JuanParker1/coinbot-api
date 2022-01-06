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
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Autowired
	@Qualifier(CoinApiConstants.DB_SOURCE_COIN_STATION)
	private SqlSession sqlSession;

	/**
	 * 코인 별명 가져오기
	 * @param
	 */
	public String getCoinNick(String nick) {
		String result = sqlSession.selectOne("coin_nick.getCoinNick", nick);
		return result;
	}

	/**
	 * 코인 거래소리스트를 순서와 가져오고 가격 api url을 가져옴
	 * @return
	 */
	public List<ExchangesVo> getExchangeList() {
		return sqlSession.selectList("coin_exchange.getExchanges");
	}

	/**
	 * 바이낸스의 가격 정보
	 */
	public CoinPriceResponseVo getBinancePrice(String symbol){
		symbol = symbol.toUpperCase(Locale.ROOT);
		HttpGet http = new HttpGet(CoinApiConstants.API_BINANCE_COIN_PRICE + symbol + "USDT");
		commonService.setHeadersWithoutKey(http);

		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);

			CoinPriceResponseVo result;
			//코인이 없거나 오류가 있을 경우 code라는 키값이 존재
			if(map.get("code") == null){
				DecimalFormat form = new DecimalFormat("###,###.####");
				String price = form.format( Float.parseFloat(map.get("lastPrice").toString()) );
				DecimalFormat Percentform = new DecimalFormat("###,###.##");
				String percent = String.valueOf(Percentform.format(Double.parseDouble(map.get("priceChangePercent").toString()))) + " %";
				result = new CoinPriceResponseVo("BINANCE", price + " USDT", percent);
			}else{
				result = null;
			}
			logger.info("result : ", result);

			return result;
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}

	/**
	 * 업비트 가격 정보
	 * @param symbol
	 * @return
	 */
	public CoinPriceResponseVo getUpbitPrice(String symbol) {

		symbol = symbol.toUpperCase(Locale.ROOT);
		HttpGet http = new HttpGet(CoinApiConstants.API_UPBIT_COIN_PRICE + symbol);
		commonService.setHeadersWithoutKey(http);

		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			CoinPriceResponseVo result;
			//코인이 없거나 오류가 있을 경우 리스트가 아니라 바로 키값이 존재
			if(dataStr.startsWith("[")){
				Map<String,Object> map = commonService.jsonToListMap(dataStr).get(0);
				DecimalFormat Priceform = new DecimalFormat("###,###.####");
				String price = Priceform.format( Float.parseFloat(map.get("trade_price").toString()) );
				DecimalFormat Percentform = new DecimalFormat("###,###.##");
				String percent = String.valueOf( Percentform.format(Double.parseDouble(map.get("signed_change_rate").toString()) * 100) ) + " %";
				result = new CoinPriceResponseVo("UPBIT", price + " KRW", percent );
			}else{
				result = null;
			}
			logger.info("result : ", result);

			return result;
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}

	/**
	 * FTX 가격 정보
	 * @param symbol
	 * @return
	 */
	public CoinPriceResponseVo getFtxPrice(String symbol) {

		symbol = symbol.toUpperCase(Locale.ROOT);
		HttpGet http = new HttpGet(CoinApiConstants.API_FTX_COIN_PRICE + symbol + "/usd");
		commonService.setHeadersHasKey(http, CoinApiConstants.API_FTX_HEADER_API_KEY1, CoinApiConstants.API_FTX_HEADER_API_KEY_VALUE1);
		commonService.setHeadersHasKey(http, CoinApiConstants.API_FTX_HEADER_API_KEY2, CoinApiConstants.API_FTX_HEADER_API_KEY_VALUE2);

		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);

			CoinPriceResponseVo result;
			//success == true일 경우
			if((boolean)map.get("success")){
				Map<String,Object> m = (Map<String,Object>)map.get("result");
				DecimalFormat form = new DecimalFormat("###,###.####");
				String price = form.format( Float.parseFloat(m.get("price").toString()) );
				DecimalFormat Percentform = new DecimalFormat("###,###.##");
				String percent = String.valueOf( Percentform.format(Double.parseDouble(m.get("change24h").toString()) * 100) ) + " %";
				result = new CoinPriceResponseVo("FTX", price + " USD", percent );
			}else{
				result = null;
			}
			logger.info("result : ", result);

			return result;
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}

	public CoinPriceResponseVo getBithumbPrice(String symbol) {
		symbol = symbol.toUpperCase(Locale.ROOT);
		HttpGet http = new HttpGet(CoinApiConstants.API_BITHUMB_COIN_PRICE + "/" + symbol);
		commonService.setHeadersWithoutKey(http);

		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);

			CoinPriceResponseVo result;
			//status == 0000일 경우 조회성공
			if(map.get("status").toString().equals("0000")){
				Map<String,Object> m = (Map<String,Object>)map.get("data");
				DecimalFormat form = new DecimalFormat("###,###.####");
				String price = form.format( Float.parseFloat(m.get("closing_price").toString()) );

				Double o_price = Double.parseDouble(m.get("opening_price").toString());		// 시작가
				Double now_price = Double.parseDouble(m.get("closing_price").toString());	// 현재가

				Double calculPercent = ( o_price - now_price ) / o_price * 100;
				DecimalFormat Percentform = new DecimalFormat("###,###.##");
				String percent =  Percentform.format(calculPercent) + " %";

				result = new CoinPriceResponseVo("BITHUMB", price + " KRW", percent );
			}else{
				result = null;
			}
			logger.info("result : ", result);

			return result;
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}

	/**
	 * 코인원 가격 정보
	 */
	public CoinPriceResponseVo getCoinonePrice(String symbol){
		symbol = symbol.toUpperCase(Locale.ROOT);
		HttpGet http = new HttpGet(CoinApiConstants.API_COINONE_COIN_PRICE + symbol);
		commonService.setHeadersWithoutKey(http);

		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
			response.getStatusLine().getStatusCode();
			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Map<String,Object> map = commonService.jsonToMap(dataStr);

			CoinPriceResponseVo result;
			//코인이 없거나 오류가 있을 경우 currency가 최상단에 나오지 않음
			if(map.get("currency") != null){
				DecimalFormat form = new DecimalFormat("###,###.####");
				String price = form.format( Float.parseFloat(map.get("last").toString()) );

				Double o_price = Double.parseDouble(map.get("yesterday_last").toString());		// 시작가
				Double now_price = Double.parseDouble(map.get("last").toString());	// 현재가

				Double calculPercent = ( o_price - now_price ) / o_price * 100;
				DecimalFormat Percentform = new DecimalFormat("###,###.##");
				String percent =  Percentform.format(calculPercent) + " %";

				result = new CoinPriceResponseVo("COINONE", price + " KRW", percent);
			}else{
				result = null;
			}
			logger.info("result : ", result);

			return result;
		} catch(Exception e) {
			logger.error("error : ", e);
			return null;
		}
	}
}
