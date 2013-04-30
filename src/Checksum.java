import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Generate checksum and provide method to store file name and corresponding checksum value
 * 
 * @author Fan Zhang, Zhiqi Chen
 *
 */
public class Checksum {
	public Client client;
	public MessageDigest md;
	
	public Checksum(){
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	public Checksum(Client c) {
		client = c;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String generateChecksum(String filepath) {
		try {
			FileInputStream fis = new FileInputStream(filepath);

			byte[] dataBytes = new byte[1024];

			int nread = 0;

			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		System.out.println("Checksum(in hex format):: " + sb.toString());

		return sb.toString();
	}
	
	public void setAll(){
		File[] list = client.list.list;
		for (File f : list) {
			if (f.isDirectory()) {
//				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				String checksum = generateChecksum(f.getAbsolutePath());
				client.checksums.put(f.getName(), checksum);
			}
		}
	}
}
