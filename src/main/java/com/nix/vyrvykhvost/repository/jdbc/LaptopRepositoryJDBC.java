package com.nix.vyrvykhvost.repository.jdbc;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.JDBCConfig;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.laptop.Laptop;
import com.nix.vyrvykhvost.model.laptop.LaptopType;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class LaptopRepositoryJDBC implements CrudeRepository<Laptop> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static LaptopRepositoryJDBC instance;

    @Autowired
    public LaptopRepositoryJDBC() {
    }

    public static LaptopRepositoryJDBC  getInstance() {
        if (instance == null) {
            instance = new LaptopRepositoryJDBC ();
        }
        return instance;
    }


    @Override
    public void save(Laptop laptop) {
        String sql = "INSERT INTO laptop (id, count, laptop_type, manufacturer, model, price, title) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, laptop);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Laptop> laptops) {
        String sql = "INSERT INTO laptop (id, count, laptop_type, manufacturer, model, price, title) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Laptop laptop : laptops) {
                setObjectFields(statement, laptop);
                statement.addBatch();
            }
            statement.executeBatch();
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Laptop> findAll() {
        final List<Laptop> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM laptop");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Laptop laptop) {
        statement.setString(1, laptop.getId());
        statement.setInt(2, laptop.getCount());
        statement.setString(3,laptop.getLaptopType().name());
        statement.setString(4, laptop.getManufacturer().name());
        statement.setString(5, laptop.getModel());
        statement.setDouble(6, laptop.getPrice());
        statement.setString(7, laptop.getTitle());
    }

    @Override
    public boolean update(Laptop laptop) {
        String update = "UPDATE laptop SET count = ?,laptop_type = ?, manufacturer = ?, model = ?, price = ?, title = ?  WHERE id = ?;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(update)) {
            setObjectFields(statement, laptop);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM laptop WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Laptop> getAll() {
        final List<Laptop> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM laptop");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private Laptop setFieldsToObject(final ResultSet resultSet) {
        int count = resultSet.getInt("count");
        Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"), Manufacturer.NONE);
        final String model = resultSet.getString("model");
        double price = resultSet.getDouble("price");
        String title = resultSet.getString("title");
        LaptopType laptopType = EnumUtils.getEnum(LaptopType.class, resultSet.getString("laptop_type"), LaptopType.NONE);
        Laptop laptop = new Laptop(title, count, price, model, manufacturer, laptopType);
        laptop.setId(resultSet.getString("id"));
        return laptop;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        String sql = "SELECT * FROM laptop WHERE id = ?";
        Optional<Laptop> laptop = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                laptop = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return laptop;
    }
}
