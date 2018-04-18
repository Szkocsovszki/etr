package dao.model;

import java.sql.Date;
import java.util.ArrayList;


public class Referent extends Account {

	public Referent(String name, String eha, Date birthDate, String address, ArrayList<String> department) {
		this.name = name;
		this.eha = eha;
		this.birthDate = birthDate;
		this.address = address;
		this.department = department;
	}

}