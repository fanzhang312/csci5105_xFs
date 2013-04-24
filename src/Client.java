import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public String ip;
	public int port;
	public FileList list;
	public FileSender sender = null;
	public Socket clientSocket;

	public Client(int port, String filepath) {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			this.port = port;			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		list = new FileList(filepath);

		// Start listening incoming socket on given port
		new ListenSocket(port);
		// Start listening user input command
		// new ListenCommand();
	}

	public void sendFile(){
		try {
			// Create a connected socket to specified IP and port
			// Hard code it right now
			clientSocket = new Socket(ip, 5000);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(list.getFile(7));
		sender = new FileSender(clientSocket, list.getFile(7));
		sender.send();
	}
	
	public static void main(String[] args) {
		Client c1 = new Client(5000, Config.DIRECTORY);
		Client c2 = new Client(5001, Config.DIRECTORY);
		c1.list.getList();
		c2.sendFile();
	}
}
