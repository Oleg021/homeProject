package com.nix.vyrvykhvost.repository.jdbc;


import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.JDBCConfig;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PhoneRepositoryJDBC implements CrudeRepository<Phone> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static PhoneRepositoryJDBC instance;

    @Autowired
    public PhoneRepositoryJDBC() {
    }

    public static PhoneRepositoryJDBC  getInstance() {
        if (instance == null) {
            instance = new PhoneRepositoryJDBC ();
        }
        return instance;
    }


    @Override
    public void save(Phone phone) {
        String sql = "INSERT INTO db.phone (id, count, manufacturer, model, price, title) VALUES (?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, phone);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Phone> phones) {
        String sql = "INSERT INTO db.phone (id, count, manufacturer, model, price, title) VALUES (?, ?, ?, ?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Phone phone : phones) {
                setObjectFields(statement, phone);
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
    public List<Phone> findAll() {
        final List<Phone> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM db.phone");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Phone phone) {
        statement.setString(1, phone.getId());
        statement.setInt(2, phone.getCount());
        statement.setString(3, phone.getManufacturer().name());
        statement.setString(4, phone.getModel());
        statement.setDouble(5, phone.getPrice());
        statement.setString(6, phone.getTitle());
    }

    @Override
    public boolean update(Phone phone) {
        String update = "UPDATE db.phone SET count = ?, manufacturer = ?, model = ?, price = ?, title = ?  WHERE id = ?;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(update)) {
            setObjectFields(statement, phone);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM db.phone WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Phone> getAll() {
        final List<Phone> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM db.phone");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private Phone setFieldsToObject(final ResultSet resultSet) {
        int count = resultSet.getInt("count");
        Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"), Manufacturer.NONE);
        final String model = resultSet.getString("model");
        double price = resultSet.getDouble("price");
        String title = resultSet.getString("title");
        Phone phone = new Phone(title, count, price, model, manufacturer);
        phone.setId(resultSet.getString("id"));
        return phone;
    }

    @Override
    public Optional<Phone> findById(String id) {
        String sql = "SELECT * FROM db.phone WHERE id = ?";
        Optional<Phone> phone = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                phone = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return phone;
    }
}

