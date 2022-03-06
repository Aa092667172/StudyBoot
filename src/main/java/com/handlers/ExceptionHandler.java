package com.handlers;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.exceptions.CheckException;

import reactor.core.publisher.Mono;
@Component
//將異常優先值調高 數值越小 優先值越高
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler{

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		//設置響應頭400
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
		String errorMsg = toStr(ex);
		DataBuffer data = response.bufferFactory().wrap(errorMsg.getBytes());
		return response.writeWith(Mono.just(data));
	}
	
	private String toStr(Throwable ex) {
		if(ex instanceof CheckException) {
			CheckException e = (CheckException)ex;
			return e.getFieldName() + ":invalid value " + e.getFieldValue();
		}
		//未知異常,需打印堆棧 方便查詢錯誤
		else {
			ex.printStackTrace();
			return ex.toString();
		}
	}

}
