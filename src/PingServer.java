/**
 * In case server may crashes, once the server is up, client could report its
 * information to server.
 * 
 * @author Fan Zhang, Zhiqi Chen
 * 
 */
public class PingServer extends Thread {
	Client client;

	public PingServer(Client c) {
		client = c;
		start();
	}

	public void run() {
		while (true) {
			client.reportToServer();

			// Set a frequency for update information to server.
			// If server backup after a crash, user need to wait 60 seconds in
			// order all the clients information report to server
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
