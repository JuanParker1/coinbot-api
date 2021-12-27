package com.coinstation.coinapi.controller;

import com.coinstation.coinapi.vo.ResponseHeadVo;
import com.coinstation.coinapi.vo.ResponseVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coin")
public class CoinController {

    /**
     *
     * @param symbol
     * @return
     */
    @GetMapping(value = "/price/allmarket/{symbol}", produces = "application/json")
    ResponseEntity<?> getHelloWorld(@PathVariable(value = "symbol") String symbol){
        HttpStatus resMessage = HttpStatus.OK;
        String detail = "정상";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();
        String msg = "";

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        /* 1. 닉네임을 검색해서 해방 코인의 symbol 가져오기
        *     검색했는데 코인이 없으면 뒷 로직 스톱하고 없다고 메시지 뱉을 것*/


        /* 2. 검색가능한 거래소를 DB에서 가져오기(가져올 떄 거래소 순서에 맞게 가져올 것)*/

        /* 3. 가져온 거래소 정보와 코인 가격 api 요청을 차례대로 실행*/

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
