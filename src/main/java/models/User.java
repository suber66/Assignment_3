package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

public class User {
    private int id;
    private String nickname;
    private HashSet<Game> OwnedGames = new HashSet<>();

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    public HashSet<Game> getOwnedGames() {
        return OwnedGames;
    }


    public User(int id, String nickname, Connection con)throws SQLException {
        this.id = id;
        this.nickname = nickname;
        var selectSQL = "SELECT game_id, name, price, user_id, nickname FROM public.ownedgames JOIN users ON ownedgames.user_id = users.id JOIN games ON ownedgames.game_id = games.id WHERE user_id = ?";
        var preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1,id);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int gameID = resultSet.getInt("game_id");
            String gameName = resultSet.getString("name");
            double gamePrice = resultSet.getDouble("price");
            Game game = new Game(gameID,gameName,gamePrice);
            OwnedGames.add(game);
        }
    }

    @Override
    public String toString() {
        return id + ": " + nickname;
    }




}
