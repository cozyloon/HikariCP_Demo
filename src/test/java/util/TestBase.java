package util;

import org.testng.annotations.AfterSuite;

import static common.Constants.*;

public class TestBase {
    public static DBHelper dbHelper = new DBHelper(DB_URL, DB_USERNAME, DB_PASSWORD);

    @AfterSuite
    public void closeDataSource() {
        dbHelper.closeDataSource();
    }
}
