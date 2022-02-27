package webscoket.domain;

public class ResultMessage {
	private boolean isSystem;
	private String fromName;
	private Object message;
	public boolean isSystem() {
		return isSystem;
	}
	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ResultMessage [isSystem=" + isSystem + ", fromName=" + fromName + ", message=" + message + "]";
	}
	
}
