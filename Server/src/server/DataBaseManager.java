package server;

import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;
import client.Tunnel;
import org.json.simple.JSONObject;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import static CarlsonProject.WindowsArrayList.fromJSONToWindow;
import static CarlsonProject.WindowsArrayList.fromStringToJSONObject;
import static java.lang.System.exit;
import static java.lang.System.out;


public class DataBaseManager {
    private String user = "user";
    private String password = "password";
    private String host = "helios.se.ifmo.ru";
    private String rhost = "pg";
    private Connection connection;
    private Statement stmt = null;
    private int assignPort = 0;
    private transient PrintStream out;
    private HashSet<String> user_logins = new HashSet<>();


    /**
     * @param dbName   database name
     * @param lPort    local port
     * @param isTunnel if need tunnel, should to be TRUE
     * @throws SQLException throws if cant connect database
     */
    public DataBaseManager(String dbName, int lPort, boolean isTunnel) throws SQLException {
        try {
            this.out = System.out;
            Tunnel tunnel = new Tunnel(host,
                    user,
                    password,
                    2222,
                    rhost,
                    lPort,
                    5432);
            if (isTunnel)
                assignPort = tunnel.connect();
            Class.forName("org.postgresql.Driver");

            try {
                System.out.println("Try to connect database...");

                connection = DriverManager
                        .getConnection("jdbc:postgresql://localhost:" + assignPort + "/" + dbName,
                                user,
                                password);
                this.stmt = connection.createStatement();
                System.out.println("Database connected!");
            } catch (NullPointerException ex) {
                System.out.println("Database connection error");
                exit(1);

            }
        } catch (ClassNotFoundException e) {
            System.out.println("PSQL Driver loading error");
            exit(1);
        }
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

        while (md5Hex.length() < 31) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex.substring(0, 31);
    }

    /**
     * @return
     */
    public WindowsArrayList getWindows() {
        CopyOnWriteArrayList<Window> windows = new CopyOnWriteArrayList<>();
        WindowsArrayList windowsArrayList = new WindowsArrayList(windows);
        try {

            ResultSet rs = stmt.executeQuery("SELECT * FROM collection");
            while (rs.next()) {
                String str = "{\"color\": " + "\"" + rs.getString("color").toLowerCase() + "\"," +
                        " \"speakChance\": " + "\"" + rs.getString("speak_chance") + "\"" + "," +
                        " \"holeChance\": " + "\"" + rs.getString("hole_chance") + "\"" + "," +
                        " \"openChance\": " + "\"" + rs.getString("open_chance") + "\"" + "," +
                        " \"robberChance\": " + "\"" + rs.getString("robber_chance") + "\"" + "}";
                System.out.println(str);
                windowsArrayList.add(
                        fromJSONToWindow(
                                fromStringToJSONObject(str)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return windowsArrayList;
    }

    /**
     * @param hash
     * @param userID
     * @return
     */
    public boolean checkUser(String hash, int userID) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                if (Integer.valueOf(rs.getInt("user_id")).equals(Integer.valueOf(userID))) {
                    if (hash.equals(getMD5(rs.getString("login"))
                            + rs.getString("password_hash")))
                        return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Database connection error");
            exit(1);
            return false;
        }
        return false;
    }

    /**
     * @param login
     * @param password
     * @return
     */
    public boolean checkPass(String login, String password) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
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

    /**
     * @param window
     * @param Id
     * @param hash
     * @return
     */
    public boolean deleteWindow(Window window, int Id, String hash) {
        if (!checkUser(hash, Id)) {
            this.out.println("Wrong user and password");
            return false;
        }
        if (!(Id == window.getOwnerID())){
            this.out.println("Wrong user");
            return false;
        }
        JSONObject object = fromStringToJSONObject(window.toString());
        System.out.println(object.toString());
        String speakChance = "";
        String holeChance = "";
        String openChance = "";
        String robberChance = "";
        String color = "";
        color = (String) object.get("color");
        holeChance = (String) object.get("holeChance");
        speakChance = (String) object.get("speakChance");
        robberChance = (String) object.get("robberChance");
        openChance = (String) object.get("openChance");
        System.out.println("Try to remove " + window.toString());
        out.println("DELETE FROM collection" +
                " WHERE speak_chance = " + speakChance
                + " AND  hole_chance = " + holeChance
                + " AND  open_chance = " + openChance
                + " AND  robber_chance = " + robberChance
                + " AND color LIKE \'" + color +"\'");
        try {
            stmt.execute("DELETE FROM collection" +
                    " WHERE speak_chance = " + speakChance
                    + " AND  hole_chance = " + holeChance
                    + " AND  open_chance = " + openChance
                    + " AND  robber_chance = " + robberChance
                    + " AND color LIKE \'" + color + "\'");
            System.out.println("Database remove success");
            return true;
        } catch (SQLException ex) {
            System.err.println("Database delete failed");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @param window
     * @param Id
     * @return
     */
    public boolean addWindow(Window window, int Id) {
        window.setOwnerID(Id);
        JSONObject object = fromStringToJSONObject(window.toString());
        String values = "";
        String[] flagnames = {"speakChance", "holeChance", "robberChance", "openChance"};
        for (String flagname : flagnames) {
            if (object.get(flagname) != null) {
                values += (String) object.get(flagname) + ",";
            }
        }
        values += "\'" + (String) object.get("color") + "\' ,";
        values += Id;
        System.out.println(values);
        try {
            stmt.execute("INSERT INTO collection(SPEAK_CHANCE, HOLE_CHANCE, ROBBER_CHANCE, OPEN_CHANCE, COLOR, OWNER_ID)" +
                    "VALUES (" + values + ")");
            return true;
        } catch (SQLException ex) {
            System.err.println("Database add failed");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @param login
     * @param email
     * @param password
     * @return
     */
    public boolean registerUser(String login, String email, String password) {
        if (!user_logins.contains(login)) {
            try {
                System.out.println("INSERT INTO users(EMAIL, LOGIN, PASSWORD_HASH)" +
                        "VALUES (\'" + email + "\' , \'" + login + "\' , \'" + getMD5(password) + "\' )");
                stmt.execute("INSERT INTO users(EMAIL, LOGIN, PASSWORD_HASH)" +
                        "VALUES (\'" + email + "\' , \'" + login + "\' , \'" + getMD5(password) + "\' )");
                user_logins.add(login);
            } catch (SQLException ex) {
                System.err.println("Database add failed");
                ex.printStackTrace();
                return false;
            }
            return true;
        } else return false;
    }

    /**
     * @param len
     * @return
     */
    public static String getRandomPassword(int len) {
        String password = "";
        for (int i = 0; i < len; i++) {
            password += (char) ((int) (Math.random() * 20) + 65);
        }
        return password;
    }

    /**
     * @return
     */
    public HashSet<String> getUserLogins() {
        HashSet<String> logins = new HashSet<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                logins.add(rs.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logins;
    }

    public int getUserID(String login) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT login, user_id FROM users");
            while (rs.next()) {
                if (rs.getString("login").equals(login)) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
}