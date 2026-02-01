package models;

import java.sql.Connection;
import java.sql.SQLException;

public class Admin extends User{
    public Admin(int id, String nickname, double money_spent, String password, Connection con) throws SQLException {
        super(id, nickname, money_spent, password, con);
    }
}
