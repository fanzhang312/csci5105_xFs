
/**
 * The system default
 * 
 * @author Fan Zhang, Zhiqi Chen
 *
 */

public class Config {
	// Define the local storage directory
	public static final String DIRECTORY = "/Users/fan/Documents/";
	// Define the directory you want to save the download file
	public static final String SAVEPATH = "/Users/fan/Documents/receive/";
	// Specify number of client
	public static final int NUMBER = 5;
	
	// Server's IP and Port
	public static String serverIP;
	public static int serverPort = 5150;
	// Current client interact with user
	public static int clientPort;
	
	// Latency table
	public static int[][] latency = new int[NUMBER][NUMBER];
	public static final int MIN_LATENCY = 100;
	public static final int MAX_LATENCY = 5000;
	public static void setLatency(){
		for(int i=0; i<NUMBER; i++){
			for(int j=i; j<NUMBER; j++){
				if(i==j){
					latency[i][j] = 0;
				}else if(i<j){
					latency[i][j] = MIN_LATENCY + (int) (Math.random()*(MAX_LATENCY-MIN_LATENCY)+1);
					latency[j][i] = latency[i][j];
				}else{
					continue;
				}
			}
		}
	}
	public static void printLatency(){
		System.out.println("----------Latency Table----------");
		for(int i=0; i<NUMBER; i++){
			for(int j=0; j<NUMBER; j++){
				System.out.format("%6d",latency[i][j]);
			}
			System.out.println();
		}
	}
}
