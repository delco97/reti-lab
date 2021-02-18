package L07_InetAddress_TCPSockets.Capitalizer;

import java.net.ServerSocket;
import java.util.concurrent.*;
public class CapitalizerServer {
	
	
	public static void main(String[] args) throws Exception {
		try (ServerSocket listener = new ServerSocket(10000)) {
			System.out.println("The capitalization server is running...");
			ExecutorService pool = Executors.newFixedThreadPool(20);
			while (true) {
				pool.execute(new Capitalizer(listener.accept()));
			}
		}
	}
	
}
