package L01_IntroduzioneThread.Es3;

import L01_IntroduzioneThread.Es2.DatePrinterThread;

import java.util.Calendar;
import java.util.Date;

public class DatePrinterRunnable implements Runnable {
	public static void main(String[] args) {
		Runnable task = new DatePrinterRunnable();
		Thread t = new Thread(task);
		t.start();
		System.out.println(Thread.currentThread().getName());
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
