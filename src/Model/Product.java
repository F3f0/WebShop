package Model;

import java.util.List;

public class Product {
    int id;
    String model;
    int price;
    int size;
    String color;
    int stock;
    String strbrand;
    Brand brand;
    List<Category> category;

    public Product (){}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return strbrand;
    }

    public void setBrand(String brand) {
        this.strbrand = brand;
    }

    @Override
    public String toString() {
        return "Model: " + model  +
                " from " + strbrand +
                " Size: " + size +
                " Color: " + color +
                " Price: " + price ;
    }
}
