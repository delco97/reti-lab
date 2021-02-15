package L02_Threadpools.Assignment02_PostalOffice;

import java.util.concurrent.ThreadLocalRandom;

public class User implements Runnable{
	static final String[] randNames = new String[]{
			"Bob",
			"Alice",
			"Eve"
	};
	
	private int serviceTime;
	private int serviceNumber;
	private String name;
	private final int MIN_SERVICE_TIME = 10;
	private final int MAX_SERVICE_TIME = 1000;
	
	User(String p_name, int p_num) {
		name = p_name;
		serviceNumber = p_num;
		serviceTime = ThreadLocalRandom.current().nextInt(MIN_SERVICE_TIME,MAX_SERVICE_TIME+1);
	}
	
	User(int p_num) {
		this(getRandomUserName(), p_num);
	}
	
	User() {
		this(getRandomUserName(), -1);
	}
	
	public void setServiceNumber(int serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	
	@Override
	public String toString() {
		return "User{" + "serviceNumber=" + serviceNumber + ", name='" + name + '\'' + '}';
	}
	
	public static String getRandomUserName() {
		return randNames[ThreadLocalRandom.current().nextInt(0, randNames.length)];
	}
	
	@Override
	public void run() {
		try {
			System.out.println(toString() + ": service started.");
			Thread.currentThread().sleep(serviceTime);
		} catch (InterruptedException e) {
			System.out.println(toString() + ": service have been interrupted!");
			e.printStackTrace();
		}
		System.out.println(toString() + ": service is finished.");
	}
	
}
