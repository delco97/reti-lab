package L01_IntroduzioneThread.Es1;

import java.util.Calendar;
import java.util.Date;

public class DatePrinter {
	
	public static void main(String[] args) {
		while (true) {
			Date rightNow = Calendar.getInstance().getTime();
			String tName = Thread.currentThread().getName();
			System.out.println("[" + rightNow + "]: thread " + tName + " is going to sleep for 2 seconds");
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("[" + rightNow + "]: thread " + tName + " sleep interrupted.");
				return;
			}
		}
		// The following instruction is never reached.
		// System.out.println(Thread.currentThread().getName());
	}
}
