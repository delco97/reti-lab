package L07_InetAddress_TCPSockets.TimeRFC;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.nio.*;

public class TimeServer {
	
	public final static int PORT = 6000;
	
	public static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(x);
		return buffer.array();
	}
	
	public static void main(String[] args) {
		// The time protocol sets the epoch at 1900,
		// the Date class at 1970. This number
		// converts between them.
		long differenceBetweenEpochs = 2208988800L;
		try (ServerSocket server = new ServerSocket(PORT)) {
			while (true) {
				try (Socket connection = server.accept()) {
					OutputStream out = connection.getOutputStream();
					Date now = new Date();
					long msSince1970 = now.getTime();
					long secondsSince1970 = msSince1970 / 1000;
					long secondsSince1900 = secondsSince1970 + differenceBetweenEpochs;
					byte[] time = new byte[8];
					time = longToBytes(secondsSince1900);
					out.write(time);
					System.out.println(secondsSince1900);
					out.flush();
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
}