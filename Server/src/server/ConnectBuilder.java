package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectBuilder {
    Statement stmt;
    String name, url, login, password;
    public ConnectBuilder(String name, String url, String login, String password){
        this.name = name;
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public Statement getStatement() {
        try {
            Class.forName(name);
            Connection con = DriverManager.getConnection(url, login, password);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }
}
