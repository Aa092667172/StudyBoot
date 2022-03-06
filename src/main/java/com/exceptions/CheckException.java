package com.exceptions;

import lombok.Getter;
import lombok.Setter;

public class CheckException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//出錯參數名
	@Getter
	@Setter
	private String fieldName;
	//出錯參數值
	@Getter
	@Setter
	private String fieldValue;

	public CheckException() {
		super();
	}

	public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param fieldName
	 * @param fieldValue
	 */
	public CheckException(String fieldName, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	
}
