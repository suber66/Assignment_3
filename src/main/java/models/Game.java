package models;

import java.util.Objects;

public class Game {
    private int id;
    private String name;
    private double price;

    public Game(int id,String name,double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        // Define similarity based on name and age attributes
        return id == game.id && Objects.equals(name, game.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id,name);
    }

    @Override
    public String toString() {
        return id + ": " + name + " $" + price;
    }
}
