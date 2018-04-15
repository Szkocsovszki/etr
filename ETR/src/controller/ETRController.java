package controller;

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

	public Account getAccount(String eha, String password) {
		return dao.getAccount(eha, password);
	}
	
	//public void createAccount(Account account);
	
	//public void deleteAccount(String eha);
	
	//public void modifyAccount(Account account);
	
	//public void changePassword();
}
