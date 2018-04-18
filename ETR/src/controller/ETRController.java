package controller;

import java.sql.SQLException;

import dao.ETRDAO;
import dao.ETRDAOImpl;
import dao.model.Account;
import view.ETRGUI;

public class ETRController {
	
	private ETRGUI gui;
	private ETRDAO dao;

	public void startDesktop() {
		gui = new ETRGUI(this);
		gui.startGUI();
		dao = new ETRDAOImpl();
	}

	public Account getAccount(String eha, String password) throws SQLException{
		return dao.getAccount(eha, password);
	}
	
	public Account getAccountToModify(String eha) throws SQLException{
		return dao.getAccountToModify(eha);
	}
	
	public void createAccount(Account account) throws SQLException{
		dao.createAccount(account);
	}
	
	public void deleteAccount(String eha) throws SQLException{
		dao.deleteAccount(eha);
	}
	
	public void modifyAccount(Account account) throws SQLException{
		dao.modifyAccount(account);
	}
	
	public void changePassword() throws SQLException{
		dao.changePassword();
	}
}
