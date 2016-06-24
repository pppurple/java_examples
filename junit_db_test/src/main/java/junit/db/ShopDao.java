package junit.db;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pppurple on 2016/06/23.
 */
public class ShopDao {
    public List<String> getList() throws SQLException {
        ResultSet rs = createStatement().executeQuery("select name from shop");
        LinkedList<String> result = new LinkedList<>();
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }

    public void insert(String itemName, int price) throws SQLException {
        String sql = "insert into shop(name, price) "
                + " values('" + itemName + "'," + price + ")";
        createStatement().executeUpdate(sql);
    }

    private Statement createStatement() throws SQLException {
        //String url = "jdbc:h2:tcp://localhost/db;SCHEMA=ut";
        String url = "jdbc:h2:~/db;SCHEMA=ut";
        Connection connection = DriverManager.getConnection(url, "sa", "");
        return connection.createStatement();
    }
}
