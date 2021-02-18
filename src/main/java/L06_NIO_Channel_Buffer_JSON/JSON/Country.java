package L06_NIO_Channel_Buffer_JSON.JSON;

//Classe che rappresenta la nazione e relativi metodi

import java.util.ArrayList;

public class Country {
	
	private String name;
	private int population;
	private final ArrayList<String> regions = new ArrayList<String>();
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public void setPopulation(int population) { this.population = population;}
	
	public int getPopulation() { return population; }
	
	public void addRegion(String region) {
		this.regions.add(region);
	}
	
	public ArrayList<String> getRegions() { return regions; }
	
}
