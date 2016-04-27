package persistance;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import logic.Account;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DnsDAO {
	
	public final static String dnsHost = "https://arqservices.firebaseio.com/services.json";

	public static Map<String, Map<String, String>> getHosts() {
		Map<String, Map<String, String>> map = null;
		try{
			URL url = new URL(dnsHost);
			InputStream input = url.openStream();
			map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, Map<String, String>>>(){}.getType());			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

}
