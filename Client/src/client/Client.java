package client;

import java.io.*;
import java.net.*;

import CarlsonProject.UserHandler;
import CarlsonProject.commands.Command;
import server.DataBaseManager;

import javax.mail.internet.AddressException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.exit;
import static server.DataBaseManager.getMD5;

public class Client {
    private Socket socket;
    private InetAddress serverAdress;
    private int port;
    private boolean isWorking;
    private int userId;
    private String defaultFilename;
    private HashSet<String> user_logins = new HashSet<>();
    private DataBaseManager dataBaseManager;
    private Tunnel tunnel;
    private String login = "";
    private String password = "";

    public Client(String serverAdress, int port) throws IOException, SQLException {
        isWorking = true;
        dataBaseManager = new DataBaseManager("studs", 7878, true);
        this.serverAdress = InetAddress.getByName(serverAdress);
        this.port = port;
        this.tunnel = new Tunnel("helios.se.ifmo.ru",
                "s264449",
                "cfv571",
                2222,
                "localhost",
                port,
                port);
        this.tunnel.connect();
        socket = new Socket("localhost", port);

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

    private void work() throws IOException {
        try (OutputStream os = socket.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os);

             InputStream is = socket.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(is)) {

            System.out.println("Ready");
            String response;

            while (isWorking) {
                Command command = UserHandler.getInput("Type command (help): ", HELP, defaultFilename);
                command.setUserID(userId);
                command.setUserHash(login, password);
                oos.writeObject(command);
                try {
                    response = (String) ois.readObject();
                    System.out.println(response);
                    isWorking = response.contains("Exit") ? false : true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("End connection");
        }
    }

    private void logIn() {
        boolean isLogin = false;
        System.out.print("Please autorizate in________" +
                "\n\tRegister | new user" +
                "\n\tSing In" +
                "\nType command: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine().toLowerCase();
        System.out.println(s);
        while (!isLogin) {

            while (!(s.equals("register")) && (!s.equals("sing in"))) {
                if (!s.equals(" "))
                    System.out.println("No such command");
                System.out.println("Type command: ");
                s = in.nextLine().trim();
            }
            switch (s) {
                case "register":
                    s = " ";
                    System.out.println("\tType Login : ");
                    login = in.nextLine().toLowerCase().trim();

                    System.out.println("\tType EMAIL : ");
                    String email = in.nextLine().trim();
                    password = DataBaseManager.getRandomPassword(4);
                    System.out.println("Your password: " + password);
                    try{
                        EmailManager.sendEmail(email, "Registration", EmailManager.getPassMessage(login, password));
                    } catch (AddressException e){
                        System.err.println("Unable to send mail. Wrong address");
                    }
                    if (dataBaseManager.registerUser(login, email, password)) {
                        System.out.println("Successful registration");
                        System.out.println("Your password: " + password);
                        isLogin = true;
                        userId = dataBaseManager.getUserID(login);
                    } else {
                        System.out.println("Registration failed. User already exist");
                    }
                    break;

                case "sing in":
                    s = " ";
                    System.out.print("\tType login: ");
                    login = in.nextLine().trim();
                    if (!dataBaseManager.getUserLogins().contains(login)) {
                        System.out.print("Wrong login\n");
                        break;
                    }
                    System.out.print("\tType password : ");
                    password = in.nextLine().trim();
                    if (dataBaseManager.checkPass(login, password)) {
                        System.out.println("Sing In success");
                        isLogin = true;
                        userId = dataBaseManager.getUserID(login);
                    } else {
                        System.out.println("Wrong password");
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.print("Try to connect server");
            try {
                System.out.println();
                Client sender = new Client("localhost", Integer.valueOf(args[0]));
                System.out.println("Hello there");
                System.out.println("Server connected");
                sender.logIn();
                sender.work();
            } catch (SQLException ex) {
                ex.printStackTrace();
                exit(1);
            } catch (IOException ioe) {
                System.err.println("Server is unavaliable");
                    ioe.printStackTrace();
                exit(1);
            } catch (Exception e) {
                System.err.println("Error. Lost connection");
//                e.printStackTrace();
                exit(1);
            }
        } else System.out.println("Write correct port number");
    }
}
