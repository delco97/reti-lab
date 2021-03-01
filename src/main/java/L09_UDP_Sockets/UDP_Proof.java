package L09_UDP_Sockets;

import java.net.*;

public class UDP_Proof {
	public static void main(String args[]) throws Exception {
		DatagramSocket dgs = new DatagramSocket();
		int r = dgs.getReceiveBufferSize();
		int s = dgs.getSendBufferSize();
		System.out.println("receive buffer" + r);
		System.out.println("send buffer" + s);
	}
}