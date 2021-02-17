package L06_NIO_Channel_Buffer_JSON.Bank;

import java.util.ArrayList;

public class BankAccount {
	private String name;
	private ArrayList<Movment> movements;
	
	private BankAccount() {}
	
	public BankAccount(String name, ArrayList<Movment> movements) {
		this.name = name;
		this.movements = movements;
	}
	
	public BankAccount(String name) {
		this.name = name;
		this.movements = new ArrayList<Movment>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Movment> getMovements() {
		return movements;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMovements(ArrayList<Movment> movements) {
		this.movements = movements;
	}
	
	public void addMovment(Movment m) {
		movements.add(m);
	}
}
