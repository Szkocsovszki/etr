package dao;

import java.sql.*;
import java.util.ArrayList;

import dao.model.*;
import dao.model.Account;
import oracle.jdbc.pool.OracleDataSource;

public class ETRDAOImpl implements ETRDAO {

	private Connection conn;

	public ETRDAOImpl() {

		super();
		try {
			OracleDataSource ods = new OracleDataSource();
			Class.forName("oracle.jdbc.OracleDriver");
			ods.setURL("jdbc:oracle:thin:@localhost:4000:kabinet");
			conn = ods.getConnection("h675353", "geniusos123");
			conn.setAutoCommit(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Account getAccount(String eha, String password) {
		return null;
	}

	@Override
	public Account getAccountToModify(String eha){
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet accExists = st.executeQuery("SELECT * FROM SZEMELY WHERE EHA ='" + eha + "'");
			if (accExists.next()) {
				String accName = accExists.getString(2);
				String accEHA = accExists.getString(1);
				Date accBirth = accExists.getDate(3);
				String accAddress = accExists.getString(4);
				accExists.close();
				
				ArrayList<String> departmentStore = new ArrayList<String>();
				ResultSet departments = st.executeQuery("SELECT szaknev FROM SZAK WHERE EHA ='" + eha + "'");
				while (departments.next())
					departmentStore.add(departments.getString(1));
				departments.close();

				ResultSet accTypeCheck = st.executeQuery("SELECT * FROM REFERENS WHERE EHA ='" + eha + "'");
				if (accTypeCheck.next()) 
					return new Referent(accName, accEHA, accBirth, accAddress, departmentStore);
				else {
					accTypeCheck = st.executeQuery("SELECT * FROM OKTATO WHERE EHA ='" + eha + "'");
					if(accTypeCheck.next())
						return new Professor(accName, accEHA, accBirth, accAddress, departmentStore);
					else {
						accTypeCheck = st.executeQuery("SELECT * FROM HALLGATO WHERE EHA ='" + eha + "'");
						if(accTypeCheck.next())
							return new Student(accName, accEHA, accBirth, accAddress, departmentStore);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void createAccount(Account account) {

	}

	@Override
	public void deleteAccount(String eha) {

	}

	@Override
	public void modifyAccount(Account account) {

	}

	@Override
	public void changePassword() {

	}
}
