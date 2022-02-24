package Model;

import java.util.Date;
import java.util.List;

public class Shop {
    int id;
    Date date;
    Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public Shop (){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    @Override
    public String toString() {
        return "Shop{" +
                ", date=" + date +
                ", customer=" + customer.getName() +
                ", products=" +
                '}';
    }
}
