package L06_NIO_Channel_Buffer_JSON.JSON;

//######## JSON TO JAVA

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileReader {
	
	public static void main(String[] args) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File("src/main/java/L06_NIO_Channel_Buffer_JSON/JSON/RegionFileJackson.json");
		
		Country newCountry;
		
		try {
			
			newCountry = objectMapper.readValue(file, Country.class); //Classe Country utilizzata nella serializzazione
			System.out.println("Deserialized object from JSON");
			System.out.println("-----------------------");
			System.out.println("Country name " + newCountry.getName() + newCountry.getPopulation());
			System.out.println("Country regions " + newCountry.getRegions());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
