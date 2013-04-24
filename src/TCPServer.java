import java.net.Socket;


public class TCPServer extends Thread {
	public Socket clientSocket;
	
	public TCPServer(Socket clientSocket){
		this.clientSocket = clientSocket;
		start();
	}
	
	public void run(){
		System.out.println("New TCP socket thread");
		FileReceiver receiver = new FileReceiver(clientSocket, Config.SAVEPATH);
		receiver.receive();
	}
}
