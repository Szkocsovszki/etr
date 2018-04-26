package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.model.Account;

public interface ETRDAO {
	public Account logIn(String eha, String password) throws SQLException;
	
	public void createAccount(Account account) throws SQLException;
	
	public void deleteAccount(String eha) throws SQLException;
	
	public void modifyAccount(Account account) throws SQLException;

	public Account getAccountToModify(String eha) throws SQLException;

	public void changePassword(String eha, String jelszo) throws SQLException;
	
	public ArrayList<String> getCourses() throws SQLException ;
	
	public ArrayList<String> getExams() throws SQLException;
	
	public ArrayList<String> pickUpACourse(String eha, String courseCode) throws SQLException;
	
	public ArrayList<String> pickUpAnExam(String eha, String examCode) throws SQLException;
	
	
}
