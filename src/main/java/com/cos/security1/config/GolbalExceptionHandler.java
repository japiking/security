package com.cos.security1.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cos.security1.config.exception.CustomException;
import com.cos.security1.entity.TestVO;

@RestControllerAdvice
public class GolbalExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
    public @ResponseBody ResponseEntity<TestVO> handleIllegalArgumentException(CustomException e) {
        // ...
		TestVO rlt = new TestVO();
		rlt.setApiRspsCd(e.getErrCd());
		rlt.setApiRspsMsg(e.getErrMsg());
		
		System.out.println("e : " + e);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("BBB", e.getErrMsg());
		
		return ResponseEntity.ok().headers(responseHeaders).body(rlt);
    }
	
}
