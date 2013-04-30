import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Server extends Thread{
	public String ip;
	public int port;
	public ServerSocket serverSocket;
	public ArrayList<ClientModel> clientList = new ArrayList<ClientModel>();
	
	public Server(int port){
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			this.port = port;	
			Config.serverIP = ip;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		start();
	}
	
	public void run(){
		while(true){
			System.out.println("\nServer is waiting incoming socket on port: "+ serverSocket.getLocalPort());
        	try {
        		 new RequestHandler(serverSocket.accept(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<ClientModel> find(String filename){
		ArrayList<ClientModel> targets = new ArrayList<ClientModel>();
		if(clientList.isEmpty())
			return null;
		for(ClientModel cm : clientList){
			if(cm.fileList.contains(filename)){
				targets.add(cm);
				System.out.println("Find the given file on client with port: "+ cm.port);
			}
		}
		if(targets.isEmpty()){
			System.out.println("Can't find the given file: "+filename);
		}
		return targets;
	}
	public static void main(String[] args){
		Config.setLatency();
		Config.printLatency();
		new Server(Config.serverPort);
		
	}
}
