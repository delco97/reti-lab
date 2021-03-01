package L09_UDP_Sockets.PreAssignment_PinPong;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class Server {
	
	static int BUF_SZ = 1024;
	static int port = 8340;
	
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
								read(key);
								key.interestOps(SelectionKey.OP_WRITE);
							} else if (key.isWritable()) {
								write(key);
								key.interestOps(SelectionKey.OP_READ);
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
	
	static public void main(String[] args) {
		Server svr = new Server();
		svr.process();
	}
	
}