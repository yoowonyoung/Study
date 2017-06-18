import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int port = 10000;
		String ip = "127.0.0.1";
		Charset charset = Charset.forName("EUC-KR");
		CharsetEncoder encoder = charset.newEncoder();
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ServerSocket server = ssc.socket();
		server.bind(new InetSocketAddress(ip, port));
		ssc.configureBlocking(false);
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("대기중...");
		int socketOps = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
		ByteBuffer buff = null;
		while (selector.select() > 0) {
			Set keys = selector.selectedKeys();
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				SelectionKey selected = (SelectionKey) iter.next();
				iter.remove();
				SelectableChannel channel = selected.channel();
				if (channel instanceof ServerSocketChannel) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
					SocketChannel socketChannel = serverChannel.accept();
					if (socketChannel == null) {
						System.out.println("## null server socket");
						continue;
					}
					System.out.println("## socket accepted : " + socketChannel);
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, socketOps);
				}
				else {
					SocketChannel socketChannel = (SocketChannel) channel;
					buff = ByteBuffer.allocate(100);
					if (selected.isConnectable()) {
						System.out.println("Client와의 연결");
						if (socketChannel.isConnectionPending()) {
							System.out.println("Client와의 연결 중");
							socketChannel.finishConnect();
						}
					}
					else if (selected.isReadable()) {
						try {
							socketChannel.read(buff);
							if (buff.position() != 0) {
								buff.clear();
								System.out.print("클라이언트로 전달된 내용 : ");
								while (buff.hasRemaining()) {
									System.out.print((char) buff.get());
								}
								buff.clear();
								System.out.println();
								if (selected.isWritable()) {
									System.out.println("서버에서 보냄 ");
								}
							}
						}
						catch (IOException ioe) {
							ioe.printStackTrace();
							socketChannel.finishConnect();
							socketChannel.close();
						}
					}
				}
			}
		}
	}
}
