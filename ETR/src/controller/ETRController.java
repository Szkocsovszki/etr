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
	
	public Account getAccountToModify(String eha) {
		return dao.getAccountToModify(eha);
	}
	
	public void createAccount(Account account) {
		dao.createAccount(account);
	}
	
	public void deleteAccount(String eha) {
		dao.deleteAccount(eha);
	}
	
	public void modifyAccount(Account account) {
		dao.modifyAccount(account);
	}
	
	//public void changePassword();
}
