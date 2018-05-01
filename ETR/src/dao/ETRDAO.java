package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.course.Course;
import dao.course.Exam;
import dao.model.Account;

public interface ETRDAO {
	public Account logIn(String eha, String password) throws SQLException;
	
	public void createAccount(Account account) throws SQLException;
	
	public void deleteAccount(String eha) throws SQLException;
	
	public void modifyAccount(Account account) throws SQLException;

	public Account getAccountToModify(String eha) throws SQLException;

	public void changePassword(String eha, String jelszo) throws SQLException;
	
	/**
	 * Get all the courses.
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Course> getCourses() throws SQLException ;
	
	public ArrayList<Exam> getExams() throws SQLException;
	
	/**
	 * Get picked up courses.
	 * @param eha
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Course> getCourses(String eha) throws SQLException ;
	
	public ArrayList<Exam> getExams(String eha) throws SQLException;
	
	public void pickUpACourse(String eha, Course course) throws SQLException;
	
	public void pickUpAnExam(String eha, Exam exam) throws SQLException;
	
	
}
