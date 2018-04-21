package dao;

import java.sql.*;
import java.util.ArrayList;

import dao.model.*;
import dao.model.Account;
//import oracle.jdbc.pool.OracleDataSource;

public class ETRDAOImpl implements ETRDAO {

	private Connection conn;

	public ETRDAOImpl() {

		super();
		try {
			//OracleDataSource ods = new OracleDataSource();
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:4000:kabinet", "h675353", "geniusos123");
			//ods.setURL("jdbc:oracle:thin:@localhost:4000:kabinet");
			//conn = ods.getConnection("h675353", "geniusos123");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Account getAccount(String eha, String password) throws SQLException{
		return null;
	}

	@Override
	public Account getAccountToModify(String eha) throws SQLException{
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
			/*if(departmentStore.isEmpty())
				departmentStore = null;*/

			ResultSet accTypeCheck = st.executeQuery("SELECT * FROM REFERENS WHERE EHA ='" + eha + "'");
			if (accTypeCheck.next()) {
				accTypeCheck.close();
				accExists.close();
				return new Referent(accName, accEHA, accBirth, accAddress, departmentStore);
			}else {
				accTypeCheck = st.executeQuery("SELECT * FROM OKTATO WHERE EHA ='" + eha + "'");
				if(accTypeCheck.next()) {
					accTypeCheck.close();
					accExists.close();
					return new Professor(accName, accEHA, accBirth, accAddress, departmentStore);
				}else {
					accTypeCheck = st.executeQuery("SELECT * FROM HALLGATO WHERE EHA ='" + eha + "'");
					if(accTypeCheck.next()) {
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
	public void createAccount(Account account) throws SQLException{

	}

	@Override
	public void deleteAccount(String eha) throws SQLException{

	}

	@Override
	public int modifyAccount(Account account) throws SQLException{
		Statement st = conn.createStatement();
		return st.executeUpdate("UPDATE "+account.tableName()+
				" SET eha='"+account.getEha()+", "+
				" nev='"+account.getName()+", "+
				" szuletesi datum=to_year('"+account.getBirthDate()+"', 'YYYY-MM-DD'), "+
				" cim='"+account.getAddress()+"' "+
				" where eha='"+account.getEha()+"'");
	}

	@Override
	public void changePassword() throws SQLException{

	}
}
