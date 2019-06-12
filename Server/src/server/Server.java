package server;
import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.System.exit;

public class Server {
    private InetAddress         host;
    private Socket              tcpSocket;
    private DataBaseManager     dataBaseManager;
    private ServerSocket        serverSocket;
    private WindowsArrayList windows = new WindowsArrayList(new CopyOnWriteArrayList<Window>());

    private Server(String hostName, int port)throws IOException {
        this.host = InetAddress.getByName(hostName);
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("IP: " + host);
        System.out.println("port: " + port);
        try {
            this.dataBaseManager = new DataBaseManager("studs", 8690, true);
            this.windows = this.dataBaseManager.getWindows();
        } catch (SQLException e){
            System.out.println("Database connection error");
            exit(1);
        }
    }
    private Server(int port) throws IOException, SQLException {
        this("localhost", port);
    }

    private void listen() throws IOException {
        while (true) {
            tcpSocket = serverSocket.accept();

            TCPResponseThread responseThread = new TCPResponseThread(tcpSocket, windows, dataBaseManager);
            responseThread.start();
        }
    }

    public static void main(String[] args) {
        if(args.length>0) {
            try {
                Server server = new Server(Integer.valueOf(args[0]));

                server.listen();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                System.err.println("Can't connect database");
                ex.printStackTrace();
            }
        }else System.out.println("Need to write port");
    }
}
