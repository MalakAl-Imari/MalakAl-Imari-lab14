import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private List<LocalDateTime> connectionTimes;

    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    public void serve(int numClients) {
        int clientsServed = 0;
        while (clientsServed < numClients) {
            try {
                Socket clientSock = serverSocket.accept();
                connectionTimes.add(LocalDateTime.now());
                System.out.println("New connection: " + clientSock.getRemoteSocketAddress());
                new ClientHandler(clientSock).start();

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
            // ignore
        }
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
    }
}