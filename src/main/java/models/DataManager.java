package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class DataManager {
    private HashMap<Integer,User> userByID = new HashMap<>();
    private HashMap<Integer,Game> gameByID = new HashMap<>();
    static String JDBC_URL = "jdbc:postgresql://localhost:5432/SimpleDB?currentSchema=public&user=postgres&password=0000";
    Connection con = null;
    public DataManager() {
        try {

            con = DriverManager.getConnection(JDBC_URL);

            var selectSQL = "SELECT * FROM users";
            var statement = con.createStatement();
            var resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nickname = resultSet.getString("nickname");
                double money_spent = resultSet.getDouble("money_spent");
                User user = new User(id,nickname,money_spent,con);
                userByID.put(id,user);
            }
            selectSQL = "SELECT * FROM games";
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Game game = new Game(id,name,price);
                gameByID.put(id,game);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
    }
}


    public User getUserByID (int id) {
        try {
            return userByID.get(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Game getGameByID (int id) {
        return gameByID.get(id);
    }

    public void AddOwnedGame (int gameID,int userID) {
        try {
            con = DriverManager.getConnection(JDBC_URL);
            var insertSQL = "INSERT INTO ownedgames(game_id, user_id) VALUES (?, ?)";
            var preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, gameID);
            preparedStatement.setInt(2, userID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }

    public void AddUser (String user_nickname) {
        try {
            con = DriverManager.getConnection(JDBC_URL);
            var insertSQL = "INSERT INTO users(nickname) VALUES (?)";
            var preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setString(1, user_nickname);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }
    public void BuyGame (int gameID,int userID) {
        try {
            con = DriverManager.getConnection(JDBC_URL);
            var insertSQL = "INSERT INTO ownedgames(game_id, user_id) VALUES (?, ?)";
            var preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, gameID);
            preparedStatement.setInt(2, userID);
            preparedStatement.execute();
            var selectSQL = "SELECT price FROM games WHERE id = ?";
            var preparedSelectStatement = con.prepareStatement(selectSQL);
            preparedSelectStatement.setInt(1, gameID);
            var resultset = preparedSelectStatement.executeQuery();
            resultset.next();
            double price = resultset.getDouble("price");
            var updateSQL = "UPDATE users SET money_spent = ? WHERE id = ?";
            var preparedUpdateStatement = con.prepareStatement(updateSQL);
            System.out.println(getUserByID(userID).getMoney_spent()+price);
            double new_money_spent = getUserByID(userID).getMoney_spent()+price;
            preparedUpdateStatement.setDouble(1, new_money_spent);
            preparedUpdateStatement.setInt(2, userID);
            preparedUpdateStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }

    public Iterable<Game> getAllGames () {
        return gameByID.values();
    }
    public Iterable<User> getAllUsers () {
        return userByID.values();
    }



}
