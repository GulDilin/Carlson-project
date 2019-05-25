package server;

import CarlsonProject.WindowsArrayList;

import java.sql.*;


public class LoadFromDb
{
    public static void load(){
        ConnectBuilder connectBuilder = new ConnectBuilder("org.postgresql.Driver", "jdbc:postgresql://pg:5432/studs",
                "s264449", "cfv571");
        try {
            Statement stmt = connectBuilder.getStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while(rs.next()) {
                String str = rs.getString("USER_ID") + ": " + rs.getString(2);
                System.out.println(str);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}