package com.coinstation.coinapi.vo;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Data
public class CoinNickVo {
    private Integer id;
    private String coin_symbol;
    private String nick_nm;
    private String create_dt;
}
