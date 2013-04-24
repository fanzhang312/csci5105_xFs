import java.io.IOException;
import java.net.ServerSocket;

/**
 * ListenSocket class create a infinite loop to listen incoming sockets
 * Once receive an incoming socket, start a new thread(TCPServer) to handle it.
 * 
 * @author Fan Zhang, Zhiqi Chen
 *
 */
public class ListenSocket extends Thread {
	public ServerSocket serverSocket;
	
	public ListenSocket(int port){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	
	public void run(){
		while(true){
			System.out.println("waiting incoming socket on port: "+ serverSocket.getLocalPort());
        	try {
				new TCPServer(serverSocket.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
