import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client side program response for send file to requested client
 * 
 * @author Fan Zhang, Zhiqi Chen
 *
 */
public class FileSender {
	// TCP socket
	Socket socket;
	// filepath should be a full path to a file
	String filepath;
	Client client;

	public FileSender(Socket socket, String filepath, Client c) {
		this.socket = socket;
		this.filepath = filepath;
		client = c;
	}

	public void send() {
		// byte[] buf = new byte[100];
		OutputStream os = null;
		FileInputStream fins = null;
		try {
			client.load++;
			os = socket.getOutputStream();
			// First send out file name
			File f = new File(filepath);
			String filename = f.getName();
			String checksum = (String) client.checksums.get(filename);
			os.write(checksum.getBytes());
			os.write(filename.getBytes());
			try {
				// force to sleep 500ms to make sure the file name send out
				// without content since multi-threads are used
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Start file transfer: " + filename);
			System.out.println("Please wait for the success message");
			// Start sending the file
			fins = new FileInputStream(filepath);
			int data;
			while ((data = fins.read()) != -1) {
				os.write(data);
			}
			System.out.println("Success: send out " + filename);
			client.load--;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fins != null)
					fins.close();
				if (os != null)
					os.close();
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
