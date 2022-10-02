package com.nix.vyrvykhvost.repository.jdbc;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.JDBCConfig;
import com.nix.vyrvykhvost.model.Invoice;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.model.ProductType;
import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.headphone.HeadphonesType;
import com.nix.vyrvykhvost.model.laptop.LaptopType;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.repository.InvoiceRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.Date;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;

@Singleton
public class InvoiceRepositoryJDBC implements InvoiceRepository {

    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static InvoiceRepositoryJDBC instance;

    @Autowired
    public InvoiceRepositoryJDBC() {
    }

    public static InvoiceRepositoryJDBC getInstance() {
        if (instance == null) {
            instance = new InvoiceRepositoryJDBC();
        }
        return instance;
    }


    @Override
    public void save(Invoice invoice) {
        String insert = "INSERT INTO db.invoice (id, sum, time) VALUES (?, ?, ?);";
        String phone = "UPDATE db.phone SET invoice_id = ? WHERE id = ?;";
        String headphones = "UPDATE db.headphones SET invoice_id = ? WHERE id = ?;";
        String laptop = "UPDATE db.laptop SET invoice_id = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(insert)) {
            CONNECTION.setAutoCommit(false);

            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setDouble(2, invoice.getSum());
            preparedStatement.setDate(3, Date.valueOf(invoice.getTime().toLocalDate()));
            preparedStatement.execute();
            List<Product> products = invoice.getProducts();
            products.forEach(product -> {
                if (product instanceof Phone) {
                    updateProduct(phone, invoice, product);
                }
                if (product instanceof Headphones) {
                    updateProduct(headphones, invoice, product);
                }
                if (product instanceof com.nix.vyrvykhvost.model.laptop.Laptop) {
                    updateProduct(laptop, invoice, product);
                }
            });

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }


    private void updateProduct(String query, Invoice invoice, Product product) {
        try (PreparedStatement statement = CONNECTION.prepareStatement(query)) {
            statement.setString(1, invoice.getId());
            statement.setString(2, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @SneakyThrows
    public Product setFieldsToObject(final ResultSet resultSet, ProductType productType) {
        return switch (productType) {
            case PHONE -> {
                final String model = resultSet.getString("model");
                int count = resultSet.getInt("count");
                double price = resultSet.getDouble("price");
                String title = resultSet.getString("title");
                Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"), Manufacturer.NONE);
                Phone phone = new Phone(title, count, price, model, manufacturer);
                phone.setId(resultSet.getString("id"));
                yield phone;
            }
            case HEADPHONES -> {
                int count = resultSet.getInt("count");
                double price = resultSet.getDouble("price");
                String title = resultSet.getString("title");
                String model = resultSet.getString("model");
                Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"), Manufacturer.NONE);
                HeadphonesType headphonesType = EnumUtils.getEnum(HeadphonesType.class, resultSet.getString("headphones_type"), HeadphonesType.NONE);
                Headphones headphone = new Headphones(title, count, price, model, manufacturer, headphonesType);
                headphone.setId(resultSet.getString("id"));
                yield headphone;
            }
            case LAPTOP -> {
                int count = resultSet.getInt("count");
                double price = resultSet.getDouble("price");
                String title = resultSet.getString("title");
                String model = resultSet.getString("model");
                Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"), Manufacturer.NONE);
                LaptopType laptopType = EnumUtils.getEnum(LaptopType.class, resultSet.getString("laptop_type"), LaptopType.NONE);
                com.nix.vyrvykhvost.model.laptop.Laptop laptop = new com.nix.vyrvykhvost.model.laptop.Laptop(title, count, price, model, manufacturer, laptopType);
                laptop.setId(resultSet.getString("id"));
                yield laptop;
            }
        };
    }


    @Override
    public Optional<Invoice> findById(String id) {
        String select = """
                SELECT db.invoice.*,
                                
                phone.id AS id,
                phone.title AS title,
                phone.count AS count,
                phone.price AS price,
                phone.manufacturer AS manufacturer,
                phone.model AS model,
                                
                headphones.id AS id,
                headphones.title AS title,
                headphones.price AS price,
                headphones.count AS count,
                headphones.model AS model,
                headphones.manufacturer AS manufacturer
                headphones.type AS headphones_type,
                                
                laptop.id AS id,
                laptop.title AS title,
                laptop.price AS price,
                laptop.count AS count,
                laptop.model AS model,
                laptop.manufacturer AS manufacturer,
                laptop.type AS laptop_type,
                
                                
                FROM db.invoice
                                
                LEFT JOIN db.phone ON phone.invoice_id = invoice.id
                LEFT JOIN db.headphones ON headphones.invoice_id = invoice.id
                LEFT JOIN db.laptop ON laptop.invoice_id = invoice.id
                                
                WHERE invoice.id = ? ORDER BY invoice.id;""";
        try (PreparedStatement statement = CONNECTION.prepareStatement(select)) {

            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Map<String, Invoice> id_invoice = new HashMap<>();
            while (resultSet.next()) {
                String id1 = resultSet.getString("id");
                if (!id_invoice.containsKey(id1)) {
                    Invoice invoice = new Invoice();
                    invoice.setId(id1);
                    invoice.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.of(0, 0)));
                    invoice.setSum(resultSet.getDouble("sum"));
                    invoice.setProducts(new ArrayList<>());
                    id_invoice.put(invoice.getId(), invoice);
                }
                List<Product> products = id_invoice.get(id1).getProducts();
                if (resultSet.getString("id") != null) {
                    Headphones headphone = (Headphones) setFieldsToObject(resultSet, ProductType.HEADPHONES);
                    if (!products.contains(headphone)) {
                        products.add(headphone);
                    }
                }
                if (resultSet.getString("id") != null) {
                    Phone phone = (Phone) setFieldsToObject(resultSet, ProductType.PHONE);
                    if (!products.contains(phone)) {
                        products.add(phone);
                    }
                }
                if (resultSet.getString("id") != null) {
                    com.nix.vyrvykhvost.model.laptop.Laptop laptop = (com.nix.vyrvykhvost.model.laptop.Laptop) setFieldsToObject(resultSet, ProductType.LAPTOP);
                    if (!products.contains(laptop)) {
                        products.add(laptop);
                    }
                }
            }
            return id_invoice.values().stream().findFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Invoice> findAllGreaterSumInvoices(double sum) {
        String select = """
                   SELECT db.invoice.*,
                                
                phone.id AS id,
                phone.title AS title,
                phone.count AS count,
                phone.price AS price,
                phone.manufacturer AS manufacturer,
                phone.model AS model,
                                
                headphones.id AS id,
                headphones.title AS title,
                headphones.price AS price,
                headphones.count AS count,
                headphones.model AS model,
                headphones.manufacturer AS manufacturer
                headphones.type AS headphones_type,
                                
                laptop.id AS id,
                laptop.title AS title,
                laptop.price AS price,
                laptop.count AS count,
                laptop.model AS model,
                laptop.manufacturer AS manufacturer,
                laptop.type AS laptop_type,
                                
                FROM db.invoice
                                
                LEFT JOIN db.phone ON phone.invoice_id = invoice.id
                LEFT JOIN db.headphones ON headphones.invoice_id = invoice.id
                LEFT JOIN db.laptop ON laptop.invoice_id = invoice.id
                WHERE sum > ? ORDER BY invoice.id;""";
        try (PreparedStatement statement = CONNECTION.prepareStatement(select)) {
            statement.setDouble(1, sum);
            ResultSet resultSet = statement.executeQuery();
            Map<String, Invoice> id_invoice = new HashMap<>();
            while (resultSet.next()) {
                String id1 = resultSet.getString("id");
                if (!id_invoice.containsKey(id1)) {
                    Invoice invoice = new Invoice();
                    invoice.setId(id1);
                    invoice.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.of(0, 0)));
                    invoice.setSum(resultSet.getDouble("sum"));
                    invoice.setProducts(new ArrayList<>());
                    id_invoice.put(invoice.getId(), invoice);
                }
                List<Product> products = id_invoice.get(id1).getProducts();
                if (resultSet.getString("id") != null) {
                    Headphones headphone = (Headphones) setFieldsToObject(resultSet, ProductType.HEADPHONES);
                    if (!products.contains(headphone)) {
                        products.add(headphone);
                    }
                }
                if (resultSet.getString("id") != null) {
                    Phone phone = (Phone) setFieldsToObject(resultSet, ProductType.PHONE);
                    if (!products.contains(phone)) {
                        products.add(phone);
                    }
                }
                if (resultSet.getString("id") != null) {
                    com.nix.vyrvykhvost.model.laptop.Laptop laptop = (com.nix.vyrvykhvost.model.laptop.Laptop) setFieldsToObject(resultSet, ProductType.LAPTOP);
                    if (!products.contains(laptop)) {
                        products.add(laptop);
                    }
                }
            }

            return id_invoice.values().stream().toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Map< Double, Integer> sortBySum() {
        Map< Double, Integer> count_sum = new HashMap<>();
        String sortBySum = "SELECT count(id) AS count, invoice.sum FROM db.invoice GROUP BY invoice.sum;";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sortBySum);
            while (resultSet.next()) {
                double sum = resultSet.getDouble("sum");
                int count = resultSet.getInt("count");
                count_sum.put( sum, count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count_sum;
    }
    public int getInvoiceCount() {
        String count = "SELECT count(id) AS count FROM db.invoice";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(count);
            if (resultSet.next()) {
                return resultSet.getInt("count");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Invoice invoice) {
        String update = "UPDATE db.invoice SET sum = ?, time = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(update)) {
            preparedStatement.setDouble(1, invoice.getSum());
            preparedStatement.setDate(2, Date.valueOf(invoice.getTime().toLocalDate()));
            preparedStatement.setString(3, invoice.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}