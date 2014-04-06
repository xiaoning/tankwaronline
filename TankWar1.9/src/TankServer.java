import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TankServer {
	private static int ID = 100;
	public static final int TCP_PORT = 8888;
	List<Client> clients = new ArrayList<Client>();
	
	public void start() {
		ServerSocket ssocket = null;
		try {
			ssocket = new ServerSocket(TCP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			Socket socket = null;
			try {
				socket = ssocket.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String ip = socket.getInetAddress().getHostAddress();
				int udpPort = dis.readInt();
				Client c = new Client(ip, udpPort);
				clients.add(c);
				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				dataOutputStream.writeInt(ID++);
				socket.close();
				System.out.println("A client connet! Address is " + socket.getInetAddress() + ": " + socket.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TankServer().start();
	}
	
	private class Client {
		String ip;
		int udpPort;
		
		public Client(String ip, int udpPort) {
			this.ip = ip;
			this.udpPort = udpPort;
		}
	}
}
