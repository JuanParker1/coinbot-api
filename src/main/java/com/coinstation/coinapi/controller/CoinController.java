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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            CoinNickVo nickVo = coinService.getCoinNick(nick);
            if(nickVo == null) {
                has_nick = false;
            }else{
                symbol = nickVo.getCoin_symbol();  // 닉네임에서 검색해서 나오면 symbol에 넣어줌
            }
        }else if( nick.matches("^[a-zA-Z]*$") ){    // 영어로 검색 들어오면 바로 symbol로 받음
            symbol = nick;
        }

        /* 2. 코인가격 api 요청을 차례대로 실행*/
        List<CoinPriceResponseVo> coinList = new ArrayList<CoinPriceResponseVo>();

        CoinPriceResponseVo vo = new CoinPriceResponseVo();

        //바이낸스
        vo = coinService.getBinancePrice(symbol);
        if(vo != null)coinList.add(vo);
        
        //업비트

        //ftx

        //빗썸

        //코인원

        if (isError || coinList.size() < 1) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));
        responseVo.setBody(coinList);

        return ResponseEntity.status(resMessage).body(responseVo);
    }

    /**
     *
     * @param exchange  거래소명
     * @param symbol    코인심볼
     * @return
     */
    @GetMapping(value = "/price/exchange/{exchange}/{symbol}", produces = "application/json")
    ResponseEntity<?> getCoinExchangePrice(@PathVariable String exchange, @PathVariable String symbol ){
        HttpStatus resMessage = HttpStatus.OK;
        String detail = "정상";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        if (isError) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));
//        responseVo.setBody(isError ? null : itemViewVo);
        responseVo.setBody("ok");

        return ResponseEntity.status(resMessage).body(responseVo);
    }

    /**
     * 사용성이 오픈되어 있는 모든 거래소에서 코인을 찾아서 가격과 퍼센트 도출
     * @param symbol
     * @return
     */
    @GetMapping(value = "/price/all/{symbol}", produces = "application/json")
    ResponseEntity<?> getCoinPrice(@PathVariable String symbol ){
        HttpStatus resMessage = HttpStatus.OK;
        String detail = "정상";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        // 1. 검색을 할 수 있는 거래소 리스트 도출(거래소 마다 우선순위를 부여하고, 우선순위가 높은

        /*  2. API를 통해 가격 정보를 가져옴 for문을 통해 차례차례 가져오고 4개를 정상적으로 받았다면 멈춤

        */

        // 3. 받은

        if (isError) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));

        responseVo.setBody("ok");

        return ResponseEntity.status(resMessage).body(responseVo);
    }
}
