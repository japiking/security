package com.cos.security1.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1286677377974669322L;
	private String errCd;
	private String errMsg;
	public CustomException(String errCd, String errMsg) {
		this.errCd = errCd;
		this.errMsg = errMsg;
	}
	public String getErrCd() {
		return errCd;
	}
	public String getErrMsg() {
		return errMsg;
	}
}
