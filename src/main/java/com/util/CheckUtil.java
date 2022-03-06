package com.util;

import java.util.stream.Stream;

import com.exceptions.CheckException;

public class CheckUtil {
	private static final  String[] INVALID_NAMES = {"admin","guanliyuan"};
	
	/**
	 * 校驗名稱,不成立拋出異常
	 */
	public static void checkName(String value) {
		Stream.of(INVALID_NAMES).filter(name->name.equalsIgnoreCase(value))
		.findAny().ifPresent(name->{
			throw new CheckException("name", value);
		});
	}
}
