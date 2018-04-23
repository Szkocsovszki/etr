package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.model.Account;
import dao.model.Professor;
import dao.model.Referent;
import dao.model.Student;
//import oracle.jdbc.pool.OracleDataSource;

public class ETRDAOImpl implements ETRDAO {

	private Connection conn;

	public ETRDAOImpl() {

		super();
		try {
			// OracleDataSource ods = new OracleDataSource();
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:4000:kabinet", "h675353", "geniusos123");
			// ods.setURL("jdbc:oracle:thin:@localhost:4000:kabinet");
			// conn = ods.getConnection("h675353", "geniusos123");
			conn.setAutoCommit(false);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Account getAccount(String eha, String password) throws SQLException {
		return null;
	}

	@Override
	public Account getAccountToModify(String eha) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet accExists = st.executeQuery("SELECT * FROM SZEMELY WHERE EHA ='" + eha + "'");
		if (accExists.next()) {
			String accName = accExists.getString(2);
			String accEHA = accExists.getString(1);
			String accBirth = accExists.getString(3);
			String accAddress = accExists.getString(4);
			accExists.close();

			ArrayList<String> departmentStore = new ArrayList<String>();
			ResultSet departments = st.executeQuery("SELECT szaknev FROM SZAK WHERE EHA ='" + eha + "'");
			while (departments.next())
				departmentStore.add(departments.getString(1));
			departments.close();

			if(departmentStore.isEmpty()) departmentStore = null;


			ResultSet accTypeCheck = st.executeQuery("SELECT * FROM REFERENS WHERE EHA ='" + eha + "'");
			if (accTypeCheck.next()) {
				accTypeCheck.close();
				accExists.close();
				return new Referent(accName, accEHA, accBirth, accAddress, departmentStore);
			} else {
				accTypeCheck = st.executeQuery("SELECT * FROM OKTATO WHERE EHA ='" + eha + "'");
				if (accTypeCheck.next()) {
					accTypeCheck.close();
					accExists.close();
					return new Professor(accName, accEHA, accBirth, accAddress, departmentStore);
				} else {
					accTypeCheck = st.executeQuery("SELECT * FROM HALLGATO WHERE EHA ='" + eha + "'");
					if (accTypeCheck.next()) {
						accTypeCheck.close();
						accExists.close();
						return new Student(accName, accEHA, accBirth, accAddress, departmentStore);
					}
				}
			}
			accTypeCheck.close();
		}
		accExists.close();
		return null;
	}

	@Override
	public void createAccount(Account account) throws SQLException {

	}

	@Override
	public void deleteAccount(String eha) throws SQLException {
		PreparedStatement st = conn.prepareStatement("DELETE FROM szemely WHERE eha = ? ");
		st.setString(1, eha);
		st.executeUpdate();
		conn.commit();
		st.close();
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
	public int modifyAccount(Account account) throws SQLException {
		PreparedStatement update = conn.prepareStatement("UPDATE szemely " + "SET eha =?," + " nev =?,"
				+ " szuletesi_datum =to_date(?, 'YYYY-MM-DD')," + " cim =?" + " where eha =?");
		update.setString(1, account.getEha());
		update.setString(2, account.getName());
		update.setString(3, account.getBirthDate());
		update.setString(4, account.getAddress());
		update.setString(5, account.getEha());
		int szemely = update.executeUpdate();
		update.close();

		PreparedStatement delete = conn.prepareStatement("DELETE FROM szak WHERE eha = ?");
		delete.setString(1, account.getEha());
		int szakDel = delete.executeUpdate();
		delete.close();

		PreparedStatement insert = conn.prepareStatement("INSERT INTO szak VALUES (?, ?)");
		int szakIns = 0;
		insert.setString(1, account.getEha());
		for (String sz : account.getDepartment()) {
			insert.setString(2, sz);
			szakIns += insert.executeUpdate();
		}
		insert.close();
		conn.commit();
		return szemely + szakDel + szakIns;
	}

	@Override
	public void changePassword() throws SQLException {

	}
}
