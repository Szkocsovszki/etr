package dao;

import java.sql.SQLException;

import dao.model.Account;

public interface ETRDAO {
	public Account getAccount(String eha, String password) throws SQLException;
	
	public void createAccount(Account account) throws SQLException;
	
	public void deleteAccount(String eha) throws SQLException;
	
	public int modifyAccount(Account account) throws SQLException;

	public Account getAccountToModify(String eha) throws SQLException;

	void changePassword(String eha, String jelszo) throws SQLException;
}
