package dao.model;

import java.util.ArrayList;

public class Student extends Account {
	private int balance;
	
	public Student(String name, String eha, String birthDate, String address, ArrayList<String> department) {
		this.name = name;
		this.eha = eha;
		this.birthDate = birthDate;
		this.address = address;
		this.department = department;
		this.balance = 0;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String tableName() {
		return "hallgato";
	}
}
