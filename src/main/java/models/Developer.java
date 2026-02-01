package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

public class Developer extends User{
    private double money_gained;
    private HashSet<Game> DevelopedGames = new HashSet<>();

    public void setMoney_gained(double money_gained) { this.money_gained = money_gained; }
    public void setDevelopedGames(HashSet<Game> DevelopedGames) {
        this.DevelopedGames = DevelopedGames;
    }
    public void addDevelopedGame (Game game) {
        DevelopedGames.add(game);
    }

    public double getMoney_gained() {
        return money_gained;
    }
    public HashSet<Game> getDevelopedGames() {
        return DevelopedGames;
    }

    public Developer(int id, String nickname, double money_spent, double money_gained, String password, Connection con) throws SQLException {
        super(id, nickname, money_spent, password, con);
        this.money_gained = money_gained;
        var selectSQL = "SELECT game_id, name, price, genre FROM public.developedgames JOIN developers ON developer_id = developers.id JOIN games ON game_id = games.id WHERE user_id = ?";
        var preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1,id);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int gameID = resultSet.getInt("game_id");
            String gameName = resultSet.getString("name");
            double gamePrice = resultSet.getDouble("price");
            String gameGenre = resultSet.getString("genre");
            Game game = new Game(gameID,gameName,gamePrice,gameGenre);
            DevelopedGames.add(game);
        }
    }

    @Override
    public String toString() {
        return getId() + ": " + getNickname() + " Money spent: $" + getMoney_spent() + " Money gained: $" + getMoney_gained();
    }

}
