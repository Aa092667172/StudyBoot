package com.handlers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.domain.User;
import com.repository.UserRepository;
import com.util.CheckUtil;

import reactor.core.publisher.Mono;
/**
 * 因APPLICATION_PROBLEM_JSON_UTF8已被棄用,改為application檔案設定server.servlet.encoding
 * 採用靜態import 簡潔代碼
 * @author linyukai
 *
 */
@Component
public class UserHandler {
	private final UserRepository repository;
	
	
	public UserHandler(UserRepository rep) {
		this.repository = rep;
	}
	
	/**
	 * 得到所有用戶
	 */
	public Mono<ServerResponse> getAllUser(ServerRequest requset){
		return  ok().contentType(APPLICATION_PROBLEM_JSON)
				.body(this.repository.findAll(),User.class);
	}
	
	
	/**
	 * 創建用戶
	 */
	public Mono<ServerResponse> createUser(ServerRequest requset){
		//此處驗證不可調用block 會阻塞執行緒,並且會報錯
		Mono<User> user = requset.bodyToMono(User.class);
		return user.flatMap(u->{
				CheckUtil.checkName(u.getName());
				return ok().contentType(APPLICATION_JSON)
					.body(this.repository.saveAll(user),User.class);
		});
	}
	
	/**
	 * 根據id刪除用戶
	 */
	public Mono<ServerResponse> deleteUserById(ServerRequest requset){
		String id = requset.pathVariable("id");
		return this.repository.findById(id)
				.flatMap(user->
						this.repository.delete(user).then(ok().build()))
						.switchIfEmpty(notFound().build());
	}
}
