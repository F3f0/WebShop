package Repository;

import Model.Category;
import Model.Customer;
import Model.Product;
import Model.Shop;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Repository {

    public Repository() {
    }

    public boolean checkCustomer(String name, String password) throws IOException {
        String query = "{? = CALL isRegistered(?,?)}";
        boolean isRegistered = false;
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));


        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {
            stmt.registerOutParameter(1, Types.BOOLEAN);
            stmt.setString(2, name);
            stmt.setString(3, password);
            stmt.execute();
            isRegistered = stmt.getBoolean(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRegistered;
    }

    public Customer getCustomerData(String nameIn) throws IOException {
        String query = "select * from customer where name = ?";
        Customer customer = new Customer();
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {
            stmt.setString(1, nameIn);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setTelef(rs.getString("telef"));
                customer.setPassword(rs.getString("password"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Category> getCategory() throws IOException {
        List<Category> catlist = new ArrayList<>();
        String query = "select * from category";
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                catlist.add(cat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catlist;
    }

    public List<Product> getProductByCategory(String category) throws IOException, SQLException {
        List<Product> productlist = new ArrayList<>();
        String query = "select item.id, model, price, size, color, stock, brand.name as brand from item \n" +
                "inner join brand\n" +
                "on brand.id = item.brandid\n" +
                "inner join belong\n" +
                "on belong.itemid = item.id\n" +
                "inner join category\n" +
                "on category.id = belong.categoryid\n" +
                "where category.name = ?;";
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt("id"));
                prod.setModel(rs.getString("model"));
                prod.setPrice(rs.getInt("price"));
                prod.setSize(rs.getInt("size"));
                prod.setColor(rs.getString("color"));
                prod.setStock(rs.getInt("stock"));
                prod.setBrand(rs.getString("brand"));
                productlist.add(prod);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productlist;
    }

    public List<Product> getProductByCustomer(String name) throws IOException, SQLException {
        List<Product> productlist = new ArrayList<>();
        String query = "select item.id, model, price, size, color, stock, brand.name as brand from item \n" +
                "inner join brand\n" +
                "on brand.id = item.brandid\n" +
                "inner join cart\n" +
                "on cart.itemid = item.id\n" +
                "inner join shop\n" +
                "on shop.id = cart.shopid\n" +
                "inner join customer\n" +
                "on customer.id = shop.customerid\n" +
                "where customer.name = ?;";
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt("id"));
                prod.setModel(rs.getString("model"));
                prod.setPrice(rs.getInt("price"));
                prod.setSize(rs.getInt("size"));
                prod.setColor(rs.getString("color"));
                prod.setStock(rs.getInt("stock"));
                prod.setBrand(rs.getString("brand"));
                productlist.add(prod);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productlist;
    }


    public boolean hasCustomerShops(int customerId) throws IOException {
        String query = "{? = CALL hasCustomerShop(?)}";
        boolean hasShopped = false;
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {
            stmt.registerOutParameter(1, Types.BOOLEAN);
            stmt.setInt(2, customerId);
            stmt.execute();
            hasShopped = stmt.getBoolean(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasShopped;
    }

    public List<Object> getCartByCustomer(int custID) throws IOException, SQLException {
        List<Object> list = new ArrayList<>();
        Shop shop = new Shop();
        Product prod = new Product();

        String query = "select shop.id, shopdate, item.id, item.model, item.price, item.size, item.color, item.stock, brand.name from shop \n" +
                "inner join cart\n" +
                "on cart.shopid = shop.id \n" +
                "inner join item\n" +
                "on item.id = cart.itemid\n" +
                "inner join brand\n" +
                "on item.brandId = brand.id\n" +
                "inner join customer\n" +
                "on shop.customerid = customer.id\n" +
                "where customer.id = ?;";
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, custID);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                shop.setId(rs.getInt("id"));
                shop.setDate(rs.getDate("shopdate"));
                prod.setId(rs.getInt("id"));
                prod.setModel(rs.getString("model"));
                prod.setPrice(rs.getInt("price"));
                prod.setSize(rs.getInt("size"));
                prod.setColor(rs.getString("color"));
                prod.setStock(rs.getInt("stock"));
                prod.setBrand(rs.getString("name"));
                list.add(shop);
                list.add(prod);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void setCart(int customer, int shop, int product) throws IOException {
        String query = "CALL addtocart(?, ?, ?)";
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));


        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {
            stmt.setInt(1, customer);
            stmt.setInt(2, shop);
            stmt.setInt(3, product);
            stmt.execute();
            System.out.println("Thx for your shop!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAverageScore(String nameIn) throws IOException {
        String query = "select item.model, avgScore(item.id) as Score, getAvgScoreAsText(item.id) as ScoreText, feedback.comment from item\n" +
                "inner join feedback\n" +
                "on item.id = feedback.itemid\n" +
                "where item.model = ?;";
        Customer customer = new Customer();
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {
            stmt.setString(1, nameIn);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                System.out.print(rs.getString("Model") + " ");
                System.out.print(rs.getInt("Score")+ " ");
                System.out.print(rs.getString("ScoreText")+ " ");
                System.out.print(rs.getString("Comment")+ " ");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFeedback(int rate, int model, int customer, String comment) throws IOException {
        String query = "insert into feedback (fbratingid, itemId, customerId, comment) values (?,?,?,?);";
        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));


        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, rate);
            stmt.setInt(2, model);
            stmt.setInt(3, customer);
            stmt.setString(4, comment);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

