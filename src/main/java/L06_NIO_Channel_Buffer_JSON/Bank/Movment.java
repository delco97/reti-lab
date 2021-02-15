package L06_NIO_Channel_Buffer_JSON.Bank;

import java.util.Calendar;
import java.util.Date;

public class Movment {
	Date dt;
	MovmentType type;
	
	Movment(Date dt, MovmentType type) {
		this.dt = dt;
		this.type = type;
	}
	
	Movment(MovmentType type) {
		this.dt = Calendar.getInstance().getTime();
		this.type = type;
	}
	
}
