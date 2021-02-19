package L07_InetAddress_TCPSockets.Assignment_HTTP_FileServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RequestWorker implements Runnable {
	private Socket s;
	public final static String FILES_PATH = "src/main/java/L07_InetAddress_TCPSockets/Assignment_HTTP_FileServer/Files/";
	
	public RequestWorker(Socket s) {
		this.s = s;
	}
	
	// serving a request
	@Override
	public void run() {
		String file = null;
		try{
			file = check_url();
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(file != null) {
			try{
				File f= check_file(file);
				if(f != null) {
					send_header(f);
					send_file(f);
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// check the request is well formed, and get the requested file name
	public String check_url() throws IOException {
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		StringBuffer sb= new StringBuffer();
		int b;
		while((b = in.read()) != -1){
			if(b == '\n') break;
			sb.append((char) b);
		}
		String req= sb.toString();
		char[] arr= req.toCharArray();
		if(arr[0] != 'G'&& arr[1] != 'E'&& arr[2] != 'T'){
			send_not_allowed();
			return null;
		}else {
			String[] ret_arr= req.split(" ");
			return ret_arr[1].substring(1);
		}
	}
	
	// check whether the file exists
	private File check_file(String file) throws IOException{
		File f= new File(FILES_PATH + file);
		if(!f.exists()){
			send_not_found();
			return null;
		}
		return f;
	}
	
	// send the header of the http reply
	private void send_header(File f) throws IOException {
		OutputStream out = s.getOutputStream();
		out.write("HTTP/1.1 200 OK\r\n".getBytes());
		out.write(("Content-Length: "+f.length()+"\r\n").getBytes());
		out.write("\r\n".getBytes());
	}
	
	// send the requested file
	private void send_file(File file) throws IOException {
		int count;
		byte[] buffer = new byte[8192];

		//Set FileInput to transmit
		FileInputStream fs = new FileInputStream(file);
		OutputStream out = s.getOutputStream();
		while ((count = fs.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		out.flush();
		fs.close();
		out.close();
		System.out.println("File" + file.getName() + " sent!");
	}
	
	// request is not well formatted
	private void send_not_allowed() throws IOException {
		OutputStream out= s.getOutputStream();
		out.write("HTTP/1.1 405 Method Not Allowed\r\n\r\n".getBytes());
		out.close();
	}
	
	// requested file not found
	private void send_not_found() throws IOException {
		OutputStream out= s.getOutputStream();
		out.write("HTTP/1.1 404 Resource Not Found\r\n\r\n".getBytes());
		// can also send something!
		
		FileInputStream in= new FileInputStream(new File(FILES_PATH + "404reply.jpeg"));
		int b;
		while((b = in.read()) != -1) out.write(b);
		in.close();
		
		out.close();
	}
	
}
