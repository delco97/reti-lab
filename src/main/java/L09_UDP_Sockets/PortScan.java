package L09_UDP_Sockets;

import java.net.*;

public class PortScan {
	
	private static final int UPPER_LIMIT_PORT = 6000;
	
	public static void main(String args[]) {
		for (int i = 1024; i < UPPER_LIMIT_PORT; i++) {
			try {
				DatagramSocket s = new DatagramSocket(i);
				System.out.println("Porta libera" + i);
			} catch (BindException e) {
				System.out.println("porta già in uso");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}