package edu.school21.dao.impl;

import edu.school21.dao.ProductDAO;
import edu.school21.entity.Product;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;
    private final static String FIND_ALL =
            "SELECT * FROM PRODUCT";

    private final static String FIND_BY_ID =
            "SELECT * FROM PRODUCT\n" +
            "WHERE PRODUCT_ID = ?";

    private final static String UPDATE =
            "UPDATE PRODUCT \n" +
            "SET NAME = ?,\n" +
            "    PRICE = ?\n" +
            "WHERE PRODUCT_ID = ?";

    private final static String SAVE =
            "INSERT INTO PRODUCT (NAME, PRICE)\n" +
            "VALUES (?, ?)";

    private final static String DELETE =
            "DELETE FROM PRODUCT\n" +
            "WHERE PRODUCT_ID = ?";


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);

            while (resultSet.next()) {
                products.add(Product.of(
                        resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            Product product = null;
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = Product.of(
                        resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
            }

            return Optional.ofNullable(product);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
