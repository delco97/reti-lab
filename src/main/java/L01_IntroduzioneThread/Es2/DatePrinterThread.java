package L01_IntroduzioneThread.Es2;

import java.util.Calendar;
import java.util.Date;

public class DatePrinterThread extends Thread {
	public static void main(String[] args) {
		Thread t = new DatePrinterThread();
		t.start();
		System.out.println(currentThread().getName());
	}
	
	@Override
	public void run() {
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
	}
	
}
