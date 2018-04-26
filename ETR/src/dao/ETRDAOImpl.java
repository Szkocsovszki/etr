package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.model.Account;
import dao.model.Professor;
import dao.model.Referent;
import dao.model.Student;
import view.Labels;

public class ETRDAOImpl implements ETRDAO {

	private Connection conn;

	public ETRDAOImpl() {

		super();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:4000:kabinet", "h675353", "geniusos123");
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
			String accName = accExists.getString(2);
			String accEHA = accExists.getString(1);
			String accBirth = (accExists.getString(3).split(" "))[0];
			String accAddress = accExists.getString(4);
			accExists.close();

			ArrayList<String> departmentStore = new ArrayList<String>();
			st = conn.prepareStatement("SELECT szaknev FROM szak WHERE EHA = ?");
			st.setString(1, eha);
			ResultSet departments = st.executeQuery();
			while (departments.next())
				departmentStore.add(departments.getString(1));
			departments.close();
			
			st = conn.prepareStatement("SELECT * FROM referens WHERE EHA = ?");
			st.setString(1, eha);
			ResultSet accTypeCheck = st.executeQuery();
			if (accTypeCheck.next()) {
				accTypeCheck.close();
				st.close();
				return new Referent(accName, accEHA, accBirth, accAddress, departmentStore);
			} else {
				st = conn.prepareStatement("SELECT * FROM oktato WHERE EHA = ?");
				st.setString(1, eha);
				accTypeCheck = st.executeQuery();
				if (accTypeCheck.next()) {
					accTypeCheck.close();
					st.close();
					return new Professor(accName, accEHA, accBirth, accAddress, departmentStore);
				} else {
					st = conn.prepareStatement("SELECT * FROM hallgato WHERE EHA = ?");
					st.setString(1, eha);
					accTypeCheck = st.executeQuery();
					if (accTypeCheck.next()) {
						accTypeCheck.close();
						st.close();
						return new Student(accName, accEHA, accBirth, accAddress, departmentStore);
					}
				}
			}
			accTypeCheck.close();
		}
		accExists.close();
		st.close();
		return null;
	}

	@Override
	public void createAccount(Account account) throws SQLException {
		PreparedStatement st = conn.prepareStatement("INSERT INTO szemely VALUES (?,?,to_date(?, 'YYYY-MM-DD'),?,?,ora_hash(concat(?,'etr')))");
		st.setString(1, account.getEha());
		st.setString(2, account.getName());
		st.setString(3, account.getBirthDate());
		st.setString(4, account.getAddress());
		st.setString(5, account.getEha());
		st.setString(6, account.getEha());
		st.executeUpdate();
		
		st = conn.prepareStatement("INSERT INTO szak VALUES (?,?)");
		st.setString(1, account.getEha());
		for(String sz : account.getDepartment()) {
			st.setString(2, sz);
			st.executeQuery();
		}

		st = conn.prepareStatement("INSERT INTO "+account.tableName()+"(eha) VALUES (?)");
		st.setString(1, account.getEha());
		st.executeQuery();
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

	public void updateStudentBalance(Account acc, int balance) throws SQLException {
		PreparedStatement st = conn.prepareStatement("UPDATE hallgato SET egyenleg = ? WHERE eha = ?");
		st.setInt(1, balance);
		st.setString(2, acc.getEha());
		st.executeUpdate();
		st.close();
		conn.commit();
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
	public ArrayList<String> getCourses() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getExams() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> pickUpACourse(String eha, String courseCode) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> pickUpAnExam(String eha, String examCode) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
