import java.net.Socket;


public class TCPServer extends Thread {
	public Socket clientSocket;
	
	public TCPServer(Socket clientSocket){
		this.clientSocket = clientSocket;
		start();
	}
	
	public void run(){
		if(clientSocket.getPort()!= Config.serverPort){
			System.out.println("Start download file");
			FileReceiver receiver = new FileReceiver(clientSocket, Config.SAVEPATH);
			receiver.receive();
		}
	}
}
