import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class NetClient {
	private static int UDP_PORT_START = 2223;
	private int udpPort;

	private TankClient tc;
	
	public NetClient(TankClient tc) {
		udpPort = UDP_PORT_START++;
		this.tc = tc;
	}
	
	public void connect(String IP, int port) {
		Socket socket = null;
		try {
			socket = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			int id = dataInputStream.readInt();
			tc.myTank.id = id;
			System.out.println("Connect server");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				try {
					socket.close();
					socket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}