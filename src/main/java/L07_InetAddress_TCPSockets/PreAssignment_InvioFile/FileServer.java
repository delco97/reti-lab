package L07_InetAddress_TCPSockets.PreAssignment_InvioFile;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.nio.*;

public class FileServer {
	
	public final static int PORT = 6000;
	
	public static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(x);
		return buffer.array();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		int count;
		byte[] buffer = new byte[8192];
		System.out.println("Use this command to send a request to the server:");
		System.out.println("    telnet localhost 6000");
		try (ServerSocket server = new ServerSocket(PORT)) {
			while (true) {
				System.out.println("Waiting request...");
				try (Socket connection = server.accept()) {
					System.out.println("Preparing file...");
					FileInputStream file = new FileInputStream(new File("src/main/java/L07_InetAddress_TCPSockets/PreAssignment_InvioFile/demo.txt"));
					OutputStream out = connection.getOutputStream();
					//Write File
					while ((count = file.read(buffer)) > 0)
					{
						out.write(buffer, 0, count);
					}
					out.flush();
					System.out.println("File sent!");
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
}