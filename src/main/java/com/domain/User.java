package com.domain;


import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
/**
 * 因springboot改版
 * 需於pom檔案中加入
 * <dependency>
 *			<groupId>org.springframework.boot</groupId>
 *			<artifactId>spring-boot-starter-validation</artifactId>
 * </dependency>
 * @author linyukai
 *
 */
@Document(collection = "user")
@Data
public class User {
	@Id
	private String id;
	//不允許空或空字串 可增加message修改回覆訊息
	@NotBlank
	private String name;
	@Range(min =10 ,max = 100)
	private int age;
}
