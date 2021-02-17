package L06_NIO_Channel_Buffer_JSON.Bank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainClass {
	
	public static Date getRandomDate(int startYear, int endYear) {
		GregorianCalendar gc = new GregorianCalendar();
		
		int year = randBetween(startYear, endYear);
		gc.set(gc.YEAR, year);
		
		int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		gc.set(gc.DAY_OF_YEAR, dayOfYear);
		
		return gc.getTime();

	}
	
	public static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}
	
	public static BankAccount randBankAccount(int id) {
		BankAccount tmp = new BankAccount("ID_"+ id);
		int nMov = randBetween(10, 100);
		for (int i = 0; i < nMov; i++) {
			int val = randBetween(5,10000);
			MovmentType t = MovmentType.randomEnum();
			if(t != MovmentType.Accredito) val *= -1;
			Date dt = getRandomDate(2019,2021);
			tmp.addMovment(new Movment(val, dt, t));
		}
		return tmp;
	}
	
	public static void createDemoBankAccounts(String filePath, int n) {
		ObjectMapper m = new ObjectMapper();
		m.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		List<BankAccount> accounts = new ArrayList<>();
		try{
			File f = new File(filePath);
			f.createNewFile();
			//Create all random accounts
			for (int i = 0; i < n; i++)
				accounts.add(randBankAccount(i));
			//Write them as json array object
			m.writeValue(f,accounts);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		final String DBfilePath = "src/main/java/L06_NIO_Channel_Buffer_JSON/Bank/BankDB/accounts.json";
		final int nAccounts = 10;
		DBManager dbManager = new DBManager();
		
		//Create a demo DB with randomly generated bank accounts
		createDemoBankAccounts(DBfilePath, nAccounts);
		
		new Thread(new DBReader(DBfilePath,dbManager)).start();
	}
}
