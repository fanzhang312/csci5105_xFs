import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server side socket program. Perform two kinds of incoming requests.
 * Update client information: Client information are stored in a ClientModel class which could be serialize.
 * Perform find() operation on server side.
 * 
 * @author Fan Zhang, Zhiqi Chen
 *
 */
public class RequestHandler extends Thread{
	public Socket socket;
	public Server server;
	public RequestHandler(Socket s, Server ser){
		socket = s;
		server = ser;
		start();
	}
	
	public void run(){
		receiveMsg();
	}
	
	/**
	 *  Two types of incoming message:
	 *  Update client information: ClientModel
	 *  Perform find() operation: find(filename)
	 */
	public void receiveMsg(){
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Object newObj = ois.readObject();
			if (newObj instanceof ClientModel) {
				// Always perform add when ClientModel receive to update client information
				ClientModel client = (ClientModel) newObj;
				// remove old object
				if(!server.clientList.isEmpty()){
					ArrayList<ClientModel> deleteList = new ArrayList<ClientModel>();
					for(ClientModel cm : server.clientList){
						// distinguish clients by port number
						if(cm.port == client.port){
//							server.clientList.remove(cm);
							deleteList.add(cm);
						}
					}
					server.clientList.removeAll(deleteList);
				}
	
			    server.clientList.add(client);
			    System.out.println("Client information is stored in server");
			    System.out.println(client.toString());
			} else if (newObj instanceof String) {
				String filename = (String) newObj;
				System.out.println("Start to find file: "+filename);
				ArrayList<ClientModel> targets = server.find(filename);
				if(targets.isEmpty()||targets==null){
					System.out.println("No result found: "+filename);
				}
				// Send the result back
				OutputStream os = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os); 
				oos.writeObject(targets);  
				oos.close();  
				os.close();  
			} 
			ois.close();
			is.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	public void receive() {
//		byte[] buf = new byte[100];
//		try {
//			// s.connect(new InetSocketAddress(serverip, sport), cport);
//			InputStream is = socket.getInputStream();
//			// Receive the file name
//			int len = is.read(buf);
//			String filename = new String(buf, 0, len);
//			System.out.println(filename);
//
//			// Write out the received file
//			FileOutputStream fos = new FileOutputStream(filepath + filename);
//			int data;
//			while ((data = is.read()) != -1) {
//				fos.write(data);
//			}
//			is.close();
//			socket.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
}
