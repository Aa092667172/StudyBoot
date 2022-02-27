package webscoket.controller;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import webscoket.config.GetHttpSessionConfigurator;
import webscoket.domain.Message;
import webscoket.domain.MessageUtils;

@ServerEndpoint(value="/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class MessageController {
	
	//用來儲存每個客戶端的對象
	private static Map<String,MessageController> onlineUsers = new ConcurrentHashMap<>();
	//聲明session對象,通過該對象可以發送給指定用戶
	private Session session;
	//聲明一個httpSession對象,我們之前在http對象中儲存了使用者名稱
	private HttpSession httpSession;
	
	
	@OnOpen
	public void onOpen(Session session,EndpointConfig config) {
		this.session = session;
		//獲取httpSession對象
		HttpSession httpSession =(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		this.httpSession = httpSession;
		
		//從httpSession對象用戶名
		String userName = (String)httpSession.getAttribute("user");
		
		//將對象存儲到容器
		onlineUsers.put(userName, this);
		//將當前在線用戶的用戶名推播給使用者
		String message = MessageUtils.getMessage(true,null,getNames());
		
		broadCastAllUsers(message);
	}
	
	private void broadCastAllUsers(String message) {
		try {
			Set<String> names = getNames();
			for(String name:names) {
				MessageController cnatEndpoint = onlineUsers.get(name);
				cnatEndpoint.session.getBasicRemote().sendText(message);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Set<String> getNames(){
		return onlineUsers.keySet();
	}
	
	@OnMessage
	public void onMessage(String message,Session session) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Message msg = mapper.readValue(message, Message.class);
			//將數據發送給用戶
			String toName = msg.getToName();
			String data = msg.getMessage();
			//獲取當前登入用戶
			String userName = (String)httpSession.getAttribute("user");
			//獲取消息格式的數據
			
			String resultMessage = MessageUtils.getMessage(false,userName, data);
			//發送數據
			onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@OnClose
	public void onColse(Session session) {
		
	}
	
}
