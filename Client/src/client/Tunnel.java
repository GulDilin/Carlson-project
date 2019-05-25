package client;

import com.jcraft.jsch.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tunnel {
    private String host;
    private String user;
    private String password;
    private int port;
    private String tunnelRemoteHost;
    private int tunnelLocalPort;
    private int tunnelRemotePort;

    /**
     * SSH Tunnel constructor
     * @param host  Target SSH host, which you will be connected (example: "helios.se.ifmo.ru")
     * @param user  User on target SSH host
     * @param password  Password on target SSH host
     * @param port  Port on target SSH host (example: 2222)
     * @param tunnelRemoteHost  The host from where the tunnel is created (example: "localhost")
     * @param tunnelLocalPort   The port on target SSH host, which will be listen
     * @param tunnelRemotePort  The port on machine where tunnel is createl to SSH machine port
     */
    public Tunnel(String    host,
                  String    user,
                  String    password,
                  int       port,
                  String    tunnelRemoteHost,
                  int       tunnelLocalPort,
                  int       tunnelRemotePort){


        this.user = user;
        this.password = password;
        this.port = port;
        this.host = host;
        try {
            InetAddress.getByName(host);
        } catch (UnknownHostException e){
            System.out.println("Uncorrect SSH host");
            e.printStackTrace();
        }

        try {
            this.tunnelRemoteHost = InetAddress.getByName(tunnelRemoteHost).toString().split("/")[1];
        } catch (UnknownHostException e){
            System.out.println("Uncorrect Tunnel Remote host");
            e.printStackTrace();
        }
        this.tunnelRemotePort = tunnelRemotePort;
        this.tunnelLocalPort = tunnelLocalPort;
    }

    public int connect() {
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            localUserInfo lui = new localUserInfo();
            session.setUserInfo(lui);
            System.out.println("Establishing SSH connection to " +
                    host + " : " + port);
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(3000);
//            session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
            int assingedPort = session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
            System.out.println("Connected");
            try {
                System.out.println(InetAddress.getByName(tunnelRemoteHost).toString() + " : " + assingedPort + " -> " +
                        host + InetAddress.getByName(host).toString() + " : " + tunnelRemotePort);
            } catch (UnknownHostException e) {
                System.out.println("Unknown host");
            }
            return assingedPort;

        } catch (JSchException e) {
            System.out.println("SSH connection error");
            e.printStackTrace();
        }
            return -1;
    }

    class localUserInfo implements UserInfo {
        String passwd;
        public String getPassword(){ return passwd; }
        public boolean promptYesNo(String str){return true;}
        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){return true; }
        public boolean promptPassword(String message){return true;}
        public void showMessage(String message){}
    }

//    public dataBaseConnect() throws SQLException {

//    }
    public static void main(String[] args) throws UnknownHostException {
        System.out.println("helios");
        System.out.println(InetAddress.getByName("helios.se.ifmo.ru").toString().split("/")[1]);
        System.out.println("localhost");
        System.out.println(InetAddress.getLocalHost().toString().split("/")[1]);
            try {
                String rhost = InetAddress.getLocalHost().toString().split("/")[1];
                Tunnel tunnel = new Tunnel("helios.se.ifmo.ru",
                        "s264449",
                        "cfv571",
                        2222,
                        "localhost",
                        9080,
                        5432);
                int assignPort = tunnel.connect();
                if(assignPort == -1) return;
                Class.forName("org.postgresql.Driver");
                try {
                    System.out.println("Try to connect database...");
//                DriverManager.setLoginTimeout(10);
//                System.out.println("Try to connect database...");
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + 5432 + "/studs", "s264449", "cfv571");

//                DriverManager.setLoginTimeout(10);

                    System.out.println("Database connected!");
                    System.out.println(connection == null);

                } catch (NullPointerException | SQLException ex) {
                    System.out.println("Database connection error");
                    ex.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                System.out.println("PSQL Driver loading error");
            } catch (UnknownHostException ex){
                ex.printStackTrace();
            }
    }
}
