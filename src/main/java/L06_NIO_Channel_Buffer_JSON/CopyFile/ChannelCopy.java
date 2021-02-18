package L06_NIO_Channel_Buffer_JSON.CopyFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {
	
	public static void main(String[] argv) throws IOException {
		ReadableByteChannel source = Channels.newChannel(new FileInputStream("in.txt"));
		WritableByteChannel dest = Channels.newChannel(new FileOutputStream("out.txt"));
		channelCopy1(source, dest);
		source.close();
		dest.close();
		
	}
	
	private static void channelCopy1(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while (src.read(buffer) != -1) {
			
			// prepararsi a leggere i byte che sono stati inseriti nel buffer
			buffer.flip();
			
			// scittura nel file destinazione; può essere bloccante
			dest.write (buffer);
			
			// non è detto che tutti i byte siano trasferiti, dipende da quanti // bytes la write ha scaricato sul file di output
			// compatta i bytes rimanenti all’inizio del buffer // se il buffer stato completamente scaricato, si comporta come clear(
			buffer.compact();
		}
		// quando si raggiunge l’EOF, possibile che alcuni byte debbano essere ancor // scritti nel file di output
		buffer.flip();
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
	
}