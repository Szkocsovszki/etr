package dao;

import dao.model.Account;

public interface ETRDAO {
	public Account getAccount(String eha, String password);
	
	public void createAccount(Account account);
	
	public void deleteAccount(String eha);
	
	public void modifyAccount(Account account);
	
	public void changePassword();
}
