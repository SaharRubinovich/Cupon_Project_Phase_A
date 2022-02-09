package beans;

import java.util.Date;

public class Coupon {
    private int id;
    private int companyId;
    //todo: add Category enum
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
}
