package L07_InetAddress_TCPSockets.Capitalizer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Capitalizer implements Runnable {
	
	private Socket socket;
	
	Capitalizer(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		System.out.println("Connected: " + socket);
		try (Scanner in = new Scanner(socket.getInputStream());
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
			while (in.hasNextLine()) {
				out.println(in.nextLine().toUpperCase());
			}
		} catch (Exception e) {
			System.out.println("Error:" + socket);
		}
	}
	
}
