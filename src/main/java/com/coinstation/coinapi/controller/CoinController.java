package com.coinstation.coinapi.controller;

import com.coinstation.coinapi.service.CoinService;
import com.coinstation.coinapi.service.CommonService;
import com.coinstation.coinapi.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private CommonService commonService;

    /**
     *
     * @return
     */
    @GetMapping(value = "/price/allmarket/{nick}", produces = "application/json")
    ResponseEntity<?> getHelloWorld(@PathVariable(value = "nick") String nick){
        HttpStatus resMessage = HttpStatus.OK;
        String detail = "";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();
        String msg = "";
        boolean is_symbol = true; // 검색한 값이 symbol인지 nick인지
        String symbol = "";

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        // Body에 들어갈 응답값
        Map<String, Object> rspsBody = new HashMap<String, Object>();

        /* 1. 닉네임을 검색해서 해당 코인의 symbol 가져오기
        *     검색했는데 symbol이 없으면 뒷 로직 스톱하고 없다고 메시지 뱉을 것*/
        if( nick.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") ){   // 한글로 검색 들어오면 닉네임 검색
            String nickSymbol = coinService.getCoinNick(nick);
            if(nickSymbol == null) {
                // 한글로 검색한 결과가 없으므로 바로 응답
                detail = "등록된 한글 코인명이 없습니다.";
                resMessage = HttpStatus.OK;
                responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));
                rspsBody.put("result_status", "NOT_NICK");
                return ResponseEntity.status(resMessage).body(responseVo);
            }else{
                symbol = nickSymbol;
                detail = nickSymbol;
                is_symbol = false;
            }
        }else if( nick.matches("^[a-zA-Z]*$") ){    // 영어로 검색 들어오면 바로 symbol로 받음
            symbol = nick;
        }

        /* 2. 코인가격 api 요청을 차례대로 실행*/
        List<CoinPriceResponseVo> coinList = new ArrayList<CoinPriceResponseVo>();

        //바이낸스
        CoinPriceResponseVo binance = coinService.getBinancePrice(symbol);
        float binancePrice = 0;
        if(binance != null){
            binancePrice = Float.parseFloat( ((binance.getPrice()).replace(" USDT", "")).replace(",","") );
            coinList.add(binance);
        }
        
        //업비트
        CoinPriceResponseVo upbit = coinService.getUpbitPrice(symbol, binancePrice);
        if(upbit != null)coinList.add(upbit);

        //ftx
        CoinPriceResponseVo ftx = coinService.getFtxPrice(symbol);
        if(ftx != null)coinList.add(ftx);

        //빗썸
        CoinPriceResponseVo bithumb = coinService.getBithumbPrice(symbol, binancePrice);
        if(bithumb != null)coinList.add(bithumb);

        //코인원
        CoinPriceResponseVo coinone = coinService.getCoinonePrice(symbol, binancePrice);
        if(coinone != null)coinList.add(coinone);

        if (isError) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        CoinInfoVo info = coinService.getCoinInfo(symbol);
        if(coinList.size() < 1){
            if(info == null){    // 코인마켓캡에도 없음
                rspsBody.put("result_status", "NOT_COIN");
                detail = "존재하지 않는 코인입니다.";
                responseVo.setBody(rspsBody);
            }else{
                rspsBody.put("result_status", "NOT_EXIST");
                rspsBody.put("coin_info", info);
                detail = "검색 지원되는 거래소에 해당 코인이 없습니다. 코인마켓캡의 정보를 가져옵니다.";
                responseVo.setBody(rspsBody);
            }
        }else{
            rspsBody.put("result_status", "SUCCESS");
            rspsBody.put("is_symbol", is_symbol);
            rspsBody.put("coin_info", info);
            rspsBody.put("symbol", symbol.toUpperCase());
            rspsBody.put("coin_list", coinList);
            responseVo.setBody(rspsBody);
        }
        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));

        return ResponseEntity.status(resMessage).body(responseVo);
    }
}
