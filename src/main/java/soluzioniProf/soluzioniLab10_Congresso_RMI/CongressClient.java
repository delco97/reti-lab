package soluzioniProf.soluzioniLab10_Congresso;

import L10_MulticastRMI.InfoEU.EUStatsService;
import soluzioniProf.soluzioniLab10_preAssign_Welcome.WelcomeClient;

import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Definire un client CongressClient che si unisce a welcomegroup, riceve un messaggio di welcome, quindi termina.
 *
 * @author Samuel Fabrizi
 * @version 1.0
 */
public class CongressClient {
	public static void main(String[] args) {
		try {
			//Setup RMI
			Registry registry = LocateRegistry.getRegistry(30000);
			CongressService stub = (CongressService) registry.lookup("CONGRESS-SERVER");
			Scanner in = new Scanner(System.in);
			while(true){ //Menù
				System.out.println("Which operation?:");
				System.out.println("1. Add speaker to a session\n2. Get congress program\n0. EXIT");
				try{ //Get operation
					switch(Integer.parseInt(in.nextLine())) {
						case 0:
							System.out.println("CLIENT EXIT");
							System.exit(0);
						case 1:
							insertSpeaker(in,stub);
							break;
						case 2:
							printProgram(stub);
							break;
						default:
							System.out.println("Unrecognized option");
							break;
					}
				}catch(NumberFormatException ex) { System.out.println("Not a number!");}
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public static void insertSpeaker(Scanner in, CongressService stub) throws RemoteException {
		int day, sessionNumber;
		System.out.println("Insert day: ");
		try{ day = Integer.parseInt(in.nextLine()); }catch(NumberFormatException ex){ System.out.println("Not a number!"); return;}
		System.out.println("Insert session number: ");
		try{ sessionNumber = Integer.parseInt(in.nextLine()); }catch(NumberFormatException ex){ System.out.println("Not a number!"); return;}
		System.out.println("Insert speaker: ");
		String speaker = in.nextLine();
		boolean result = stub.registerSpeaker(speaker, sessionNumber, day); //Use RMI to insert the Speaker to the i-session of the j-day
		if(!result) System.out.println("Operation error!"); else System.out.println("Operation done with success!");
	}
	
	public static void printProgram(CongressService stub) throws RemoteException {
		System.out.println(stub.printProgram());
	}
	
	
}