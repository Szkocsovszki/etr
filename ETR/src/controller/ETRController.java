package controller;

import java.sql.SQLException;
import java.util.ArrayList;

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

	public Account logIn(String eha, String password) throws SQLException{
		return dao.logIn(eha, password);
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
	
	public void changePassword(String eha, String password) throws SQLException{
		dao.changePassword(eha, password);
	}
	
	public ArrayList<String> getCourses() throws SQLException {
		return dao.getCourses();
	}

	public ArrayList<String> getExams() throws SQLException {
		return dao.getExams();
	}

	public ArrayList<String> pickUpACourse(String eha, String courseCode) throws SQLException {
		return dao.pickUpACourse(eha, courseCode);
	}

	public ArrayList<String> pickUpAnExam(String eha, String examCode) throws SQLException {
		return dao.pickUpAnExam(eha, examCode);
	}
	
}
