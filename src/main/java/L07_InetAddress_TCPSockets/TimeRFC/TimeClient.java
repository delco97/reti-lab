package L07_InetAddress_TCPSockets.TimeRFC;

import java.net.*;
import java.io.*;
import java.nio.*;

public class TimeClient {
	
	static public long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + 10);
		buffer.put(bytes);
		buffer.flip();//need flip
		return buffer.getLong();
	}
	
	public static void main(String[] args) {
		String hostname = "Localhost";
		Socket socket = null;
		byte[] time = new byte[8];
		try {
			socket = new Socket(hostname, 6000);
			InputStream in = socket.getInputStream();
			int x = in.read(time);
			System.out.println(x);
			Long timeL = bytesToLong(time);
			System.out.println(timeL);
		} catch (IOException ex) {
			System.out.println(ex);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) { // ignore
				}
			}
		}
	}
	
}