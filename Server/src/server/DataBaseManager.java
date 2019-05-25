package server;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;
import client.Tunnel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SocketFactory;

import javax.xml.bind.SchemaOutputResolver;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import static CarlsonProject.WindowsArrayList.fromJSONToWindow;
import static CarlsonProject.WindowsArrayList.fromStringToJSONObject;


public class DataBaseManager {
    private String url = "jdbc:postgresql://localhost:5432/studs";
    private String user = "s264449";
    private String password = "cfv571";
    private String host = "helios.se.ifmo.ru";
    private Connection connection;
    private int assignPort =0;

    private HashSet<String> user_logins = new HashSet<>();



    /**
     * Constructor for DataBaseManager
     * Connect to DataBase studs on helios
     *
     * @throws SQLException
     */
    public DataBaseManager() throws SQLException {
        try {
            String rhost = InetAddress.getLocalHost().toString().split("/")[1];
            Tunnel tunnel = new Tunnel("helios.se.ifmo.ru",
                    "s264449",
                    "cfv571",
                    2222,
                    rhost,
                    3443,
                    5432);
            assignPort = tunnel.connect();
            if(assignPort == -1) return;
            Class.forName("org.postgresql.Driver");
            try {
                System.out.println("Try to connect database...");
//                DriverManager.setLoginTimeout(10);
//                System.out.println("Try to connect database...");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + 5432 + "/studs", "s264449", "cfv571");
                Statement s = connection.createStatement();
//                DriverManager.setLoginTimeout(10);

                System.out.println("Database connected!");
                System.out.println(connection == null);

            } catch (NullPointerException ex) {
                System.out.println("Database connection error");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PSQL Driver loading error");
        } catch (UnknownHostException ex){
            ex.printStackTrace();
        }
        System.out.println("Database connected");
    }

    /**
     * Get MD5 hash from string
     *
     * @param st string to get hash from
     * @return MD5 hash in HEX format
     */
    public static String getMD5(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    public boolean add(Window window) {

        return true;
    }

    public WindowsArrayList getWindows() {
        CopyOnWriteArrayList<Window> windows = new CopyOnWriteArrayList<>();
        WindowsArrayList windowsArrayList = new WindowsArrayList(windows);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM collection")) {
            while (rs.next()) {
                windowsArrayList.add(
                        fromJSONToWindow(
                                fromStringToJSONObject("color: " + rs.getString("hole_chance") +
                                        " speakChance: " + rs.getString("speak_chance") +
                                        " holeChance: " + rs.getString("hole_chance") +
                                        " openChance: " + rs.getString("open_chance") +
                                        " robberChance: " + rs.getString("robber_chance"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return windowsArrayList;
    }

    public boolean checkPass(String login, String password) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                if (login.equals(rs.getString("login"))) {
                    if (DataBaseManager.getMD5(password)
                            .equals(rs.getString("password_hash")))
                        return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Database connection error");
            return false;
        }
        return false;
    }

    public boolean registerUser(String login, String email, String password) {
        if (!user_logins.contains(login)) {
            user_logins.add(login);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeQuery("INSERT INTO users(EMAIL, LOGIN, PASSWORD_HASH)" +
                        "VALUE (" + email + ", " + login + ", " + getMD5(password) + ")");
            } catch (SQLException ex) {
                System.err.println("Database add failed");
                return false;
            }
            return true;
        } else return false;
    }

    public static String getRandomPassword(int len) {
        String password = "";
        for (int i = 0; i < len; i++) {
            password += (char) ((int) (Math.random() * 20) + 65);
        }
        return password;
    }

    public HashSet<String> getUserLogins() {
        HashSet<String> logins = new HashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                logins.add(rs.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logins;
    }
}