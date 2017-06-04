import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int port = 10000;
		ServerSocket server = new ServerSocket(port);
		System.out.println("대기중...");
		Socket socket = server.accept();
		
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String line = null;
		OutputStreamWriter osw = new OutputStreamWriter(System.out);
		PrintWriter pw = new PrintWriter(osw);
		while((line = br.readLine()) != null) {
			//System.out.print("받음 : ");
			//pw.println(line);
			//pw.flush();
		}
		
		br.close();
		pw.close();
		socket.close();
	}

}
