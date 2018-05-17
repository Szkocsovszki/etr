package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.course.Course;
import dao.course.Exam;
import dao.course.ForumMessage;
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
	
	/**
	 * Get picked up courses.
	 * @param eha
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Course> getCourses(String eha) throws SQLException ;
	
	public void giveMarkOnCourse(String eha, String courseCode, int mark) throws SQLException;
	
	public ArrayList<Exam> getExams() throws SQLException;
	
	public ArrayList<Exam> getExams(String eha) throws SQLException;
	
	public void giveMarkOnExam(String eha, String examCode, int mark) throws SQLException;
	
	public void pickUpACourse(String eha, String courseCode) throws SQLException;
	
	public void pickUpAnExam(String eha, String examCode) throws SQLException;
	
	public void takeOffACourse(String eha, String courseCode) throws SQLException;
	
	public void takeOffAnExam(String eha, String examCode) throws SQLException;
	
	public void makeACourse(String eha, Course course) throws SQLException;
	
	public void makeAnExam(Exam exam) throws SQLException;
	
	public void payment(String eha, String examCode) throws SQLException;
	
	public ArrayList<ForumMessage> getMessages(String courseCode) throws SQLException;
	
	public void writeMessage(String eha, String courseCode, String message) throws SQLException;
	
	public Double getAvg(String eha) throws SQLException;

	public Double getSulyAvg(String eha) throws SQLException;
}
