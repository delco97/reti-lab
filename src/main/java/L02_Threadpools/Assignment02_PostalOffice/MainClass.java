package L02_Threadpools.Assignment02_PostalOffice;

public class MainClass {
	public static void main(String[] args) {
		int numUsers = 10;
		int nDesks = 4;
		int k = 3; //Max number of users in waiting queue of service room.
		
		PostalOffice office = new PostalOffice(nDesks, k);
		office.startSimulation(numUsers);
		office.manageOffice();
		office.endSimulation();
		
	}
}
