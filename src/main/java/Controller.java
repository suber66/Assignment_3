import models.*;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
    static public void Login(Scanner sc) throws SQLException {
        System.out.println("Write your login: ");
        String nickname = sc.next();
        System.out.println("Write your password");
        String password = sc.next();
        DataManager dm = new DataManager();
        if (dm.getAdminByNickname(nickname) != null) {
            if (dm.getAdminByNickname(nickname).getPassword().equals(password)) {
                AdminLogged(sc,dm.getAdminByNickname(nickname));
            }
            else {
                System.out.println("Wrong password");
            }
        }
        else if (dm.getDeveloperByNickname(nickname) != null) {
            if (dm.getDeveloperByNickname(nickname).getPassword().equals(password)) {
                DeveloperLogged(sc,dm.getDeveloperByNickname(nickname));
            }
            else {
                System.out.println("Wrong password");
            }
        }
        else if (dm.getUserByNickname(nickname) != null) {

            if (dm.getUserByNickname(nickname).getPassword().equals(password)) {
                UserLogged(sc,dm.getUserByNickname(nickname));
            }
            else {
                System.out.println("Wrong password");
            }
        }
        else {
            System.out.println("Wrong nickname");
        }
    }
    static public void UserLogged(Scanner sc, User user) throws SQLException {
        boolean run = true;
        System.out.println("Welcome " + user.getNickname());
        while(run) {
            System.out.println("Choose operation:\n1:Show all games\n2:Show my games\n3:Buy game\n4:Check how many you spent\n5:Exit from account");
            DataManager dm = new DataManager();
            user = dm.getUserByNickname(user.getNickname());
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    ShowAllGames();
                    break;
                case 2:
                    AllUserGames(user.getId());
                    break;
                case 3:
                    UnownedUserGames(user.getId());
                    System.out.println("Choose Game ID");
                    BuyGame(sc.nextInt(),user.getId());
                    break;
                case 4:
                    System.out.println("$" + user.getMoney_spent());
                case 5:
                    run = false;
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
    static public void DeveloperLogged(Scanner sc, Developer dev) throws SQLException {
        boolean run = true;
        System.out.println("Welcome " + dev.getNickname());
        while(run) {
            System.out.println("Choose operation:\n1:Show all games\n2:Show my games\n3:Buy game\n4:Check how many you spent\n5:Develop a game\n6:Show developed games\n7:Check how many you gained\n8:Exit from account");
            DataManager dm = new DataManager();
            dev = dm.getDeveloperByNickname(dev.getNickname());
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    ShowAllGames();
                    break;
                case 2:
                    AllUserGames(dev.getId());
                    break;
                case 3:
                    UnownedUserGames(dev.getId());
                    System.out.println("Choose Game ID");
                    BuyGame(sc.nextInt(),dev.getId());
                    break;
                case 4:
                    System.out.println("$" + dev.getMoney_spent());
                    break;
                case 5:
                    DevelopGame(sc, dev.getId());
                    break;
                case 6:
                    AllDevelopedGames(dev.getId());
                    break;
                case 7:
                    System.out.println("$" + dev.getMoney_gained());
                    break;
                case 8:
                    run = false;
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
    static public void AdminLogged(Scanner sc, Admin admin) throws SQLException {
        boolean run = true;
        int UserID;
        int GameID;
        String UserNickname;
        String UserPassword;
        System.out.println("Welcome " + admin.getNickname());
        while (run) {
            System.out.print(" Choose operation:\n1:Show All Games\n2:Show Games of certain User\n3:Show All Users\n4:Change Nickname\n5:Assign Role to User\n6:Delete Role from User\n7:Exit from Account\n> ");
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
                    break;
                case 4:
                    ShowAllUsers();
                    System.out.println("Choose user ID");
                    UserID = sc.nextInt();
                    System.out.println("Write new nickname");
                    UserNickname = sc.next();
                    UpdateNickname(UserID,UserNickname);
                    break;
                case 5:
                    AddRoletoUser(sc);
                    break;
                case 6:
                    DeleteRoleFromUser(sc);
                    break;
                case 7:
                    run = false;
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
    static public void Start() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        String UserNickname;
        String UserPassword;
        while (run) {
            System.out.print("Choose operation:\n1:Login\n2:Create a new Account\n3:Exit\n> ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    Login(sc);
                    break;
                case 2:
                    System.out.println("Write nickname");
                    UserNickname = sc.next();
                    System.out.println("Write password");
                    UserPassword = sc.next();
                    AddUser(UserNickname,UserPassword);
                    break;
                case 3:
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
        Iterable<Game> games = dm.getAllGames();
        games.forEach(game -> {System.out.println(game);});
    }
    static public void ShowAllUsers() {
        DataManager dm = new DataManager();
        Iterable<User> users = dm.getAllUsers();
        users.forEach(user -> {System.out.println(user);});
    }
    static public void ShowAllDevelopers() {
        DataManager dm = new DataManager();
        Iterable<Developer> developers = dm.getAllDevelopers();
        developers.forEach(developer -> {System.out.println(developer);});
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
    static public void AllDevelopedGames(int id) {
        DataManager dm = new DataManager();
        if (dm.getDeveloperByID(id) == null) {
            System.out.println("Developer doesn't Exist");
        }
        else if (dm.getDeveloperByID(id).getDevelopedGames().isEmpty()) {
            System.out.println("You don't have developed games");
        }
        else {
            for (Game game: dm.getDeveloperByID(id).getDevelopedGames()) {
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
    static public void BuyGame (int gameID,int userID) throws SQLException {
        DataManager dm = new DataManager();
        if (dm.getUserByID(userID).getOwnedGames().contains(dm.getGameByID(gameID))) {
            System.out.println("You already own this game");
        }
        else {
            dm.BuyGame(gameID,userID);
            System.out.println("You successfully buy this game");
        }
    }
    static public void DevelopGame (Scanner sc, int developerID) {
        DataManager dm = new DataManager();
        sc.nextLine();
        System.out.println("Write name for your game: ");
        String gamename = sc.nextLine();
        double gameprice = 0;
        boolean run = true;
        while (run) {
            System.out.println("Write price for your game: ");
            try {
                gameprice = sc.nextDouble();
                run = false;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input");
                sc.nextLine();
            }
        }
        System.out.println("Choose genre for your game: \n1:MOBA\n2:Roguelike\n3:Metroidvania\n4:RPG");
        int input = sc.nextInt();
        String gamegenre = switch (input) {
            case 1 -> "MOBA";
            case 2 -> "Roguelike";
            case 3 -> "Metroidvania";
            case 4 -> "RPG";
            default -> "Unspecified";
        };
        dm.AddGame(gamename,gameprice,gamegenre,developerID);
        System.out.println("Game successfully added");
    }
    static public void AddUser (String nickname, String password) {
        DataManager dm = new DataManager();
        dm.AddUser(nickname,password);
        System.out.println("User successfully added");
    }
    static public void AddRoletoUser (Scanner sc) {
        DataManager dm = new DataManager();
        boolean run = true;
        while (run) {
            ShowAllUsers();
            System.out.println("Choose user id");
            int userID = sc.nextInt();
            System.out.println("Choose role: \n1:Developer\n2:Admin\n0:Exit");
            int role = sc.nextInt();
            if (dm.getUserByID(userID) == null) {
                System.out.println("Wrong id");
                continue;
            }
            if (!(dm.getAdminByID(userID) == null && dm.getDeveloperByID(userID) == null) && role != 0) {
                System.out.println("There is already role assigned");
                continue;
            }
            switch (role) {
                case 0:
                    run = false;
                    break;
                case 1, 2:
                    dm.AssignRoleToUser(userID, role);
                    run = false;
                    break;
                default:
                    System.out.println("Wrong role");
            }
        }
    }
    static public void DeleteRoleFromUser (Scanner sc) throws SQLException {
        DataManager dm = new DataManager();
        ShowAllUsers();
        System.out.println("Choose user id");
        int userID = sc.nextInt();
        System.out.println("Are you sure you want to delete role from " + dm.getUserByID(userID).getNickname() + "\n1:Yes\n2:No");
        int input = sc.nextInt();
        switch (input) {
            case 1:
                dm.DeleteUserRole(userID);
                break;
            case 2:
                System.out.println("Cancel");
                break;
            default:
                System.out.println("Wrong input");
                break;
        }
    }
    static public void UpdateNickname (int id, String new_nickname) {
        DataManager dm = new DataManager();
        dm.UpdateNickname(id, new_nickname);
        System.out.println("Nickname successfully changed");
    }
    static public void ShowUserByNickname (String nickname) {
        DataManager dm = new DataManager();
        if (dm.getUserByNickname(nickname) != null) {
            System.out.println(dm.getUserByNickname(nickname));
        }
        else {
            System.out.println("Wrong nickname");
        }
    }
    static public void ShowDeveloperByNickname (String nickname) {
        DataManager dm = new DataManager();
        if (dm.getDeveloperByNickname(nickname) != null) {
            System.out.println(dm.getDeveloperByNickname(nickname));
        }
        else {
            System.out.println("Wrong nickname");
        }
    }
}
