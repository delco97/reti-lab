package L06_NIO_Channel_Buffer_JSON.JSON;

//######## JAVA TO JSON

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONFileCreator2 {
	
	public static void main(String[] args) {
		ObjectMapper objectMapper = new ObjectMapper();
		//Creazione oggetto di esempio
		Country countryObj = new Country();
		countryObj.setName("Italia");
		countryObj.setPopulation(54000000);
		countryObj.addRegion("Toscana");
		countryObj.addRegion("Sicilia");
		countryObj.addRegion("Veneto");
		try {
			File file = new File("src/main/java/L06_NIO_Channel_Buffer_JSON/JSON/RegionFileJackson.json");
			file.createNewFile();
			System.out.println("Writing JSON object to file");
			System.out.println("-----------------------");
			//Serializza l’oggetto
			objectMapper.writeValue(file, countryObj);
			//FileWriter fileWriter = new FileWriter(file);
			// in alternativa //objectMapper.writeValue(fileWriter, countryObj); //fileWriter.close();
			System.out.println("Writing JSON object to string");
			System.out.println(objectMapper.writeValueAsString(countryObj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}