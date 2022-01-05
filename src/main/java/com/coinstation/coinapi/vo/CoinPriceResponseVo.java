package com.coinstation.coinapi.vo;

public class CoinPriceResponseVo {
    private String exchange;// 거래소명
    private String price;   // 가격
    private String percent; // 퍼센트

    public CoinPriceResponseVo(String exchange, String price, String percent) {
        this.exchange = exchange;
        this.price = price;
        this.percent = percent;
    }

    public CoinPriceResponseVo() {
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }


    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
