package com.coinstation.coinapi.controller;

import com.coinstation.coinapi.controller.vo.ResponseHeadVo;
import com.coinstation.coinapi.controller.vo.ResponseVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coin")
public class CoinInfoController {
    @GetMapping(value = "/{symbol}", produces = "application/json")
    ResponseEntity<?> getHelloWorld(@PathVariable String symbol){
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
     *
     * @param exchange  거래소명
     * @param symbol    코인심볼
     * @return
     */
    @GetMapping(value = "/price/{exchange}/{symbol}", produces = "application/json")
    ResponseEntity<?> getCoinPrice(@PathVariable String exchange, @PathVariable String symbol ){
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
}
