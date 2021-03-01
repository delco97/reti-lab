package L10_MulticastRMI.InfoEU;

class EUData {
	
	private String Language;
	private int population;
	private String Capital;
	
	EUData(String Lang, int pop, String Cap) {
		
		Language = Lang;
		
		population = pop;
		
		Capital = Cap;
		
	}
	
	String getLangs() { return Language; }
	
	int getPop() { return population; }
	
	String getCapital() { return Capital; }
	
}
