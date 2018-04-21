package dao.model;

import java.util.ArrayList;
import java.sql.Date;

public class Professor extends Account {
	public Professor(String name, String eha, String birthDate, String address, ArrayList<String> department) {
		this.name = name;
		this.eha = eha;
		this.birthDate = birthDate;
		this.address = address;
		this.department = department;
	}

	@Override
	public String tableName() {
		return "oktato";
	}
}
