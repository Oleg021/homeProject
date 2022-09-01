package com.nix.vyrvykhvost.repository.jdbc;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.JDBCConfig;
import com.nix.vyrvykhvost.model.Manufacturer;
import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.headphone.HeadphonesType;
import com.nix.vyrvykhvost.model.laptop.LaptopType;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class HeadphonesRepositoryJDBC implements CrudeRepository<Headphones> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    private static HeadphonesRepositoryJDBC instance;

    @Autowired
    public HeadphonesRepositoryJDBC() {
    }

    public static HeadphonesRepositoryJDBC  getInstance() {
        if (instance == null) {
            instance = new HeadphonesRepositoryJDBC ();
        }
        return instance;
    }


    @Override
    public void save(Headphones headphone) {
        String sql = "INSERT INTO db.headphones (id, count, headphones_type, manufacturer, model, price, title) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, headphone);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Headphones> headphones) {
        String sql = "INSERT INTO db.headphones (id, count, headphones_type, manufacturer, model, price, title) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Headphones headphone : headphones) {
                setObjectFields(statement, headphone);
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
    public List<Headphones> findAll() {
        final List<Headphones> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM db.headphones");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Headphones headphone) {
        statement.setString(1, headphone.getId());
        statement.setInt(2, headphone.getCount());
        statement.setString(3,headphone.getHeadphonesType().name());
        statement.setString(4, headphone.getManufacturer().name());
        statement.setString(5, headphone.getModel());
        statement.setDouble(6, headphone.getPrice());
        statement.setString(7, headphone.getTitle());
    }

    @Override
    public boolean update(Headphones headphone) {
        String update = "UPDATE db.headphones SET count = ?,headphones_type = ?, manufacturer = ?, model = ?, price = ?, title = ?  WHERE id = ?;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(update)) {
            setObjectFields(statement, headphone);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM db.headphones WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Headphones> getAll() {
        final List<Headphones> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM db.headphones");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SneakyThrows
    private Headphones setFieldsToObject(final ResultSet resultSet) {
        int count = resultSet.getInt("count");
        Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"), Manufacturer.NONE);
        final String model = resultSet.getString("model");
        double price = resultSet.getDouble("price");
        String title = resultSet.getString("title");
        HeadphonesType headphonesType = EnumUtils.getEnum(HeadphonesType.class, resultSet.getString("headphones_type"), HeadphonesType.NONE);
        Headphones headphone = new Headphones(title, count, price, model, manufacturer, headphonesType);
        headphone.setId(resultSet.getString("id"));
        return headphone;
    }

    @Override
    public Optional<Headphones> findById(String id) {
        String sql = "SELECT * FROM db.headphones WHERE id = ?";
        Optional<Headphones> headphone = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                headphone = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return headphone;
    }
}

