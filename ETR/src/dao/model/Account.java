package dao.model;

import java.util.ArrayList;


public class Account {

	protected String eha;
	protected String name;
	protected String birthDate;
	protected String address;
	protected ArrayList<String> department;
	protected Integer type;

	public Account(String eha, String name, String birthDate, String address, ArrayList<String> department, Integer type) {
		this.name = name;
		this.eha = eha;
		this.birthDate = birthDate;
		this.address = address;
		this.department = department;
		this.type = type;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
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
