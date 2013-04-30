import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class CommandServer extends Thread {
	public Socket clientSocket;
	public Client client;
	
	public CommandServer(Socket clientSocket, Client c){
		this.clientSocket = clientSocket;
		client = c;
		start();
	}
	
	public void run(){
		if(clientSocket.getPort()!= Config.serverPort){
			System.out.println("Ready for command");
			
			try {
				InputStream is = clientSocket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				OutputStream os = clientSocket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				String command = (String) ois.readObject();
				if(command.equals("download")){
					System.out.println(clientSocket.getInetAddress().toString());
					client.sendFile(clientSocket.getInetAddress().toString().substring(1), Config.clientPort);
				}else{
					// Send current client load back
					int load = client.load();
					oos.writeObject(Integer.valueOf(load));  
				}
				ois.close();
				is.close();
				oos.close();  
				os.close();  
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
