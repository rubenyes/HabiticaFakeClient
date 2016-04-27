package persistance;

import logic.Account;

public interface IAccountsDAO {
	public void setHost(String host);
	public boolean createAccount(Account account);
	public Account getAccount(String email, String password); 
	public void deleteAccount(String email);
}
