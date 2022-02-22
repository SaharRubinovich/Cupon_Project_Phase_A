package beans;

import java.util.Date;
import java.util.Objects;

public class Coupon {
    private int id;
    private int companyId;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon(int id, int companyId, int category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        setId(id);
        setCompanyId(companyId);
        setCategory(category);
        setTitle(title);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setAmount(amount);
        setPrice(price);
        setImage(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = Category.values()[category-1];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon)) return false;
        Coupon coupon = (Coupon) o;
        return getId() == coupon.getId() && getCompanyId() == coupon.getCompanyId() && getAmount() == coupon.getAmount() && Double.compare(coupon.getPrice(), getPrice()) == 0 && getCategory() == coupon.getCategory() && Objects.equals(getTitle(), coupon.getTitle()) && Objects.equals(getDescription(), coupon.getDescription()) && Objects.equals(getStartDate(), coupon.getStartDate()) && Objects.equals(getEndDate(), coupon.getEndDate()) && Objects.equals(getImage(), coupon.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCompanyId(), getCategory(), getTitle(), getDescription(), getStartDate(), getEndDate(), getAmount(), getPrice(), getImage());
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
