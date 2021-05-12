package application.network;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
/**
 * Multithreading ashiglan hereglegchidtei haritsana
 *
 */
public class Client extends Thread{
   //server-t holbogdson hereglegchid
    private ArrayList<Client> clients;
    //user-tai haritsahad ashigladag svljeenii socket
    private Socket socket;
    //Svljeegeer ireh zurwas unshih object
    private BufferedReader reader;
    //Svljeegeer ilgeeh zurwasiig damjuulah object
    private PrintWriter writer;

    /**
     * Constructor method
     * @param socket =  user-iin socket
     * @param clients = server-t holbogdson hereglegchid
     */
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

    /**
     * User-ees irsen zurwasiig sonsoj, Bvh holbogdson hereglegchid zurwasiig damjuulna
     */
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
        	clients.remove(this);
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
