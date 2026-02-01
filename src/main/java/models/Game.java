package models;

import java.util.Objects;

public class Game {
    private int id;
    private String name;
    private double price;
    private String genre;

    public Game(int id,String name,double price,String genre) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setGenre(String genre) {
        this.genre = genre;
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
    public String getGenre() {
        return genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && Objects.equals(name, game.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id,name);
    }

    @Override
    public String toString() {
        return id + ": " + name + " " + genre + " $" + price;
    }
}
