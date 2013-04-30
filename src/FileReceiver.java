import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Receive file and save as the original file name. After download the file,
 * check it's checksum with the source file's checksum value and print the
 * result to let user know whether the file has been corrupted
 * 
 * @author Fan Zhang, Zhiqi Chen
 * 
 */
public class FileReceiver {
	// TCP socket
	Socket socket;
	// filepath should be a full path to a file. Do not include file name here.
	String filepath;
	// Compute the checksum after download
	Checksum cs;
	String checksum;

	public FileReceiver(Socket socket, String filepath) {
		this.socket = socket;
		this.filepath = filepath;
		cs = new Checksum();
	}

	public void receive() {
		byte[] buf = new byte[100];
		byte[] buff = new byte[40];
		try {
			// s.connect(new InetSocketAddress(serverip, sport), cport);
			InputStream is = socket.getInputStream();
			int len = is.read(buff);
			String originalsum = new String(buff, 0, len);
			System.out.println("source file checksum value: " + originalsum);
			// Receive the file name
			len = is.read(buf);
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
			// Compute the checksum after download
			checksum = cs.generateChecksum(filepath + filename);
			if (!originalsum.equals(checksum)) {
				System.out.println("File has been corrupted!");
			} else {
				System.out
						.println("Checksum value is the same compare with the source file");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
