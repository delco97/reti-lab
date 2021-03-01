package L09_UDP_Sockets.UDP_Send_Recive;

import java.net.*;

public class Receiver {
	
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSock = new DatagramSocket(40000);
		byte[] buffer = new byte[100];
		DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
		while (true) {
			serverSock.receive(receivedPacket);
			String byteToString = new String(receivedPacket.getData(), 0, receivedPacket.getLength(), "US-ASCII");
			System.out.println("Length " + receivedPacket.getLength() + " data " + byteToString);
		}
	}
	
}