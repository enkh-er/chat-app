package application.network;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * server vvsgeh class
 * @author enkherdene
 *
 */
public class Server {
	/**Servert holbogdoh hvselt tawisan clients*/
	private static ArrayList<Client> clients = new ArrayList<Client>();

    public static void main(String[] args){
    	Socket socket;
        try {
        	//2345 port der server socket vvsgene
        	ServerSocket serverSocket = new ServerSocket(2345);
        	System.out.println("Waiting for clients...");
        	//holbogdohiig hvssen socket-ig tasraltgvi sonsono
            while(true) {
                socket = serverSocket.accept();
                System.out.println("Connected");
                //Client angiin objectiig vvsgene
                Client client= new Client(socket, clients);
                clients.add(client);
                //shineet vvssen thread-g ajilluulna
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
