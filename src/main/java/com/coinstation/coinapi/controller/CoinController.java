package com.coinstation.coinapi.controller;

import com.coinstation.coinapi.service.CoinService;
import com.coinstation.coinapi.service.CommonService;
import com.coinstation.coinapi.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        String detail = "정상";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();
        String msg = "";
        boolean has_nick = true; //닉네임 검색 결과 (true 있음 / false 없음)
        String symbol = "";

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        /* 1. 닉네임을 검색해서 해당 코인의 symbol 가져오기
        *     검색했는데 symbol이 없으면 뒷 로직 스톱하고 없다고 메시지 뱉을 것*/
        if( nick.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") ){   // 한글로 검색 들어오면 닉네임 검색
            String nickSymbol = coinService.getCoinNick(nick);
            if(nickSymbol == null) {
                has_nick = false;
            }else{
                symbol = nickSymbol;  // 닉네임에서 검색해서 나오면 symbol에 넣어줌
            }
        }else if( nick.matches("^[a-zA-Z]*$") ){    // 영어로 검색 들어오면 바로 symbol로 받음
            symbol = nick;
        }

        /* 2. 코인가격 api 요청을 차례대로 실행*/
        List<CoinPriceResponseVo> coinList = new ArrayList<CoinPriceResponseVo>();

        //바이낸스
        CoinPriceResponseVo binance = coinService.getBinancePrice(symbol);
        if(binance != null)coinList.add(binance);
        
        //업비트
        CoinPriceResponseVo upbit = coinService.getUpbitPrice(symbol);
        if(upbit != null)coinList.add(upbit);

        //ftx
        CoinPriceResponseVo ftx = coinService.getFtxPrice(symbol);
        if(ftx != null)coinList.add(ftx);

        //빗썸
        CoinPriceResponseVo bithumb = coinService.getBithumbPrice(symbol);
        if(bithumb != null)coinList.add(bithumb);

        //코인원
        CoinPriceResponseVo coinone = coinService.getCoinonePrice(symbol);
        if(coinone != null)coinList.add(coinone);

        if (isError || coinList.size() < 1) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));
        responseVo.setBody(coinList);

        return ResponseEntity.status(resMessage).body(responseVo);
    }
}
