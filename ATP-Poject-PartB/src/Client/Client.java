package Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
public class Client {
    private IClientStrategy strategy;
    private InetAddress serverIP;
    private int serverPort;

    public Client(IClientStrategy strategy, InetAddress serverIP, int serverPort) {
        this.strategy = strategy;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public IClientStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IClientStrategy strategy) {
        this.strategy = strategy;
    }

    public InetAddress getServerIP() {
        return serverIP;
    }

    public void setServerIP(InetAddress serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void communicateWithServer(){
        start();
    }

    public void start(){
        try(Socket socket = new Socket(serverIP,serverPort)){
            System.out.println("Client has connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(socket.getInputStream(),socket.getOutputStream());
        }
        catch(IOException IOe){
            IOe.printStackTrace();
        }
    }
}
