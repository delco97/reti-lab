package L08_SocketMultiplexing.IntGen;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.IOException;

public class IntGenClient {
	
	public static int DEFAULT_PORT = 1920;
	
	public static void main(String[] args) {
		String host;
		if (args.length == 0) {
			System.out.println("Usage: java IntgenClient host [port]");
			host = "localhost";
		} else host = args[0];
		int port;
		try {
			port = Integer.parseInt(args[1]);
		} catch (RuntimeException ex) {
			port = DEFAULT_PORT;
		}
		try {
			SocketAddress address = new InetSocketAddress(host, port);
			SocketChannel client = SocketChannel.open(address);
			ByteBuffer buffer = ByteBuffer.allocate(4);
			IntBuffer view = buffer.asIntBuffer();
			for (int expected = 0; ; expected++) {
				client.read(buffer);
				int actual = view.get();
				buffer.clear();
				view.rewind();
				if (actual != expected) {
					System.err.println("Expected " + expected + "; was " + actual);
					break;
				}
				System.out.println(actual);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}