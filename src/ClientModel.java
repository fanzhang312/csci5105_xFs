/**
 * ClientModel is a class used for store client information and 
 * can be send to server and stored in server side
 * 
 * @author Fan Zhang, Zhiqi Chen
 */

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;


public class ClientModel implements Serializable{

	private static final long serialVersionUID = 107535800548705100L;
	String ip;
	int port;
	String filePath;
	String fileList;
	int load;
	Dictionary checksums = new Hashtable();
	
	public ClientModel(String ip, int port, String filePath, String fileList){
		this.ip = ip;
		this.port = port;
		this.filePath = filePath;
		this.fileList = fileList;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileList() {
		return fileList;
	}
	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
	public int getLoad(){
		return load;
	}
	public void setLoad(int load){
		this.load = load;
	}
	public Dictionary getChecksums() {
		return checksums;
	}
	public void setChecksums(Dictionary checksums) {
		this.checksums = checksums;
	}
	
	public String toString(){
		return this.ip+"\n"+this.port+"\n"+this.filePath+"\n"+this.fileList;
	}
}
