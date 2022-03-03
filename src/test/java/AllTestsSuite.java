import db.ConnectionPoolTest;
import dbdao.DbDaoSuiteTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    private static Test suite() {
        TestSuite testSuite = new TestSuite();
        testSuite.addTest(ConnectionPoolTest.suite());
        testSuite.addTest(DbDaoSuiteTest.suite());

        return testSuite;
    }
}
