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
		    Class.forName ("oracle.jdbc.OracleDriver"); 
		    ods.setURL("jdbc:oracle:thin:@localhost:4000:kabinet"); 
		    conn = ods.getConnection("h675353","geniusos123");
		    
		} catch ( Exception ex ) { 
		    ex.printStackTrace(); 
		}
	}

	@Override
	public Account getAccount(String eha, String password) {
		return null;
	}
	
	@Override
	public Account getAccountToModify(String eha) {
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM SZEMELY WHERE EHA ='" + eha +"'" );
	        while (rs.next()) {
	        	ArrayList<String> dep = new ArrayList<String>();
	        	Account acc = new Referent(rs.getString(2),rs.getString(1),rs.getDate(3),rs.getString(4),dep);
	        	return acc;
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
