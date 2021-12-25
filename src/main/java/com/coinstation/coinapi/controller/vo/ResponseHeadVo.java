package com.coinstation.coinapi.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHeadVo implements Serializable {
	private static final long serialVersionUID = 5090878273160210857L;

	private Integer code;
	private String message;
	private String detail;
}
