package L07_InetAddress_TCPSockets;

import java.net.*;
import java.util.*;

public class InterfaceListener {
	
	public static void main(String[] args) throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface ni = interfaces.nextElement();
			System.out.println(ni);
		}
	}
	
}
