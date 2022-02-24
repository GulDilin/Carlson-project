package server;

import CarlsonProject.WindowsArrayList;
import CarlsonProject.commands.Command;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TCPResponseThread extends Thread {

    private final WindowsArrayList windowsArrayList;
    private ByteBuffer buffer;
    private DatagramChannel channel;
    private final Socket tcpSock;
    private Command command;
    private final DataBaseManager dataBaseManager;

    public TCPResponseThread(Socket tcpSock, WindowsArrayList windowsArrayList, DataBaseManager dataBaseManager ){
        super();
        this.windowsArrayList = windowsArrayList;
        this.tcpSock = tcpSock;
        this.dataBaseManager = dataBaseManager;
    }

    public void run() {
        try (InputStream is = tcpSock.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(is);

             OutputStream os = tcpSock.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os);

             ByteArrayOutputStream bao = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(bao)) {

            boolean isWorking = true;
            while (isWorking) {
                command = (Command) ois.readObject();
                String response = "";

                System.out.println("Client command: " + command.toString());
                printStream.println();
                command.setOut(printStream);
                command.setDataBaseManager(dataBaseManager);
//                System.out.println("User hash: " + command.getUserHash());
//                System.out.println("User ID: " + command.getUserId());
//                System.out.println("databaseManager: " + dataBaseManager.toString());
//                System.out.println(dataBaseManager.checkUser(command.getUserHash(), command.getUserId()));
                printUserStatus();

                try {
                    if (dataBaseManager.checkUser(command.getUserHash(), command.getUserId()))
                        command.execute(windowsArrayList);
                } catch (NullPointerException ex){
                    ex.printStackTrace();
                }
                printStream.flush();
                printStream.println();
                response = bao.toString().trim();
                bao.reset();
                System.out.println(response);
                if(response.contains("Exit")){
                    isWorking = false;
                }

                oos.writeObject(response);
                oos.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printUserStatus(){
        System.out.println("User hash: " + command.getUserHash());
        System.out.println("User ID: " + command.getUserId());
        System.out.println("databaseManager: " + dataBaseManager.toString());
        System.out.println(dataBaseManager.checkUser(command.getUserHash(), command.getUserId()));
    }
}