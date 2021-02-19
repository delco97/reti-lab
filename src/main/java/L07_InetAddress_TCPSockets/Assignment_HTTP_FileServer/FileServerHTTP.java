package L07_InetAddress_TCPSockets.Assignment_HTTP_FileServer;

import L07_InetAddress_TCPSockets.Capitalizer.Capitalizer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServerHTTP {
	
	public final static int PORT = 6789;
	public final static int NUM_THREADS = 20;
	
	public static void main(String[] args) throws IOException {

		System.out.println("Using a browser send a request to this server with the following command:");
		System.out.println("localhost:" + PORT + "/<filename>");
		
		try (ServerSocket listener = new ServerSocket(PORT)) {
			System.out.println("The file server HTTP based is running...");
			ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
			while (true) { //Constantly waiting for new connections
				pool.execute(new RequestWorker(listener.accept()));
			}
		}
	}
	
	
}