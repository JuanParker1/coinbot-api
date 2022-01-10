package com.coinstation.coinapi.controller;

import com.coinstation.coinapi.service.CoinService;
import com.coinstation.coinapi.service.CommonService;
import com.coinstation.coinapi.service.InfoService;
import com.coinstation.coinapi.vo.CoinPriceResponseVo;
import com.coinstation.coinapi.vo.ForexVo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private CommonService commonService;

    /**
     * 환율가져오기
     * @param currency
     * @return
     */
    @GetMapping(value = "/info/forex/{currency}", produces = "application/json")
    ResponseEntity<?> getForex(@PathVariable(value = "currency") String currency){
        HttpStatus resMessage = HttpStatus.OK;
        String detail = "";
        ObjectMapper mapper = new ObjectMapper();
        Boolean isError = false;
        Integer resCode = resMessage.value();
        String msg = "";

        // 응답결과 만들기
        ResponseVo responseVo = new ResponseVo();

        // Body에 들어갈 응답값
        Map<String, Object> rspsBody = new HashMap<String, Object>();

        ForexVo result = infoService.getForexInfo(currency);

        if (isError) {
            resMessage = HttpStatus.INTERNAL_SERVER_ERROR;
            resCode = resMessage.value();
        }

        rspsBody.put("currencyInfo", result);
        responseVo.setBody(rspsBody);

        responseVo.setHead(new ResponseHeadVo(resCode, resMessage.getReasonPhrase(), detail));

        return ResponseEntity.status(resMessage).body(responseVo);
    }
}
