package com.coinstation.coinapi.vo;

public class CoinPriceResponseVo {
    private String exchange;// 거래소명
    private String price;   // 가격
    private String percent; // 퍼센트
    private String excg_img; // 거래소 이미지 주소
    private String path;    // 누르면 이동할 주소

    public CoinPriceResponseVo(String exchange, String price, String percent, String excg_img, String path) {
        this.exchange = exchange;
        this.price = price;
        this.percent = percent;
        this.excg_img = excg_img;
        this.path = path;
    }

    public CoinPriceResponseVo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExcg_img() {
        return excg_img;
    }

    public void setExcg_img(String excg_img) {
        this.excg_img = excg_img;
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
