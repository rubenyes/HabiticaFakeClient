package persistance;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logic.Account;

public class AccountsDAO implements IAccountsDAO{
	
	private String host = "http://localhost:9000/api/user/";

	private static AccountsDAO dao;
	
	private AccountsDAO(){}
	
	public static AccountsDAO givemeDAO(){
		if(dao == null) dao = new AccountsDAO();
		return dao;
	}
	
	@Override
	public void setHost(String host){
		this.host = host;
	}
	
	@Override
	public boolean createAccount(Account a) {
		try{
			URL url = new URL(host);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			httpcon.connect();
	
			String json = "{\"Name\": \""+a.getName()+"\""+
							", \"Email\": \""+a.getEmail()+"\""+
							", \"password\": \""+a.getPassword()+"\"";
			
			if(a.getBirthday() != null)
				json += ", \"Birthday\": \""+a.getBirthday().getTimeInMillis()+"\""+"}";
			else
				json += "}";
			
			byte[] outputBytes = json.getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);
	
			os.close();
			
			int code = httpcon.getResponseCode();
			if(code == 201) return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Account getAccount(String email, String password) {
		Account account = null;
		try{
			URL url = new URL(host+email+"/"+password);
			InputStream input = url.openStream();
			Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
			account = new Account(map.get("Name"), map.get("Email"), map.get("password"));
			if(map.containsKey("Birthday")){
				Calendar birthday = Calendar.getInstance();
				birthday.setTimeInMillis(Long.parseLong(map.get("Birthday")));
				account.setBirthday(birthday);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return account;
	}

	@Override
	public void deleteAccount(String email) {
		try{
			URL url = new URL(host+email);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpCon.setRequestMethod("DELETE");
			httpCon.connect();
			System.out.println(httpCon.getResponseCode()+" "+httpCon.getResponseMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
