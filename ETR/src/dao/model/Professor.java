package dao.model;

import java.util.ArrayList;
import java.util.Date;

public class Professor extends Account {
	public Professor(String name, String eha, Date birthDate, String address, ArrayList<String> department) {
		this.name = name;
		this.eha = eha;
		this.birthDate = birthDate;
		this.address = address;
		this.department = department;
	}
}
