import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class FileReceiver {
	// TCP socket
	Socket socket;
	// filepath should be a full path to a file. Do not include file name here.
	String filepath;

	public FileReceiver(Socket socket, String filepath) {
		this.socket = socket;
		this.filepath = filepath;
	}

	public void receive() {
		byte[] buf = new byte[100];
		try {
			// s.connect(new InetSocketAddress(serverip, sport), cport);
			InputStream is = socket.getInputStream();
			// Receive the file name
			int len = is.read(buf);
			String filename = new String(buf, 0, len);
			System.out.println(filename);

			// Write out the received file
			FileOutputStream fos = new FileOutputStream(filepath + filename);
			int data;
			while ((data = is.read()) != -1) {
				fos.write(data);
			}
			is.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
