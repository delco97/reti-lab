package L09_UDP_Sockets.UDP_Send_Recive;

import java.net.*;

public class Sender {
	
	public static void main(String args[]) {
		try {
			DatagramSocket clientsocket = new DatagramSocket();
			byte[] buffer = "1234567890abcdefghijklmnopqrstuvwxyz".getBytes("US-ASCII");
			InetAddress address = InetAddress.getByName("localhost");
			for (int i = 1; i < buffer.length; i++) {
				DatagramPacket mypacket = new DatagramPacket(buffer, i, address, 40000);
				clientsocket.send(mypacket);
				Thread.sleep(200);
			}
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}