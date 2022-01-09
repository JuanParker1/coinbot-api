package com.coinstation.coinapi.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinInfoVo {
    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    private Integer num_market_pairs;
    private String date_added;
    private String tags;
    private BigDecimal max_supply;
    private BigDecimal circulating_supply;
    private BigDecimal total_supply;
    private Integer cmc_rank;
    private String last_updated;
    private Double price;
    private BigDecimal volume_24h;
    private Double volume_change_24h;
    private Double percent_change_1h;
    private Double percent_change_24h;
    private Double percent_change_7d;
    private Double percent_change_30d;
    private Double percent_change_60d;
    private Double percent_change_90d;
    private BigDecimal market_cap;
    private Float market_cap_dominance;
    private BigDecimal fully_diluted_market_cap;
}
