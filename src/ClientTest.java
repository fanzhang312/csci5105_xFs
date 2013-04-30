//import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Provide a command interface for user to perform functions in client.
 * 
 * @author Fan Zhang, Zhiqi Chen
 */

public class ClientTest {
	public static Client client;

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		if (str.charAt(0) == '-') {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}

	/*
	 * testInput try to decide whether the input is legal or not based on two
	 * input situate: yes or no; 1,2,3,4,5,6...
	 */
	public static boolean testInput(String input, String testCase) {
		if (input == null || input.isEmpty())
			return false;
		String in = input.trim().toLowerCase();
		if (testCase.equals("yesno")) {
			if (in.equals("yes") || in.equals("no"))
				return true;
			return false;
		}
		if (testCase.equals("number")) {
			if (isInteger(in)) {
				return true;
			}
			return false;
		}
		return false;

	}

	public static String getInput() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter file name: ");
		String result = input.nextLine().trim();
		while (result.isEmpty() || result.equals(null)) {
			System.out
					.println("File name can't be null! Please enter file name: ");
			result = input.nextLine().trim();
		}
		return result;
	}

	/*
	 * createClient() enable user to create client
	 */
	public static void createClient() {
		Scanner input = new Scanner(System.in);

		System.out.print("Please enter client port: ");
		String port = input.nextLine().trim();
		while (!testInput(port, "number")) {
			System.out.print("Be smart, enter a port number: ");
			port = input.nextLine().trim();
		}
		client = new Client(Integer.parseInt(port), Config.DIRECTORY);
		Config.clientPort = Integer.parseInt(port);
		for(int i=1; i<Config.NUMBER; i++){
			new Client(Integer.parseInt(port) + 2*i, Config.DIRECTORY);
		}
		
		try {
			// Sleep for a while in order to makes the output looks more neatly, since each client is a new thread
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Create client success!");

	}

	// Print a menu for user to choose
	public static void clientMenu() {
		System.out.println("\nPlease select following options");
		System.out.println("1. Find");
		System.out.println("2. Download");
		System.out.println("3. UpdateList");
		System.out.println("4. GetLoad");
		System.out.println("5. Exit");
		System.out.print("Command: ");
		Scanner option = new Scanner(System.in);
		String selection = option.nextLine().trim();
		if (!testInput(selection, "number") || Integer.parseInt(selection) > 5
				|| Integer.parseInt(selection) < 1) {
			System.out
					.println("\n Invalid input!Enter a number bewteen 1 to 5");
			return;
		}

		switch (Integer.parseInt(selection)) {
		case 1: {
			String filename = getInput();
			client.find(filename);
		}
			break;
		case 2:{
			System.out.println("Do you want to download the file you just find? Yes/No");
			Scanner input = new Scanner(System.in);
			String in = input.nextLine().trim().toLowerCase();
			if(in.equals("yes"))
				client.download();
			else{
				String filename = getInput();
				client.find(filename);
				client.download();
			}
		}
			break;
		case 3:
			client.reportToServer();
			break;
		case 4:
			client.getLoad();
			break;
		case 5:
			System.exit(0);
			break;
		default:
			break;
		}

	}

	public static void main(String[] args) {
		System.out
				.println("\n-----------Welcome to the Client Console-----------");
		System.out.println("Author: Fan Zhang, Zhiqi Chen\n");
		System.out.println("Do you want to create a client now? Yes/No:");
		Scanner user_input = new Scanner(System.in);
		String input = user_input.nextLine().trim().toLowerCase();
		while (!testInput(input, "yesno")) {
			System.out.println("\nPlease follow instruction: Just type Yes/No");
			System.out.println("Do you want to create a client now? Yes/No:");
			input = user_input.nextLine().trim().toLowerCase();
		}
		if (input.equals("yes")) {
			createClient();
			while (true) {
				clientMenu();
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Thanks for using Client Console!");
			System.exit(0);
		}

	}
}
