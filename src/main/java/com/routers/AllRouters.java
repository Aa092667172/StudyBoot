package com.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.handlers.UserHandler;
/**
 * 每個andRoute 相當於一隻API
 * @author linyukai
 *
 */
@Configuration
public class AllRouters {
	@Bean
	RouterFunction<ServerResponse> userRouter(UserHandler handler){
		//嵌套 第一個參數為預判,第二個參數為路由函數（RouterFunction)
		return nest(
				// 相當於ＵserController類上的@RequestMapping("/user")
				path("/user/router"), 
					route(
						//取得所有用戶
						//相當於方法上的@GetMapping("/user")
						GET("/"), handler::getAllUser)
					.andRoute(
							//創建用戶
							POST("/").and(accept(MediaType.APPLICATION_JSON)), handler::createUser)
					.andRoute(DELETE("{id}"), handler::deleteUserById));
	}
}
