package com.coinstation.coinapi;

public class CoinApiConstants {
	private CoinApiConstants() {
		throw new IllegalStateException("CoinApiConstants class");
	}

	public static final String DB_SOURCE_COIN_STATION = "jdbcCoinStation";

	public static final String API_COIN_MARKET_CAP_HEADER_API_KEY = "X-CMC_PRO_API_KEY";

	public static final String API_COIN_MARKET_CAP_HEADER_API_KEY_VALUE = "ba24a4e2-37f8-4c5a-801a-645117e63463";

	/* 코인 마켓캡 요청 url */
	public static final String API_COIN_MARKET_CAP_LIST = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=5000";

	/* 업비트 코인 가격 요청 url */
	public static final String API_UPBIT_COIN_PRICE = "https://api.upbit.com/v1/ticker?markets=KRW-";

	/* 코인원 코인 가격 요청 url */
	public static final String API_COINONE_COIN_PRICE = "https://api.coinone.co.kr/ticker?currency=";

	/* 빗썸 코인 가격 요청 url */
	public static final String API_BITHUMB_COIN_PRICE = "https://api.bithumb.com/public/ticker/";

	/* 바이낸스 코인 가격 요청 url */
	public static final String API_BINANCE_COIN_PRICE = "https://api.binance.com/api/v3/ticker/24hr?symbol=";

	/* 고팍스 코인 가격 요청 url */
	public static final String API_GOPAX_COIN_PRICE = "https://api.gopax.co.kr/trading-pairs/";
}