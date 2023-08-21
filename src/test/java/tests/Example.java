package tests;

import org.testng.annotations.Test;
import util.TestBase;

public class Example extends TestBase {

    @Test
    public void testConnectionPool() {
        dbHelper.insertDataToDB();
        dbHelper.deleteDataFromDB();
    }
}
