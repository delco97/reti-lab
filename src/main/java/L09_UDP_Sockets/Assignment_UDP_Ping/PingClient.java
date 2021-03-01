package L09_UDP_Sockets.Assignment_UDP_Ping;

import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class PingClient {
	
	private static final int MAX_SEQ = 9;
	private static final int ECHO_WAIT_LIMIT = 2000;
	
	public static void main(String args[]) throws IOException, InterruptedException {
		InetAddress IPAddress;
		int port;
		//Check arguments
		if (args.length != 2) {
			showUsage("Wrong arguments number");
			return;
		}
		//Arguments number is correct
		try {
			IPAddress = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			showUsage("First argument must be a valid and reachable server name.");
			return;
		}
		//Got server IP
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			showUsage("Second argument must be a valid port number.");
			return;
		}
		//Got valid number for a port
		DatagramSocket clientSocket = new DatagramSocket();
		clientSocket.setSoTimeout(ECHO_WAIT_LIMIT);
		byte[] sendData;
		byte[] receiveData = new byte[1024];
		int nTX = 0, nRX = 0;
		long minRTT = Long.MAX_VALUE, maxRTT = Long.MIN_VALUE;
		float avgRTT = 0;
		Timestamp start = null, end = null;
		for (int seq = 0; seq <= MAX_SEQ; seq++) {
			Thread.sleep(1000);
			//Send PING request
			start = new Timestamp(new Date().getTime());
			String msg = ("PING " + seq + " " + start);
			System.out.println("Sent to server: " + msg);
			sendData = msg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			nTX++;
			//Wait PING Echo
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				clientSocket.receive(receivePacket);
				//Response received!
				nRX++;
				//				String srvAnswer = new String(receivePacket.getData());
				//				System.out.println("seq=" + seq + " " + srvAnswer);
				end = new Timestamp(new Date().getTime());
				long milliseconds = end.getTime() - start.getTime();
				minRTT = Math.min(minRTT, milliseconds);
				maxRTT = Math.max(maxRTT, milliseconds);
				avgRTT += milliseconds;
				System.out.println("seq=" + seq + " rtt=" + milliseconds + " ms");
			} catch (SocketTimeoutException e) {
				System.out.println("seq=" + seq + " rtt=*");
			}
		}
		avgRTT /= nRX;
		int nLoss = nTX - nRX;
		//PING statistics
		System.out.println("---- PING Statistics ----");
		System.out.printf("%d packets transmitted\n" +
				"%d packet received\n" +
				"%d %% packet loss\n" +
				"round-trip (ms) min/avg/max = %d/%.2f/%d",
				nTX, nRX, (nLoss / nTX)*100, minRTT, avgRTT, maxRTT);
		
		
	}
	
	private static void showUsage(String s) {
		System.err.println(s);
		System.out.println("command syntax: ping <host> <port>");
	}
	
}
