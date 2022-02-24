package Model;

import java.util.List;

public class Cart {
    Product product;
    Shop shop;
    List<Object> cartlist;

    public Cart(){}



    @Override
    public String toString() {
        return "Model: " + product.model  +
                " size: " + product.size +
                " color: " + product.color +
                " price: " + product.price;
    }
}
