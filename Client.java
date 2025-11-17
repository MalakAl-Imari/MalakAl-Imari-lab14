import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

private final Socket socket; //private because each client has one socket
private PrintWriter out;
private BufferedReader in;

public Client(String host, int port) throws Exception{
     try {
            socket = new Socket(host, port); //set up socket
            out = new PrintWriter(socket.getOutputStream()); //output
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //input
        } catch (Exception e) {
            throw e; //if something doesnt work 
        }
    }

    //proving client valid
    public void handshake() {
        try {
            out.println("12345"); //send passcode
            out.flush(); //get it out immedietly          
        } catch (Exception e) {

        }
    }
    

       public Socket getSocket() { //getter
        return socket;
    }

    
    public String request(String msg) throws Exception {
        try {
            out.println(msg); //send line of text to server
            out.flush();
            return in.readLine();  //wait for one line back 
        } catch (Exception e) {
            return null; //if something goes wrong 
        }
    }


    public void disconnect() {
         try { 
            in.close(); //reader close
        }  catch (Exception e) {

        }
        try { 
            out.close(); //printer close
        } catch (Exception e) {

      }
        try { 
            socket.close(); //socket close
        } catch (Exception e) {

        }
    }

    

}

