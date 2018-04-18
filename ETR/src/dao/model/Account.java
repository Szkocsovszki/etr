package dao.model;

import java.sql.Date;
import java.util.ArrayList;


public abstract class Account {
	protected String name;
	protected String eha;
	protected Date birthDate;
	protected String address;
	protected ArrayList<String> department;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEha() {
		return eha;
	}

	public void setEha(String eha) {
		this.eha = eha;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<String> getDepartment() {
		return department;
	}

	public void setDepartment(ArrayList<String> department) {
		this.department = department;
	}

}
