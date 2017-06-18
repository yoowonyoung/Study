import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Main {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		String ip = "127.0.0.1";
		int port = 10000;
		Socket socket;
		
		try {
			socket = new Socket();
			socket.setSoTimeout(200000);//소켓 연결 후 인풋스트림에서 읽을때의 타임아웃 설정 => receive timeout?, 보통 리시브는 2초?
			socket.connect(new InetSocketAddress(ip, port),100000);// 소켓을 연결할때 타임아웃 설정 => connection timeout?, 보통 커넥션은 1초 설정
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw);
			
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			
			System.out.print("입력 : ");
			String line = null;
			
			while((line = br.readLine()) != null) {
				if(line.equals("Q")) {
					break;
				}
				System.out.print("입력 : ");
				pw.print(line);
				pw.flush();
			}
			
			br.close();
			pw.close();
			socket.close();
		} catch (SocketTimeoutException e) {
			System.out.println("Time out");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
