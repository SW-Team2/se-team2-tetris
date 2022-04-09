package data;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    DataBase db = DataBase.getInstance();

    @Test
    void getConnection() {
        System.out.println(db.getConnection());
    }

    @Test
    void closeConnection() {

    }
}