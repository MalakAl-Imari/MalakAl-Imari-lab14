import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

private final Socket socket;
private PrintWriter out;
private BufferedReader in;

public Client(String host, int port) throws Exception{
     try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream()); 
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw e;
        }
    }


    public void handshake() {
        try {
            out.println("12345"); 
            out.flush();          
        } catch (Exception e) {

        }
    }
    

       public Socket getSocket() {
        return socket;
    }

    
    public String request(String msg) throws Exception {
        try {
            out.println(msg);
            out.flush();
            return in.readLine();  
        } catch (Exception e) {
            return null;
        }
    }


    public void disconnect() {
         try { 
            in.close(); 
        }  catch (Exception e) {

        }
        try { 
            out.close(); 
        } catch (Exception e) {

      }
        try { 
            socket.close(); 
        } catch (Exception e) {

        }
    }

    

}

