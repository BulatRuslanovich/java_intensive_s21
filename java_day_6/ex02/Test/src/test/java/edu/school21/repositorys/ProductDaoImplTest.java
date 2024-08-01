package edu.school21.repositorys;

import edu.school21.dao.ProductDAO;
import edu.school21.dao.impl.ProductDAOImpl;
import edu.school21.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            Product.of(0L, "name1", 10000),
            Product.of(1L, "name2", 2344),
            Product.of(2L, "name3", 214),
            Product.of(3L, "name4", 544),
            Product.of(4L, "name5", 5344)
    );

    final Product EXPECTED_FIND_BY_ID_PRODUCT = Product.of(3L, "name4", 544);
    final Product EXPECTED_UPDATED_PRODUCT = Product.of(1L, "name2", 47);

    EmbeddedDatabase database;
    ProductDAO productDAO;

    @BeforeEach
    public void init() throws SQLException {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productDAO = new ProductDAOImpl(database.getConnection());
    }

    @Test
    public void findAllTest() {
        List<Product> all = productDAO.findAll();
        for (int i = 0; i < all.size(); i++) {
            assertEquals(EXPECTED_FIND_ALL_PRODUCTS.get(i), all.get(i));
        }
    }

    @Test
    public void findByIdTest() {
        Long id = 3L;
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productDAO.findById(id).get());
    }

    @Test
    public void checkFindByIdNull(){
        assertFalse(productDAO.findById(47L).isPresent());
    }

    @Test
    public void testUpdate() {
        Product expectedProduct = productDAO.findById(EXPECTED_UPDATED_PRODUCT.getProductId()).orElse(null);
        assertNotNull(expectedProduct);

        expectedProduct.setPrice(EXPECTED_UPDATED_PRODUCT.getPrice());
        productDAO.update(expectedProduct);
        Product result = productDAO.findById(expectedProduct.getProductId()).orElse(null);

        assertNotNull(result);
        assertEquals(result, EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    public void testSave(){
        int countBefore = productDAO.findAll().size();
        productDAO.save(Product.of(6L, "name6", 470));

        assertEquals(countBefore, productDAO.findAll().size() - 1);
    }

    @Test
    public void testDelete(){
        Integer countBefore = productDAO.findAll().size();
        productDAO.delete(6L);
        assertEquals(countBefore, productDAO.findAll().size());
    }

    @AfterEach
    public void shutdown() {
        database.shutdown();
    }
}
