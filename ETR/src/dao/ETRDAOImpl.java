package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.course.Course;
import dao.course.Exam;
import dao.model.Account;
import view.Labels;

public class ETRDAOImpl implements ETRDAO {

	private Connection conn;

	public ETRDAOImpl() {

		super();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:4000:kabinet", "h675353", "geniusos123");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "geniusos");
			conn.setAutoCommit(false);

		} catch (Exception ex) {
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(
					  null,
					  ex.getMessage(),
					  Labels.ERROR,
					  JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public Account logIn(String eha, String password) throws SQLException {
		PreparedStatement st = conn.prepareStatement("SELECT jelszo FROM SZEMELY WHERE EHA = ? AND jelszo = ora_hash(concat( ? , 'etr' ))");
		st.setString(1, eha);
		st.setString(2, password);
		ResultSet accExists = st.executeQuery();
		if(accExists.next() ) {
			st.close();
			return getAccountToModify(eha);
		}else {
			st.close();
			return null;
		}
	}

	@Override
	public Account getAccountToModify(String eha) throws SQLException {
		PreparedStatement st = conn.prepareStatement("SELECT * FROM szemely WHERE EHA = ?");
		st.setString(1, eha);
		ResultSet accExists = st.executeQuery();
		if (accExists.next()) {
			
			ArrayList<String> departmentStore = new ArrayList<String>();
			st = conn.prepareStatement("SELECT szaknev FROM szak WHERE EHA = ?");
			st.setString(1, eha);
			ResultSet departments = st.executeQuery();
			while (departments.next())
				departmentStore.add(departments.getString(1));

			Account account = new Account(accExists.getString(1), accExists.getString(2), (accExists.getString(3).split(" "))[0], accExists.getString(4), departmentStore, accExists.getInt(7));
			st.close();
			return account;
			
		}
		st.close();
		return null;
	}

	@Override
	public void createAccount(Account account) throws SQLException {
		PreparedStatement st = conn.prepareStatement("INSERT INTO szemely VALUES (?,?,to_date(?, 'YYYY-MM-DD'),?,?,ora_hash(concat(?,'etr')),?)");
		st.setString(1, account.getEha());
		st.setString(2, account.getName());
		st.setString(3, account.getBirthDate());
		st.setString(4, account.getAddress());
		st.setString(5, account.getEha());
		st.setString(6, account.getEha());
		st.setInt(7, account.getType());
		st.executeUpdate();
		
		st = conn.prepareStatement("INSERT INTO szak VALUES (?,?)");
		st.setString(1, account.getEha());
		for(String sz : account.getDepartment()) {
			st.setString(2, sz);
			st.executeQuery();
		}

		conn.commit();
		st.close();

	}

	@Override
	public void deleteAccount(String eha) throws SQLException {
		PreparedStatement st = conn.prepareStatement("DELETE FROM szemely WHERE eha = ? ");
		st.setString(1, eha);
		int deleted = st.executeUpdate();
		conn.commit();
		st.close();
		if(deleted == 0)
			throw new SQLException();
	}

	@Override
	public void modifyAccount(Account account) throws SQLException {
		PreparedStatement update = conn.prepareStatement("UPDATE szemely " + "SET eha =?," + " nev =?,"
				+ " szuletesi_datum =to_date(?, 'YYYY-MM-DD')," + " cim =?" + " where eha =?");
		update.setString(1, account.getEha());
		update.setString(2, account.getName());
		update.setString(3, account.getBirthDate());
		update.setString(4, account.getAddress());
		update.setString(5, account.getEha());
		update.executeUpdate();
		update.close();

		PreparedStatement delete = conn.prepareStatement("DELETE FROM szak WHERE eha = ?");
		delete.setString(1, account.getEha());
		delete.executeUpdate();
		delete.close();

		PreparedStatement insert = conn.prepareStatement("INSERT INTO szak VALUES (?, ?)");
		insert.setString(1, account.getEha());
		for (String sz : account.getDepartment()) {
			insert.setString(2, sz);
			insert.executeUpdate();
		}
		insert.close();
		conn.commit();
	}

	@Override
	public void changePassword(String eha, String password) throws SQLException {
		PreparedStatement updatePass = conn.prepareStatement("UPDATE szemely SET jelszo = ora_hash(concat(?, 'etr')), jelszo1 = ? WHERE eha = ?");
		updatePass.setString(1, password);
		updatePass.setString(2, password);
		updatePass.setString(3, eha);
		updatePass.executeUpdate();
		updatePass.close();
		conn.commit();
	}

	@Override
	public ArrayList<Course> getCourses() throws SQLException {
		PreparedStatement getCourse = conn.prepareStatement(""
				+ "SELECT egy.kurzuskod, egy.nev, egy.het_napja, egy.mettol, egy.meddig, egy.kredit, terem.nev, terem.kapacitas, ketto.kurzuskod, 0 "
				+ "FROM (kurzus egy LEFT JOIN kurzus ketto ON egy.eloadasa = ketto.kurzuskod) INNER JOIN terem ON egy.teremkod = terem.teremkod "
				+ "ORDER BY egy.kurzuskod");
		return courseGetter(getCourse);
	}
	
	@Override
	public ArrayList<Course> getCourses(String eha) throws SQLException {
		PreparedStatement getCourse = conn.prepareStatement(""
				+ "SELECT egy.kurzuskod, egy.nev, egy.het_napja, egy.mettol, egy.meddig, egy.kredit, terem.nev, terem.kapacitas, ketto.kurzuskod, hallgatja.osztalyzat "
				+ "FROM ((hallgatja INNER JOIN kurzus egy ON hallgatja.kurzuskod = egy.kurzuskod) LEFT JOIN kurzus ketto ON egy.eloadasa = ketto.kurzuskod) INNER JOIN terem ON egy.teremkod = terem.teremkod "
				+ "WHERE hallgatja.eha = ? "
				+ "ORDER BY egy.kurzuskod ");
		getCourse.setString(1, eha);
		return courseGetter(getCourse);
		
	}
	
	private ArrayList<Course> courseGetter(PreparedStatement ps) throws SQLException{
		ArrayList<Course> courses = new ArrayList<>();
		ResultSet course = ps.executeQuery();
		while(course.next()) {
			String kurzusKod = course.getString(1);
			PreparedStatement getOnit = conn.prepareStatement("SELECT count(*) FROM hallgatja WHERE kurzuskod = ?");
			getOnit.setString(1, kurzusKod);
			ResultSet onIt = getOnit.executeQuery();
			int countOnIt = 0;
			while(onIt.next())
				countOnIt = onIt.getInt(1);
			
			PreparedStatement getProff = conn.prepareStatement(""
					+ "SELECT nev "
					+ "FROM szemely INNER JOIN tanitja ON szemely.eha = tanitja.eha "
					+ "WHERE tanitja.KURZUSKOD = ?");
			getProff.setString(1, kurzusKod);
			ResultSet proff = getProff.executeQuery();
			String profs = "";
			while(proff.next())
				profs += proff.getString(1) + " ";
			
			Course c = new Course(kurzusKod, course.getString(2), course.getString(3), course.getString(4),
					course.getString(5), course.getInt(6), course.getString(7), countOnIt, course.getInt(8), course.getString(9), profs, course.getInt(10));
			courses.add(c);
			getOnit.close();
			getProff.close();
		}
		
		ps.close();
		return courses;
	}

	@Override
	public ArrayList<Exam> getExams() throws SQLException {
		PreparedStatement getExam = conn.prepareStatement(""
				+ "SELECT vizsga.idopont, vizsga.vizsgakod, kurzus.nev, terem.nev, vizsga.ar, 0 "
				+ "FROM (vizsga INNER JOIN kurzus ON vizsga.kurzuskod = kurzus.kurzuskod) INNER JOIN terem ON vizsga.teremkod = terem.teremkod "
				+ "ORDER BY vizsga.vizsgakod");
		return examGetter(getExam);
	}


	@Override
	public ArrayList<Exam> getExams(String eha) throws SQLException {
		PreparedStatement getExam = conn.prepareStatement(""
				+ "SELECT vizsga.idopont, vizsga.vizsgakod, kurzus.nev, terem.nev, vizsga.ar , vizsgazik.vizsgajegy "
				+ "FROM ((vizsgazik INNER JOIN vizsga ON vizsgazik.vizsgakod = vizsga.vizsgakod) INNER JOIN kurzus ON vizsga.kurzuskod = kurzus.kurzuskod) INNER JOIN terem ON vizsga.teremkod = terem.teremkod "
				+ "WHERE vizsgazik.eha = ?"
				+ "ORDER BY vizsga.vizsgakod");
		getExam.setString(1, eha);
		return examGetter(getExam);
	}
	
	private ArrayList<Exam> examGetter(PreparedStatement ps) throws SQLException{
		ArrayList<Exam> exams = new ArrayList<>();
		ResultSet exam = ps.executeQuery();
		while(exam.next()) {
			String timestamp = exam.getString(1);
			String time = (timestamp.split("\\."))[0] + ", " + (timestamp.split("\\."))[1] + ":" + (((timestamp.split("\\."))[2].length() == 1) ? "0" : "") + (timestamp.split("\\."))[2];
			Exam e = new Exam(exam.getString(2), exam.getString(3), time, exam.getString(4), exam.getInt(5), exam.getInt(6));
			exams.add(e);
		}
		ps.close();
		return exams;
	}

	@Override
	public void pickUpACourse(String eha, String courseCode) throws SQLException {
		PreparedStatement pickUp = conn.prepareStatement("INSERT INTO HALLGATJA VALUES (?, ?, 0, Sysdate)");
		pickUp.setString(1, eha);
		pickUp.setString(2, courseCode);
		pickUp.executeQuery();
		conn.commit();
		pickUp.close();
	}

	@Override
	public void pickUpAnExam(String eha, Exam exam) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveMarkOnCourse(String eha, String courseCode, int mark) throws SQLException {
		PreparedStatement giveMark = conn.prepareStatement("UPDATE hallgatja SET osztalyzat = ? WHERE eha = ? AND kurzuskod = ?");
		giveMark.setInt(1, mark);		
		giveMark.setString(2, eha);
		giveMark.setString(3, courseCode);
		giveMark.executeQuery();
		conn.commit();
		giveMark.close();
	}
	
}
