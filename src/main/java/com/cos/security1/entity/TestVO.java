package com.cos.security1.entity;
import lombok.Data;

@Data
public class TestVO {
	private String apiRspsCd;
	private String apiRspsMsg;
	
	
	public TestVO() {
	}
	
	public TestVO(String apiRspsCd, String apiRspsMsg) {
		this.apiRspsCd = apiRspsCd;
		this.apiRspsMsg = apiRspsMsg;
	}
	public String getApiRspsCd() {
		return apiRspsCd;
	}
	public void setApiRspsCd(String apiRspsCd) {
		this.apiRspsCd = apiRspsCd;
	}
	public String getApiRspsMsg() {
		return apiRspsMsg;
	}
	public void setApiRspsMsg(String apiRspsMsg) {
		this.apiRspsMsg = apiRspsMsg;
	}
	
}
