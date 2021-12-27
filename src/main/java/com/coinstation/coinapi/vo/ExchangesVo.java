package com.coinstation.coinapi.vo;

import lombok.Data;

@Data
public class ExchangesVo {
    private Integer id;
    private String name;
    private String nick;
    private String need_api_key;
    private String header_key;
    private String header_value;
    private Integer orders;
}
