package Controller;

import Model.Customer;
import Model.Product;
import Repository.Repository;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopProgram {

    public ShopProgram() throws IOException, SQLException {
        String custName;
        String categoryName;
        String feedbackText;
        int shopId;
        int max = Integer.MAX_VALUE;
        int feedbackRate;
        Repository rep = new Repository();
        Control control = new Control();
        Customer custObj = new Customer();
        Product chosenProd = new Product();
        List<Product> prodlist = new ArrayList<>();
        List<Object> cartList = new ArrayList<>();

        custName = control.axsShop();
        custObj = control.getCustomerData(custName);


        if (control.checkCart(custObj.getId())) {
            System.out.println("\nHere's your cart "+ custName);
            control.printProduct(control.getCustomersProduct(custName));
        }

        prodlist= control.rep.getProductByCategory(control.selectCategory());
        control.printProduct(prodlist);
        chosenProd = control.selectProduct(prodlist);
        control.Shoooopaaaa(custObj.getId(), max, chosenProd.getId());

        System.out.println("Would you like to leave a feedback?\n give a product a rate" +
                "between 1 and 4, where 1 is \"I love those shoes!!!");
        Scanner scan = new Scanner(System.in);
        feedbackRate = Integer.parseInt(scan.nextLine());
        System.out.println("Please leave a free comment too: ");
        feedbackText = scan.nextLine();
        control.rep.setFeedback(feedbackRate, chosenProd.getId(), custObj.getId(), feedbackText);
        System.out.println("Thanks for your help!\n Here's the average rate for your product: \n");
        control.rep.getAverageScore(chosenProd.getModel());
    }
}
