package L09_UDP_Sockets.MulticastBasic;

import java.net.*;

public class provemulticast {
	
	public static void main(String args[]) throws Exception {
		
		byte[] buf = new byte[10];
		
		InetAddress ia = InetAddress.getByName("228.0.0.4");
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		
		
		
		MulticastSocket ms = new MulticastSocket(4000);
		//ms.setNetworkInterface(NetworkInterface.getByName("en0"));
		ms.joinGroup(ia);
		ms.receive(dp);
		System.out.println("Pacchetto ricevuto!");
		
	}
	
}
