import models.DataManager;
import models.Game;
import models.User;

import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    static public void Start() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        System.out.println("Welcome!");
        while (run) {
            System.out.print(" Choose operation:\n1:Show All Games\n2:Show Games of certain User\n3:Own certain Game to certain User\n4:Exit\n> ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    ShowAllGames();
                    break;
                case 2:
                    ShowAllUsers();
                    System.out.println("Choose User ID");
                    AllUserGames(sc.nextInt());
                    break;
                case 3:
                    ShowAllUsers();
                    System.out.println("Choose User ID");
                    int UserID = sc.nextInt();
                    UnownedUserGames(UserID);
                    System.out.println("Choose Game ID");
                    int GameID = sc.nextInt();
                    OwnGame(GameID,UserID);
                    break;
                case 4:
                    run = false;
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }
        sc.close();
    }

    static public void ShowAllGames() {
        DataManager dm = new DataManager();
        for (Game game: dm.getAllGames()) {
            System.out.println(game);

        }
    }
    static public void ShowAllUsers() {
        DataManager dm = new DataManager();
        for (User user: dm.getAllUsers()) {
            System.out.println(user);

        }
    }
    static public void AllUserGames(int id) {
        DataManager dm = new DataManager();
        if (dm.getUserByID(id) == null) {
            System.out.println("User doesn't Exist");
        }
        else if (dm.getUserByID(id).getOwnedGames().isEmpty()) {
            System.out.println("You don't have games");
        }
        else {
            for (Game game: dm.getUserByID(id).getOwnedGames()) {
                System.out.println(game);
            }
        }
    }
    static public void UnownedUserGames (int id) {
        DataManager dm = new DataManager();
        if (dm.getUserByID(id) == null) {
            System.out.println("User doesn't Exist");
        }
        else {
            for (Game game: dm.getAllGames()) {
                System.out.print(game);
                if (dm.getUserByID(id).getOwnedGames().contains(game)) {
                    System.out.println(" owned");
                }
                else {
                    System.out.println(" not owned");
                }
            }
        }
    }
    static public void OwnGame (int gameID,int userID) throws SQLException {
        DataManager dm = new DataManager();
        if (dm.getUserByID(userID).getOwnedGames().contains(dm.getGameByID(gameID))) {
                System.out.println("You already own this game");
        }
        else {
                dm.AddOwnedGame(gameID,userID);
                System.out.println("You successfully own this game");
        }
    }
}
