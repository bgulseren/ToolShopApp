package serverControl;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;


/**
 * Implementation of the Server Controller
 * @version 1.0
 * @since October 19th, 2020
 */


public class ServerController {

	private ExecutorService pool;
	
	private ServerSocket serverSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;

	private ModelController model;

	public ServerController() {
		try {
			serverSocket = new ServerSocket(9898);
			
			pool = Executors.newFixedThreadPool(10);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    

	public void runServer () throws ClassNotFoundException, IOException {
		
		while (true) {

			Socket cliSocket = new Socket();
				
			try {
				cliSocket = serverSocket.accept();
				System.out.println("Server: incoming client connection...");
			} catch (IOException e) {
				System.out.println("Server: socket failed");
			}
			
			if (cliSocket.isConnected()) {
				socketIn = new ObjectInputStream(cliSocket.getInputStream());
				socketOut = new ObjectOutputStream (cliSocket.getOutputStream());
				model = new ModelController(); //todo: maybe construct new each time client connects with UPDATE
				model.setSrvControl(this);
				//model.setClientSocket(cliSocket);
				model.setSocketIn(socketIn);
				model.setSocketOut(socketOut);
				System.out.println("Server: a new client is connected");
			} else {
				cliSocket.close(); //todo: not sure about this
				System.out.println("Server: a client socket is closed");
				break;
			}
			
			pool.execute(model);
		}
			
		pool.shutdown();
		
		System.out.println("Server: Server closed");
	}
	
	/**
	 * Main method, which instantiates the server
	 * 
	 * @param args system arguments
	 * @throws IOException uses standard input to read lines from the CLI, which can throw an exception.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		ServerController myServer = new ServerController ();
		System.out.println("Server: Ready, awaiting client connections...");
		
		
		myServer.runServer();
	}
	
}
