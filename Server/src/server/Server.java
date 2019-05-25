package server;
import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private DatagramChannel channel;
    private InetAddress host;
    private DataBaseManager dataBaseManager;
    private WindowsArrayList windows = new WindowsArrayList(new CopyOnWriteArrayList<Window>());

    public Server(int port) throws IOException, SQLException {
        this.host = InetAddress.getLocalHost();
        this.channel = DatagramChannel.open().bind(new InetSocketAddress(host, port));
        System.out.println("Server started");
        System.out.println("IP: " + host);
        System.out.println("port: " + port);
        //this.windows.importFromFile("default.json");
        dataBaseManager = new DataBaseManager();
        windows = dataBaseManager.getWindows();
    }

    private void listen() throws IOException {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            buffer.clear();
            InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

            ResponseThread responseThread = new ResponseThread(channel, clientAddress, buffer, windows);
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
                System.out.println("Can't connect database");
                ex.printStackTrace();
            }
        }else System.out.println("Need to write port");
    }
}
