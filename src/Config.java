/**
 * The system defaults. YOU ARE RESPONSE FOR LETTING EVERY CLIENT KNOWS THE
 * SETTINGS
 * 
 * Please notice that if running on same machine, DIRECTORY and SAVEPATH should
 * be two distinct paths. If running on different machines, DIRECTORY and
 * SAVEPATH should be the same. Also, you need to pre-define the value of
 * serverIP and serverPort, and the clientPort(the client you are interacting
 * with)
 * 
 * @author Fan Zhang, Zhiqi Chen
 * 
 */

public class Config {
	// Define the local storage directory
	public static final String DIRECTORY = "/Users/fan/Documents/";

	/*
	 * Define the directory you want to save the download file (If you are
	 * running code in the same machine, savepath should be differ with
	 * directory)
	 */
	public static final String SAVEPATH = "/Users/fan/Documents/receive/";
	/*
	 * If you run the code in different machines, you need to use the below line
	 * of SAVEPATH
	 */
	// public static final String SAVEPATH = DIRECTORY;
	
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

	public static void setLatency() {
		for (int i = 0; i < NUMBER; i++) {
			for (int j = i; j < NUMBER; j++) {
				if (i == j) {
					latency[i][j] = 0;
				} else if (i < j) {
					latency[i][j] = MIN_LATENCY
							+ (int) (Math.random()
									* (MAX_LATENCY - MIN_LATENCY) + 1);
					latency[j][i] = latency[i][j];
				} else {
					continue;
				}
			}
		}
	}

	public static void printLatency() {
		System.out.println("----------Latency Table----------");
		for (int i = 0; i < NUMBER; i++) {
			for (int j = 0; j < NUMBER; j++) {
				System.out.format("%6d", latency[i][j]);
			}
			System.out.println();
		}
	}
}
