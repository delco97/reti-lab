import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Server implements Runnable {

	private Socket s; 
	public Server(Socket s) {this.s= s;}
	
	// nothing special, really...
	public static void main(String[] args) throws IOException { 
		ServerSocket ss= new ServerSocket(6780);
		ThreadPoolExecutor ex= (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
		while(true){
			Socket s= ss.accept();
			System.out.println("nuovaconnessione");
			ex.execute(new Server(s));
		}
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
		File f= new File(file);
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
	private void send_file(File f) throws IOException { 
		FileInputStream in= new FileInputStream(f);
		OutputStream out= s.getOutputStream();
		int b;
		while((b = in.read()) != -1) out.write(b);
		in.close();
		out.close();
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
		
		FileInputStream in= new FileInputStream(new File("404reply.jpeg"));
		int b;
		while((b = in.read()) != -1) out.write(b);
		in.close();
		
		out.close();
	}
	
}
