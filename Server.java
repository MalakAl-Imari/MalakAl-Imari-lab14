import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server {

    private ServerSocket serverSocket;
    private List<LocalDateTime> connectionTimes;

    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        connectionTimes = new ArrayList<>(); 
    }

    public void serve(int numClients) {
        int clientsServed = 0;
        while (clientsServed < numClients) {
            try {
                Socket clientSock = serverSocket.accept();
                connectionTimes.add(LocalDateTime.now()); //add to arraylist
                System.out.println("New connection: " + clientSock.getRemoteSocketAddress());
                new ClientHandler(clientSock).start(); //make thread 

                clientsServed++;
            } catch (Exception e) {
                break;
            }
        }
    }

    public void disconnect() {
        try {
            serverSocket.close();
        } catch (Exception e) {
    
        }
    }


     public ArrayList<LocalDateTime> getConnectedTimes() {
        ArrayList<LocalDateTime> copy = new ArrayList<>(connectionTimes);
        Collections.sort(copy);
        return copy;
    }

    private int integerFactors(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                count++;
            }
        }
        return count;
    }

    private class ClientHandler extends Thread {
        private Socket sock;

        public ClientHandler(Socket sock) {
            this.sock = sock;
        }

        @Override
        public void run() {
            PrintWriter out = null; //Set up I/O
            BufferedReader in = null;

            try {
                out = new PrintWriter(sock.getOutputStream());
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                // checking for passcode otherwise clsose 
                String handshake = in.readLine();
                if (handshake == null || !handshake.equals("12345")) {
                    out.println("couldn't handshake");
                    out.flush();
                    out.close();
                    in.close();
                    sock.close();
                    return;
                }

                // 2) take in lines and give back factors
                String msg;
                while ((msg = in.readLine()) != null) {
                    msg = msg.trim();
                    String response;

                    try {
                        int n = Integer.parseInt(msg); //turn string into int
                        int factors = integerFactors(n); //call helper
                        response = "The number " + n + " has " + factors + " factors";
                    } catch (Exception ex) {
                        response = "There was an exception on the server";
                    }

                    out.println(response);
                    out.flush();
                }

                out.close();
                in.close();
                sock.close();

            } catch (Exception e) {
                
            }

            System.out.println("Connection lost: " + sock.getRemoteSocketAddress());
        }
    }
}