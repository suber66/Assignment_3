package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

public class DataManager {
    private HashMap<Integer,User> userByID = new HashMap<>();
    private HashMap<String,User> userByNickname = new HashMap<>();
    private HashMap<Integer,Game> gameByID = new HashMap<>();
    private HashMap<String,Game> gameByName = new HashMap<>();
    private HashMap<Integer,Developer> developerByID = new HashMap<>();
    private HashMap<String,Developer> developerByNickname = new HashMap<>();
    private HashMap<Integer,Admin> adminByID = new HashMap<>();
    private HashMap<String,Admin> adminByNickname = new HashMap<>();
    Connection con = null;
    public DataManager() {
        try {

            con = DBConnection.getInstance();
            var selectSQL = "SELECT * FROM users";
            var statement = con.createStatement();
            var resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nickname = resultSet.getString("nickname");
                double money_spent = resultSet.getDouble("money_spent");
                String password = resultSet.getString("password");
                User user = new User(id, nickname, money_spent, password, con);
                userByID.put(id, user);
                userByNickname.put(nickname.toLowerCase(), user);
            }
            selectSQL = "SELECT * FROM public.developers JOIN users ON user_id = users.id";
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String nickname = resultSet.getString("nickname");
                double money_spent = resultSet.getDouble("money_spent");
                double money_gained = resultSet.getDouble("money_gained");
                String password = resultSet.getString("password");
                Developer developer = new Developer(id, nickname, money_spent, money_gained, password, con);
                developerByID.put(id, developer);
                developerByNickname.put(nickname.toLowerCase(), developer);
            }
            selectSQL = "SELECT * FROM admins JOIN users ON user_id = users.id";
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String nickname = resultSet.getString("nickname");
                double money_spent = resultSet.getDouble("money_spent");
                String password = resultSet.getString("password");
                Admin admin = new Admin(id,nickname,money_spent,password,con);
                adminByID.put(id, admin);
                adminByNickname.put(nickname.toLowerCase(), admin);
            }
            selectSQL = "SELECT * FROM games";
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String genre = resultSet.getString("genre");
                Game game = new Game(id, name, price, genre);
                gameByID.put(id, game);
                gameByName.put(name.toLowerCase(), game);
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
    public User getUserByNickname (String nickname) {
        try {
            return userByNickname.get(nickname.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Game getGameByID (int id) {
        try {
            return gameByID.get(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Game getGameByName (String nickname) {
        try {
            return gameByName.get(nickname.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Developer getDeveloperByID (int id) {
        try {
            return developerByID.get(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Developer getDeveloperByNickname (String nickname) {
        try {
            return developerByNickname.get(nickname.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Admin getAdminByID (int id) {
        try {
            return adminByID.get(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public Admin getAdminByNickname (String nickname) {
        try {
            return adminByNickname.get(nickname.toLowerCase());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public void AddOwnedGame (int gameID,int userID) {
        try {
            con = DBConnection.getInstance();
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

    public void AddGame (String gamename, double gameprice, String gamegenre, int developerID) {
        try {
            con = DBConnection.getInstance();
            var insertSQL = "INSERT INTO public.games(name, price, genre) VALUES (?, ?, ?)";
            var preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setString(1, gamename);
            preparedStatement.setDouble(2, gameprice);
            preparedStatement.setString(3, gamegenre);
            preparedStatement.execute();
            var selectSQL = "SELECT * FROM public.games WHERE name = ? AND price = ? AND genre = ?";
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, gamename);
            preparedStatement.setDouble(2, gameprice);
            preparedStatement.setString(3, gamegenre);
            var resultset = preparedStatement.executeQuery();
            resultset.next();
            int gameID = resultset.getInt("id");
            insertSQL = "INSERT INTO developedgames(developer_id, game_id) VALUES (?, ?)";
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, developerID);
            preparedStatement.setInt(2, gameID);
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
    public void AddUser (String user_nickname, String user_password) {
        try {
            con = DBConnection.getInstance();
            var insertSQL = "INSERT INTO users(nickname,password) VALUES (?,?)";
            var preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setString(1, user_nickname);
            preparedStatement.setString(2, user_password);
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
    public void AssignRoleToUser (int userID, int role) {
        try {
            switch (role) {
                case 1:
                    con = DBConnection.getInstance();
                    var updateSQL = "INSERT INTO developers(user_id) VALUES (?)";
                    var preparedStatement = con.prepareStatement(updateSQL);
                    preparedStatement.setInt(1, userID);
                    preparedStatement.execute();
                    break;
                case 2:
                    con = DBConnection.getInstance();
                    updateSQL = "INSERT INTO admins(user_id) VALUES (?)";
                    preparedStatement = con.prepareStatement(updateSQL);
                    preparedStatement.setInt(1, userID);
                    preparedStatement.execute();
                    break;
            }
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
    public void DeleteUserRole (int userID) throws SQLException {

        try {
            con = DBConnection.getInstance();
            if (getAdminByID(userID) != null) {
                var deleteSQL = "DELETE FROM admins WHERE user_id = ?";
                var preparedStatement = con.prepareStatement(deleteSQL);
                preparedStatement.setInt(1,userID);
                preparedStatement.execute();
                System.out.println("User role deleted");
            }
            else if (getDeveloperByID(userID) != null) {
                var deleteSQL = "DELETE FROM developers WHERE user_id = ?";
                var preparedStatement = con.prepareStatement(deleteSQL);
                preparedStatement.setInt(1,userID);
                preparedStatement.execute();
                System.out.println("User role deleted");
            }
            else {
                System.out.println("User doesn't have roles");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
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
            con = DBConnection.getInstance();
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
            double new_money_spent = getUserByID(userID).getMoney_spent()+price;
            preparedUpdateStatement.setDouble(1, new_money_spent);
            preparedUpdateStatement.setInt(2, userID);
            preparedUpdateStatement.execute();
            selectSQL = "SELECT * FROM public.developedgames JOIN developers ON developer_id = developers.id WHERE game_id = ?";
            preparedSelectStatement = con.prepareStatement(selectSQL);
            preparedSelectStatement.setInt(1, gameID);
            resultset = preparedSelectStatement.executeQuery();
            while (resultset.next()) {
                double money_gained = resultset.getDouble("money_gained");
                int devID = resultset.getInt("developer_id");
                double new_money_gained = money_gained+price;
                updateSQL = "UPDATE developers SET money_gained = ? WHERE id = ?";
                preparedUpdateStatement = con.prepareStatement(updateSQL);
                preparedUpdateStatement.setDouble(1, new_money_gained);
                preparedUpdateStatement.setInt(2, devID);
                preparedUpdateStatement.execute();

            }
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

    public void UpdateNickname(int id, String new_nickname){
        try {
            con = DBConnection.getInstance();
            var updateSQL = "UPDATE users SET nickname = ? WHERE id = ?";
            var preparedStatement = con.prepareStatement(updateSQL);
            preparedStatement.setString(1, new_nickname);
            preparedStatement.setInt(2, id);
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

    public Iterable<Game> getAllGames () {
        return gameByID.values();
    }
    public Iterable<User> getAllUsers () {
        return userByID.values();
    }
    public Iterable<Developer> getAllDevelopers () {
        return  developerByID.values();
    }
    public Iterable<Admin> getAllAdmins () {
        return  adminByID.values();
    }

}
