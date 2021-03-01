package L09_UDP_Sockets.PreAssignment_PinPong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	
	public static void main(String args[]) throws Exception {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = "PING".getBytes();
		byte[] receiveData = new byte[1024];
		while (true) {
			Thread.sleep(1000);
			//Send PING
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8340);
			clientSocket.send(sendPacket);
			//Wait PONG
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String srvAnswer = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + srvAnswer);
		}
	}
	
}
