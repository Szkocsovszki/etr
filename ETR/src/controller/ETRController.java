package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.ETRDAO;
import dao.ETRDAOImpl;
import dao.course.Course;
import dao.course.Exam;
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
	
	public ArrayList<Course> getCourses() throws SQLException{
		return dao.getCourses();
	}
	
	public ArrayList<Course> getCourses(String eha) throws SQLException{
		return dao.getCourses(eha);
	}
	
	public void pickUpACourse(String eha, String courseCode) throws SQLException{
		dao.pickUpACourse(eha, courseCode);
	};
	
	public void takeOffACourse(String eha, String courseCode) throws SQLException{
		dao.takeOffACourse(eha, courseCode);
	};
	
	public void giveMarkOnCourse(String eha, String courseCode, int mark) throws SQLException{
		dao.giveMarkOnCourse(eha, courseCode, mark);
	};
	
	public ArrayList<Exam> getExams() throws SQLException{
		return dao.getExams();
	};
	
	public ArrayList<Exam> getExams(String eha) throws SQLException{
		return dao.getExams(eha);
	};
	
	public void pickUpAnExam(String eha, String examCode) throws SQLException{
		dao.pickUpAnExam(eha, examCode);
	};
	
	public void takeOffAnExam(String eha, String examCode) throws SQLException{
		dao.takeOffAnExam(eha, examCode);
	};
	
	public void payment(String eha, String examCode) throws SQLException{
		dao.payment(eha, examCode);
	};
	
	public void makeACourse(Course course) throws SQLException{
		dao.makeACourse(course);
	};
	
	public void makeAnExam(Exam exam) throws SQLException{
		dao.makeAnExam(exam);
	};
	
	public String[][] getMessages(String courseCode) throws SQLException{
		return dao.getMessages(courseCode);
	};
	
}
