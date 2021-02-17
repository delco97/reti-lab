package L06_NIO_Channel_Buffer_JSON.Bank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DBReader implements Runnable {
	
	private String DBfilePath;
	private DBManager dbManager;
	private File DBFile;
	private String LOG_TAG = "[DBReader]";
	
	public DBReader(String DBfilePath, DBManager dbManager) {
		this.DBfilePath = DBfilePath;
		this.dbManager = dbManager;
		this.DBFile = new File(DBfilePath);
	}
	
	@Override
	public void run() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		List<BankAccount> accounts;
		try {
			//Get objects from json
			accounts = Arrays.asList(objectMapper.readValue(DBFile, BankAccount[].class));
			for (BankAccount a : accounts)
				dbManager.analizeAccount(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
