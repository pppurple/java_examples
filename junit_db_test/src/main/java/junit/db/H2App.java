package junit.db;

import java.sql.*;

public class H2App {
    public static void main(String[] a) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
                getConnection("jdbc:h2:~/test", "sa", "");

        Statement stmt = conn.createStatement();
        ResultSet rs = null;

        try {
            stmt.execute("drop table SHOP");
            stmt.execute("create table SHOP ( "
                    + "id int primary key,"
                    + "name varchar(2000),"
                    + "price int,"
                    + "update_date date"
                    + ")" );
            stmt.execute("insert into SHOP values (1, 'item1', 100, sysdate)");
            stmt.execute("insert into SHOP values (2, 'ppppa', 200, sysdate)");
            rs = stmt.executeQuery("select * from SHOP");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        while (rs.next()) {
            System.out.println("id : " + rs.getInt("id"));
            System.out.println("name : " + rs.getString("name"));
            System.out.println("price : " + rs.getInt("price"));
            System.out.println("update : " + rs.getDate("update_date"));
        }
        conn.commit();

        stmt.close();
        rs.close();
        conn.close();
    }
}
