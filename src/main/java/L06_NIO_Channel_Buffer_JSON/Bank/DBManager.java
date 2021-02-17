package L06_NIO_Channel_Buffer_JSON.Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class DBManager {
	private ExecutorService desksPool; //Pool of threads to serve users.
	private static final long TIMEOUT_CLOSURE = 10000; //Timeout for deskPool shutdown
	private ArrayList<BankAccount> accounts;
	
	public DBManager() {
		this.desksPool = desksPool;
	}
	
	public void analizeAccount(BankAccount a) {
	
	}
	
}
