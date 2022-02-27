package webscoket.config;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configurable
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	/**
	 * 配置 ＷebSocket進入點,及開啟使用SockJS,
	 * 這些配置主要用配置連接端點,用於WebSocket連接
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("mydlq").withSockJS();
	}
	@Bean
	//注入ServerEndpointExporter bean對象,自動註冊使用@ServerEndpoint的bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	
	//配置消息代理選項
	public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置代理前綴，在 Controller 类中的方法里面发生的消息，会首先转发到代理从而发送到对应广播或者队列中。
		registry.enableSimpleBroker("/topic");
        // 配置客户端发送请求消息的一个或多个前缀，该前缀会筛选消息目标转发到 Controller 类中注解对应的方法里
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	
}
