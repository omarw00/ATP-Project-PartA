package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPool; // Thread pool

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        Configurations.start();
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.threadPool.setCorePoolSize(Configurations.threadPoolSize());
    }
// for the starting of the server
    public void start(){
        new Thread(() -> {
            serverRun();
        }).start();
    }
    // running the server and wait for the clients
    public void serverRun(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> {
                        handleClient(clientSocket);
                    });


                } catch (SocketTimeoutException e){

                }
            }
            serverSocket.close();
            threadPool.shutdownNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// connecting the client sockets to the input and output streams
    private void handleClient(Socket clientSocket){
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

// stop the server
    public void stop(){
        stop = true;
    }
}
