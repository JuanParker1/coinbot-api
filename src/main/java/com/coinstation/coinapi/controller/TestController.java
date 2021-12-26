package com.coinstation.coinapi.controller;

import com.coinstation.coinapi.service.CoinPriceService;
import com.coinstation.coinapi.vo.ResponseHeadVo;
import com.coinstation.coinapi.vo.ResponseVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CoinPriceService coinPriceService;

    @GetMapping(value = "/insertNickTest", produces = "application/json")
    ResponseEntity<?> insertNick() throws IOException {
        HttpStatus resMessage = HttpStatus.OK;
        String detail = "정상";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        isError = coinPriceService.insertCoinNickBulk();

        if (isError) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));
        responseVo.setBody("ok");

        return ResponseEntity.status(resMessage).body(responseVo);
    }

}
