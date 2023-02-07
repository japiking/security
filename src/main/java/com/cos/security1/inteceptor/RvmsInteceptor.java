package com.cos.security1.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class RvmsInteceptor implements HandlerInterceptor{
	
	/**
	 * 전처리 인터셉터
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		System.out.println("preHandle~~~11");
		
		request.setAttribute("AAAA", "111");
		//API 거래 기록 등록
		
		LogRequestWrapper logRequestWrapper = new LogRequestWrapper((HttpServletRequest)request);
		
		System.out.println("preHandle logRequestWrapper : " + logRequestWrapper.getBody());
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	/**
	 * 후처리 인터셉터
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		
		System.out.println("postHandle~~~22");
		
		//API 응답기록 등록
		LogResponseWrapper logResponseWrapper = new LogResponseWrapper((HttpServletResponse)response);
		
		System.out.println("postHandle logResponseWrapper22 : " + logResponseWrapper.getBody());
		
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//로그처리는 여기에서 하는게 좋을것 같음
		
		System.out.println("afterCompletion~~~22");
		
		if (request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper")) return;
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
 
        if (cachingRequest.getContentType() != null && cachingRequest.getContentType().contains("application/json")) {
            if (cachingRequest.getContentAsByteArray() != null && cachingRequest.getContentAsByteArray().length != 0){
                System.out.println("Request Body : "+ new String(cachingRequest.getContentAsByteArray()));
            }
        }
        if (cachingResponse.getContentType() != null && cachingResponse.getContentType().contains("application/json")) {
            if (cachingResponse.getContentAsByteArray() != null && cachingResponse.getContentAsByteArray().length != 0) {
            	System.out.println("Response Body : "+ new String(cachingResponse.getContentAsByteArray()));
            }
        }
		
		
		
		
		
		//API 응답기록 등록
//		LogResponseWrapper logResponseWrapper = new LogResponseWrapper((HttpServletResponse)response);
//		
//		System.out.println("afterCompletion logResponseWrapper : " + logResponseWrapper.getBody());
		
		System.out.println("errMsg : " + response.getHeader("BBB"));
		System.out.println("afterCompletion~~~33");
	}
}
