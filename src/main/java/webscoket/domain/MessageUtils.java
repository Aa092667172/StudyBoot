package webscoket.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtils {
	public static String getMessage(boolean isSystemMeasage,String fromName,Object message) {
		try {
			ResultMessage result = new ResultMessage();
			result.setSystem(isSystemMeasage);
			if(fromName!=null) {
				result.setFromName(fromName);
			}
			ObjectMapper mapper = new ObjectMapper();
			
			return mapper.writeValueAsString(result);			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
