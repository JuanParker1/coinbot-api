package com.coinstation.coinapi;

public class CoinApiConstants {

	private CoinApiConstants() {
		throw new IllegalStateException("CoinApiConstants class");
	}

	public static final String DB_SOURCE_COIN_STATION = "jdbcCoinStation";

	/* 업비트 코인 가격 요청 url */
	public static final String API_UPBIT_COIN_PRICE = "https://api.upbit.com/v1/ticker?markets=KRW-";
	/* 업비트 거래소 이미지 */
	public static final String API_UPBIT_EXCHANGE_IMAGE = "https://play-lh.googleusercontent.com/VTIDerMHf0AAIXpehI3320SE0uTNhDEptB7ZqbP7JZA9VgCajBRJ4AUw84c2lZOyIlw";


	/* 코인원 코인 가격 요청 url */
	public static final String API_COINONE_COIN_PRICE = "https://api.coinone.co.kr/ticker_utc?currency=";
	/* 코인원 거래소 이미지 */
	public static final String API_COINONE_EXCHANGE_IMAGE = "https://play-lh.googleusercontent.com/GNxlvzso-hVQykg_jis0-0oBLc57z7qENvyDbIPbzqmPn5yyDahQI5eKF-stFqrQ5g";


	/* 빗썸 코인 가격 요청 url */
	public static final String API_BITHUMB_COIN_PRICE = "https://api.bithumb.com/public/ticker/";
	/* 빗썸 거래소 이미지 */
	public static final String API_BITHUMB_EXCHANGE_IMAGE = "https://play-lh.googleusercontent.com/_gGIl1OGnFwwfFtTej3wtt-J-2oyz-XkQAyhVWho7mJffyZV_J4DYhXhyyYxB7oMEx-q";


	/* 바이낸스 코인 가격 요청 url */
	public static final String API_BINANCE_COIN_PRICE = "https://api.binance.com/api/v3/ticker/24hr?symbol=";
	/* 바이낸스 거래소 이미지 */
	public static final String API_BINANCE_EXCHANGE_IMAGE = "https://play-lh.googleusercontent.com/YaJVsuv4cxsegY_VYcsWpKY-4nt7g2il77XVWZrm_z9Knd3PJAGaBlBuQyahlm85aic";

	/* 고팍스 코인 가격 요청 url */
	public static final String API_GOPAX_COIN_PRICE = "https://api.gopax.co.kr/trading-pairs/";


	/* FTX 코인 가격 요청 url */
	public static final String API_FTX_COIN_PRICE = "https://ftx.com/api/markets/";
	/* FTX API header key set*/
	public static final String API_FTX_HEADER_API_KEY1 = "FTX-KEY";
	public static final String API_FTX_HEADER_API_KEY_VALUE1 = "0Zt97x-PvRM3wLTwG-qavAudgN3DwXYz-mjDLbHy";
	public static final String API_FTX_HEADER_API_KEY2 = "FTX-SIGN";
	public static final String API_FTX_HEADER_API_KEY_VALUE2 = "Jy66l_eDaMzgmsr5Ch-BHas9sVPNrBt9IaavZf6N";
	/* FTX 거래소 이미지 */
	public static final String API_FTX_EXCHANGE_IMAGE = "https://miro.medium.com/max/3150/1*e2Hw8b74a3wR-PbQyPq4ug.jpeg";

	/* 코인베이스 거래소 이미지 */
	public static final String API_COINBASE_EXCHANGE_IMAGE = "https://play-lh.googleusercontent.com/PjoJoG27miSglVBXoXrxBSLveV6e3EeBPpNY55aiUUBM9Q1RCETKCOqdOkX2ZydqVf0";
}