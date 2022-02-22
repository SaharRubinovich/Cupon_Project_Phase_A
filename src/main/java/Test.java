import Jobs.CouponExpirationDailyJob;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.DbManager;
import exceptions.AdminFacadeException;
import exceptions.CompanyFacadeException;
import exceptions.CustomerFacadeException;
import exceptions.LoginException;
import facade.*;
import tableprinter.TablePrinter;

import java.sql.Date;
import java.time.LocalDate;

public class Test {
    private static CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
    private static ClientFacade client;
    private static LoginManager loginManager = LoginManager.getInstance();
    private static AdminFacade adminFacade;
    private static CompanyFacade companyFacade = new CompanyFacade();
    private static CustomerFacade customerFacade = new CustomerFacade();
    public static TablePrinter tablePrinter = new TablePrinter();


    public static void testAll() {
        try {
            DbManager.dropCompleteDb();
            DbManager.buildCompleteDb();

            Thread job = new Thread(couponExpirationDailyJob);
            job.start();

            client = loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
            adminFacade = (AdminFacade) client;
            System.out.println("Login attempted succeed?: " + adminFacade.login("admin@admin.com", "admin"));
            Company faker = new Company(0, "Faker", "Faker@gmail.com", "Fk123");
            Company who = new Company(0, "Who?", "Testing@walla.com", "Wh123");
            Company company = new Company(0,"testing","testing@email.com","Tt123");
            adminFacade.addCompany(faker);
            adminFacade.addCompany(who);
            adminFacade.addCompany(company);
            TablePrinter.print(adminFacade.getAllCompanies());
            who.setEmail("who@gmail.com");
            adminFacade.updateCompany(who);
            adminFacade.deleteCompany(faker.getId());
            TablePrinter.print(adminFacade.getAllCompanies());
            System.out.println("get one company:");
            TablePrinter.print(adminFacade.getOneCompany(who.getId()));
            Customer customer = new Customer(0, "Sahar", "Rubinovich", "sahar1326@gmail.com", "1234567");
            Customer customer1 = new Customer(0, "Rivka", "Rubinovich", "Rivkar2@walla.com", "1234567");
            adminFacade.addCustomer(customer);
            adminFacade.addCustomer(customer1);
            TablePrinter.print(adminFacade.getAllCustomers());
            customer.setEmail("sahar2613@gmail.com");
            adminFacade.updateCustomer(customer);
            adminFacade.deleteCustomer(customer1.getId());
            TablePrinter.print(adminFacade.getAllCustomers());
            TablePrinter.print(adminFacade.getOneCustomer(customer.getId()));

            client = loginManager.login("who@gmail.com", "Wh123", ClientType.Company);
            System.out.println("Login attempted succeed?: " + companyFacade.login("who@gmail.com", "Wh123"));
            Coupon coupon1 = new Coupon(0, who.getId(), 1, "Test", "Test",
                    Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(12)), 30, 23.30, "none");
            Coupon coupon2 = new Coupon(0, who.getId(), 1, "Test2", "Test2",
                    Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(17)), 70, 25.50, "none");
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            TablePrinter.print(companyFacade.getCompanyCoupons());
            coupon1.setDescription("New description");
            companyFacade.updateCoupon(coupon1);
            companyFacade.deleteCoupon(coupon2.getId());
            TablePrinter.print(companyFacade.getCompanyCoupons());
            TablePrinter.print(companyFacade.getCompanyDetails());

            client = loginManager.login("sahar2613@gmail.com", "1234567", ClientType.Customer);
            System.out.println("Login attempted succeed?: " + customerFacade.login("sahar2613@gmail.com", "1234567"));
            customerFacade.purchaseCoupon(coupon1);
            TablePrinter.print(customerFacade.getCustomerDetails());
            TablePrinter.print(customerFacade.getCustomerCoupons());

            couponExpirationDailyJob.stop();
            System.out.println("Daily job stopped");
            System.exit(0);
        } catch (AdminFacadeException | LoginException | CompanyFacadeException | CustomerFacadeException e) {
            System.out.println(e.getMessage());
        }

    }
}
