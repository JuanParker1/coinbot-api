package com.coinstation.coinapi.vo;

import lombok.Data;

@Data
public class CoinNickVo {
    private Integer id;
    private String coin_symbol;
    private String nick_nm;
    private String ref_room_id;
    private String create_dt;
}
