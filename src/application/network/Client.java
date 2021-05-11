package application.network;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Client extends Thread{

    private ArrayList<Client> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(Socket socket, ArrayList<Client> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
            	System.out.println("client");
                for (Client cl : clients) {
                    cl.writer.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
