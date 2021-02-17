package L06_NIO_Channel_Buffer_JSON.Bank;

import java.util.Calendar;
import java.util.Date;

public class Movment {
	double val;
	Date dt;
	MovmentType type;
	
	private Movment(){}
	
	Movment(double val, Date dt, MovmentType type) {
		this.val = val;
		this.dt = dt;
		this.type = type;
	}
	
	Movment(double val, MovmentType type) {
		this.val = val;
		this.dt = Calendar.getInstance().getTime();
		this.type = type;
	}
	
	public double getVal() {
		return val;
	}
	
	public Date getDt() {
		return dt;
	}
	
	public MovmentType getType() {
		return type;
	}
	
	public void setVal(double val) {
		this.val = val;
	}
	
	public void setDt(Date dt) {
		this.dt = dt;
	}
	
	public void setType(MovmentType type) {
		this.type = type;
	}
	
}
