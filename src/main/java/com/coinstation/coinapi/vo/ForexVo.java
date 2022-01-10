package com.coinstation.coinapi.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForexVo {
    private Integer id;
    private String currency_code;   //환율 코드
    private float base_price;       //현재 환율
    private float change_price;     //변동값 절대치
    private String change;          //변동상태값

    public ForexVo(String currency_code, float base_price, float change_price, String change) {
        this.currency_code = currency_code;
        this.base_price = base_price;
        this.change_price = change_price;
        this.change = change;
    }
}
