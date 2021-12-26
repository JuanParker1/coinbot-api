package com.coinstation.coinapi.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVo implements Serializable {

	private ResponseHeadVo head;
	private Object body;
}
