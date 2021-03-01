package L09_UDP_Sockets.Assignment_UDP_Ping;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Random;

public class PingServer {
	
	static int BUF_SZ = 1024;
	static int port;
	static Random rand;
	
	public PingServer(int port, Random rand) {
		this.port = port;
		this.rand = rand;
	}
	
	class Con {
		
		ByteBuffer req;
		ByteBuffer resp;
		SocketAddress sa;
		
		public Con() {
			req = ByteBuffer.allocate(BUF_SZ);
		}
		
	}
	
	
	private void read(SelectionKey key) throws IOException {
		DatagramChannel chan = (DatagramChannel) key.channel();
		Con con = (Con) key.attachment();
		con.sa = chan.receive(con.req);
		con.req.flip();
		System.out.println(new String(con.req.array(), "UTF-8"));
		con.resp = con.req;
	}
	
	private void write(SelectionKey key) throws IOException {
		DatagramChannel chan = (DatagramChannel) key.channel();
		Con con = (Con) key.attachment();
		chan.send(ByteBuffer.wrap("PONG".getBytes()), con.sa);
		con.req.clear();
	}
	
	private void process() {
		try {
			Selector selector = Selector.open();
			DatagramChannel channel = DatagramChannel.open();
			InetSocketAddress isa = new InetSocketAddress(port);
			channel.socket().bind(isa);
			channel.configureBlocking(false);
			SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
			clientKey.attach(new Con());
			System.out.println("PingServer Pronto");
			while (true) {
				try {
					selector.select();
					Iterator selectedKeys = selector.selectedKeys().iterator();
					while (selectedKeys.hasNext()) {
						try {
							SelectionKey key = (SelectionKey) selectedKeys.next();
							selectedKeys.remove();
							if (!key.isValid()) {
								continue;
							}
							if (key.isReadable()) {
								simualteDelay();
								read(key);
								key.interestOps(SelectionKey.OP_WRITE);
							} else if (key.isWritable()) {
								if (ignore()) System.out.println("Packet loss, ooooops!");
								else {
									write(key);
									key.interestOps(SelectionKey.OP_READ);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	private void simualteDelay() {
		try {
			Thread.sleep(getRandomNumberInRange(100, 2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean ignore() {
		return rand.nextInt(25) == 0;
	}
	
	static public void main(String[] args) {
		int port = -1;
		Random rand = null;
		//Check arguments
		if (args.length != 1 && args.length != 2) {
			showUsage("Wrong arguments number");
			return;
		}
		//Arguments number is correct
		try {
			port = Integer.parseInt(args[0]);
			rand = (args.length != 2) ? new Random() : new Random(Integer.parseInt(args[1]));
		} catch (NumberFormatException e) {
			showUsage("First argument must be a valid port and the second must be a valid seed for rand.");
			return;
		}
		
		PingServer svr = new PingServer(port, rand);
		svr.process();
	}
	
	private static void showUsage(String s) {
		System.err.println(s);
		System.out.println("command syntax: ping_srv <port> [seed]");
	}
	
}
