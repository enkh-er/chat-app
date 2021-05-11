package application.network;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	private static ArrayList<Client> clients = new ArrayList<Client>();

    public static void main(String[] args){
    	Socket socket;
        try {
        	ServerSocket serverSocket = new ServerSocket(2345);
        	System.out.println("Waiting for clients...");
            while(true) {
                socket = serverSocket.accept();
                System.out.println("Connected");
                Client client= new Client(socket, clients);
                clients.add(client);
                client.start();
            }
        }catch(EOFException e) {
        	e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
