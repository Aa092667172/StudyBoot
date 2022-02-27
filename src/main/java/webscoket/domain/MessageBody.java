package webscoket.domain;

public class MessageBody {
	private String content;
	//廣播發送的目標地址 （告知STOMP 代理轉發到哪個地方）
	private String destination;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@Override
	public String toString() {
		return "MessageBody [content=" + content + ", destination=" + destination + "]";
	}
	
}
