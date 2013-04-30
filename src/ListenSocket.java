import java.io.IOException;
import java.net.ServerSocket;

/**
 * ListenSocket class create a infinite loop to listen incoming sockets
 * Once receive an incoming socket, start a new thread(TCPServer or CommandServer) to handle it.
 * 
 * TCPServer in charges of downloading files
 * CommandServer in charges of receiving download or get load requests
 * 
 * @author Fan Zhang, Zhiqi Chen
 *
 */
public class ListenSocket extends Thread {
	public ServerSocket serverSocket;
	public Client client;
	public int port;
	
	public ListenSocket(int port, Client c){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.port = port;
		client = c;
		start();
	}
	
	public void run(){
		while(true){
			System.out.println("waiting incoming socket on port: "+ serverSocket.getLocalPort());
        	try {
        		if(client.port == port)
        			new TCPServer(serverSocket.accept());
        		else
        			new CommandServer(serverSocket.accept(), client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
