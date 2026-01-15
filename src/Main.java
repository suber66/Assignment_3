import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    static String JDBC_URL = "jdbc:postgresql://localhost:5432/SimpleDB?currentSchema=public&user=postgres&password=0000";

    public static void main(String[] args) throws Exception {

        var connection = DriverManager.getConnection(JDBC_URL);
        CreateTable(connection);

        InsertValues(connection);

        var selectSQL = "SELECT * FROM users";
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(selectSQL);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id")+resultSet.getString("name"));
        }

    }

    private static void InsertValues(Connection connection) throws SQLException {
        var insertSQL = "insert into users(name) values(?)";
        var preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1 , "Sakebay");
        preparedStatement.execute();
    }

    private static void CreateTable(Connection connection) throws SQLException {
        var statement = connection.createStatement();
        var createTableStatement = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255))";
        statement.execute(createTableStatement);
    }
}
