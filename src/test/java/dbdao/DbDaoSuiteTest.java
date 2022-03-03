package dbdao;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runners.Suite;

public class DbDaoSuiteTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite(){
        TestSuite testSuite = new TestSuite();
        testSuite.addTest(CompaniesDbDaoTest.suite());
        testSuite.addTest(CustomersDbDaoTest.suite());
        testSuite.addTest(CouponsDbDaoTest.suite());
        return testSuite;
    }
}
