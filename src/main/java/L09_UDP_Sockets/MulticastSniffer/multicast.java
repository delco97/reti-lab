package L09_UDP_Sockets.MulticastSniffer;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;

public class multicast {
	
	public static void main(String args[]) {
		try {
			InetAddress ia = InetAddress.getByName("228.5.6.7");
			byte[] data;
			data = "hello".getBytes();
			int port = 6789;
			DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
			DatagramSocket ms = new DatagramSocket(6789);
			ms.send(dp);
			Thread.sleep(80000);
		} catch (IOException ex) {
			System.out.println(ex);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}