package L07_InetAddress_TCPSockets.Capitalizer;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class CapitalizerClient {
	
	public static void main(String[] args) throws Exception {
		InetAddress host;
		if (args.length == 1) {
			host = InetAddress.getByName(args[0]);
		} else {
			host = InetAddress.getByName("localhost");
		}
		Scanner userInput = null;
		Scanner incomingData = null;
		try (Socket socket = new Socket(host, 10000)) {
			System.out.println("Enter lines of text then EXIT to quit");
			userInput = new Scanner(System.in);
			incomingData = new Scanner(socket.getInputStream());
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			boolean end = false;
			while (!end) {
				{
					String line = userInput.nextLine();
					if (line.contentEquals("exit")) end = true;
					out.println(line);
					System.out.println(incomingData.nextLine());
				}
			}
		} finally {
			userInput.close();
			incomingData.close();
		}
	}
	
}
	
