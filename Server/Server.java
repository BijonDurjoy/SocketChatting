package multiple_client_group_chat.Server;

import multiple_client_group_chat.Clients.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //Server socket that accepts client and handlers data flow
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public Server(ServerSocket serverSocket, int numberOfParticipants){
        this.serverSocket = serverSocket;
        executor = Executors.newFixedThreadPool(numberOfParticipants);
    }
    public void starServer(){
        try{
            while (!serverSocket.isClosed()){
                //new connected client socket
                Socket socket= serverSocket.accept();
                System.out.println("A new client has been connected!");
                executor.execute(new ClientHandler(socket));
            }
        }catch (IOException e){
            e.printStackTrace();
            closeServerSocket();
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket != null) {
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4443);
        System.out.println("Server Started listening on port: 4443");
        Scanner input = new Scanner(System.in);

        System.out.println("Set numbers of percipients: ");
        int numberOfParticipants = input.nextInt();

        Server server = new Server(serverSocket, numberOfParticipants);
        server.starServer();
    }
}
