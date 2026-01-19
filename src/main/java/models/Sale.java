package models;

public class Sale {
    private int id;
    private int game_id;
    private double discount;

    public Sale(int id,int game_id,double discount) {
        this.id = id;
        this.game_id = game_id;
        this.discount = discount;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }
    public int getGame_id() {
        return game_id;
    }
    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return id + ": " + "GameID: " + game_id + " " + discount;
    }
}
