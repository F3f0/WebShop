package Controller;

import Model.*;
import Repository.Repository;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Control {
    Repository rep = new Repository();


    public String axsShop() throws IOException {
        Scanner scan = new Scanner(System.in);
        Boolean axsConfirmed = false;

        while (!axsConfirmed) {
            System.out.println("Welcome to the webshop \n Insert your name: ");
            String nameIn = scan.nextLine();
            System.out.println("Insert your password: ");
            String passwordIn = scan.nextLine();

            if (rep.checkCustomer(nameIn, passwordIn)) {
                axsConfirmed = true;
                return nameIn;
            } else
                System.out.println("Namnet finns inte i systemet eller felstavad");
        }
        return "";
    }

    public Customer getCustomerData(String nameIn) throws IOException {
        return rep.getCustomerData(nameIn);
    }


    public boolean checkCart(int custId) throws IOException {
        if (rep.hasCustomerShops(custId)) {
            return true;
        }
        return false;
    }

    public List<Product> getCustomersProduct (String name) throws SQLException, IOException {
        List<Product> list = rep.getProductByCustomer(name);
        return list;
    }

    public List<Object> getCart(int custId) throws SQLException, IOException {
        List<Object> cartList = new ArrayList<>();
        cartList = rep.getCartByCustomer(custId);
        return cartList;
    }

    public int getShopId(List<Object> list) {
        int shopId;
        Shop shop = (Shop) list.get(0);
        shopId = shop.getId();
        return shopId;
    }

    public String selectCategory() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nChose a category:\n-Heels\n-Oxford\n-Sneakers" +
                "\n-Boots\n-Running\n-Sandals");
        String chosenCategory = scan.nextLine();
        return chosenCategory;
    }

    public void printProduct (List<Product> list){
        for (Product i : list) {
            System.out.println(i.toString());
            }
        }


    public Product selectProduct(List<Product> list) {
        Scanner scan = new Scanner(System.in);
        Product prod = new Product();

        System.out.println("Chose a model");
        String chosenModel = scan.nextLine();
        System.out.println("Chose a color");
        String chosenColor = scan.nextLine();
        System.out.println("Chose a size");
        String chosenSize = scan.nextLine();

        for (Product i : list) {
            if (i.getModel().equalsIgnoreCase(chosenModel) &&
                    i.getColor().equalsIgnoreCase(chosenColor) &&
                    (i.getSize() == Integer.parseInt(chosenSize))) {
                prod = i;
                return prod;
            }
        }
        return null;
    }

    public void Shoooopaaaa(int customer, int shop, int product) throws IOException {
        rep.setCart(customer, shop, product);
    }

}

