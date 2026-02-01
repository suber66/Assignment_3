package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;

public class User {
    private final int id;
    private String nickname;
    private double money_spent;
    private String password;
    private HashSet<Game> OwnedGames = new HashSet<>();

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setMoney_spent(double money_spent) { this.money_spent = money_spent; }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setOwnedGames(HashSet<Game> OwnedGames) {
        this.OwnedGames = OwnedGames;
    }
    public void addOwnedGame (Game game) {
        OwnedGames.add(game);
    }

    public int getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public double getMoney_spent() {
        return money_spent;
    }
    public String getPassword() {
        return password;
    }
    public HashSet<Game> getOwnedGames() {
        return OwnedGames;
    }


    public User(int id, String nickname, double money_spent, String password, Connection con)throws SQLException {
        this.id = id;
        this.nickname = nickname;
        this.money_spent = money_spent;
        this.password = password.toLowerCase();
        var selectSQL = "SELECT game_id, name, price, genre FROM public.ownedgames JOIN users ON ownedgames.user_id = users.id JOIN games ON ownedgames.game_id = games.id WHERE user_id = ?";
        var preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1,id);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int gameID = resultSet.getInt("game_id");
            String gameName = resultSet.getString("name");
            double gamePrice = resultSet.getDouble("price");
            String gameGenre = resultSet.getString("genre");
            Game game = new Game(gameID,gameName,gamePrice,gameGenre);
            OwnedGames.add(game);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(nickname, user.nickname) && Objects.equals(password, user.password);
    }
    @Override
    public String toString() {
        return id + ": " + nickname + " Money spent: $" + money_spent;
    }




}
