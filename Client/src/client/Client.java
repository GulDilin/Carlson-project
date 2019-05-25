package client;

import java.io.*;
import java.net.DatagramPacket;

import CarlsonProject.UserHandler;
import CarlsonProject.commands.Command;
import CarlsonProject.commands.ConnectCommand;
import server.DataBaseManager;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.exit;

public class Client {
    private DatagramSocket udpSocket;
    private InetAddress serverAdress;
    private int port;
    private String defaultFilename;
    private boolean isWorking;
    private HashSet<String> user_logins = new HashSet<>();
    private DataBaseManager dataBaseManager;
    private Tunnel tunnel;

    public Client(String serverAdress, int port) throws IOException, SQLException {
        isWorking = true;
//        dataBaseManager = new DataBaseManager();
        this.serverAdress = InetAddress.getByName(serverAdress);
        this.port = port;
        tunnel = new Tunnel("helios.se.ifmo.ru",
                "s264449",
                "cfv571",
                2222,
                "localhost",
                port,
                port);
//        tunnel.connect();
        udpSocket = new DatagramSocket();

    }

    final String HELP = "           Команды : \n" +
            "\tadd {element}:          |добавить новый элемент в коллекцию.\n" +
            "\tinsert index {element}: |добавить новый элемент в коллекцию по индексу.\n" +
            "\tshow:                   |вывести в стандартный поток вывода все элементы коллекции в строковом представлении.\n" +
            "\timport {path}:          |добавить в коллекцию все данные из файла.\n" +
            "\tsave:                   |сохранить коллекцию в файл.\n" +
            "\tsort:                   |вывод итсортированного список элементов коллекции.\n" +
            "\tinfo:                   |вывести информацию о коллекции.\n" +
            "\tremove {element}:       |удалить элемент из коллекции по его значению.\n" +
            "\tremove_last:            |удалить последний элемент.\n" +
            "\tstart:                  |запустить основную программу для настроенной коллекции.\n" +
            "\texit:                   |выход из программы.\n" +
            "\thelp:                   |вывод списка доступных команд.\n" +
            "\tcheck:                  |проверка подключения\n";


    public void testServerConnection() throws IOException {
        System.out.print("Try to connect server ");
        DatagramPacket check = createDPacket(new ConnectCommand());

        byte[] buf = new byte[1024];
        DatagramPacket testResponse = new DatagramPacket(buf, buf.length);

        boolean connected = false;
        udpSocket.setSoTimeout(1428);
        String connectString = "";
        for (int i = 0; i < 7; i++) {
            udpSocket.send(check);
            try {
                udpSocket.receive(testResponse);
            } catch (SocketTimeoutException e) {
                System.out.print('.');
                continue;
            }
            connectString = getServerOutput(check);

            if (connectString.equals("Connected")) {
                connected = true;
                break;
            }
        }

        if (connected) {
            System.out.println("Conection complete");
        } else {
            System.err.println("Can't connect server");
            exit(1);
        }
    }


    private String getServerOutput(DatagramPacket senderDPacket) throws IOException {
        String response = "";
        if (senderDPacket != null) {
            udpSocket.send(senderDPacket);

            byte[] respBuf = new byte[4096];
            DatagramPacket responsePacket = new DatagramPacket(respBuf, respBuf.length);
            try {
                this.udpSocket.receive(responsePacket);

                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(respBuf))) {
                    response = (String) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (SocketTimeoutException e) {
                System.err.println("Timeout end");
                System.err.println("Lost connection");
                testServerConnection();
            }
        }
        return response;
    }

    private DatagramPacket createDPacket(Command command) throws IOException {
        byte[] sending;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

            oos.writeObject(command);
            oos.flush();
            sending = outputStream.toByteArray();

        } catch (NotSerializableException e) {
            throw new NotSerializableException("Datagram Serializable Creating Error");
        }
        return new DatagramPacket(sending, sending.length, serverAdress, port);
    }

    private void doCommand(Command command) throws IOException {
        DatagramPacket senderDPacket;
        senderDPacket = createDPacket(command);
        String response = getServerOutput(senderDPacket);
        System.out.println(response);
        isWorking = response.contains("Exit") ? false : true;
    }


    private void doCommand() throws IOException {
        Command command = UserHandler.getInput("Type command (help): ", HELP, defaultFilename);
        doCommand(command);
    }

    private void work() throws IOException {
        System.out.println("Ready");
        while (isWorking) {
            doCommand();
        }
        System.out.println("End connection");
    }

    private void logIn() {
        boolean isLogin = false;
        String login;
        String password;

        System.out.print("Please autorizate in________" +
                "\n\tRegister | new user" +
                "\n\tSing In" +
                "\nType command: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine().toLowerCase();
        System.out.println(s);
        while (!isLogin) {
            while (!(s.equals("register")) && (!s.equals("sing in"))) {
                System.out.println("No such command");
                System.out.println("Type command: ");
                s = in.nextLine().trim();
            }
            switch (s) {
                case "register":
                    System.out.println("\tType Login : ");
                    login = in.nextLine().trim();

                    System.out.println("\tType EMAIL : ");
                    String email = in.nextLine().trim();
                    password = DataBaseManager.getRandomPassword(8);
                    if (dataBaseManager.registerUser(login, email, password)) {
                        System.out.println("Successful registration");
                        System.out.println("Your password: " + password);
                        isLogin = true;
                    } else {
                        System.out.println("Registration failed. User already exist");
                    }
                    break;

                case "sing in":
                    System.out.print("\tType login: ");
                    login = in.nextLine().trim();
                    if (!dataBaseManager.getUserLogins().contains(login)){
                        System.out.print("Wrong login");
                        break;
                    }
                    System.out.print("\tType password : ");
                    password = in.nextLine().trim();
                    if (dataBaseManager.checkPass(login, password)){
                        System.out.println("Sing In success");
                        isLogin = true;
                    } else {
                        System.out.println("Wrong password");
                    }
                    break;

            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                Client sender = new Client("localhost", Integer.valueOf(args[0]));
                System.out.println("Hello there");
//                sender.logIn();
                sender.testServerConnection();
                sender.work();
            }catch (SQLException ex){
                ex.printStackTrace();
                exit(-1);
            } catch (Exception e) {
                System.err.println("Error. Lost connection");
                e.printStackTrace();
            }
        } else System.out.println("Write correct port number");
    }
}
