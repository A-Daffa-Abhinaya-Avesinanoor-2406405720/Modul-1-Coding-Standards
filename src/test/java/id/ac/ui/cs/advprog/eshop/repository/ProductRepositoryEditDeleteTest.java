package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRepositoryEditDeleteTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void updateProduct_whenExists_updatesFields() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updated = new Product();
        updated.setProductId("p-1");
        updated.setProductName("Updated Name");
        updated.setProductQuantity(20);

        Product result = productRepository.update(updated);

        assertNotNull(result);
        Product saved = productRepository.findById("p-1");
        assertNotNull(saved);
        assertEquals("Updated Name", saved.getProductName());
        assertEquals(20, saved.getProductQuantity());
    }

    @Test
    void updateProduct_whenNotExists_returnsNull() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updated = new Product();
        updated.setProductId("p-2");
        updated.setProductName("Updated Name");
        updated.setProductQuantity(20);

        Product result = productRepository.update(updated);

        assertNull(result);
        Product saved = productRepository.findById("p-1");
        assertNotNull(saved);
        assertEquals("Original Name", saved.getProductName());
        assertEquals(10, saved.getProductQuantity());
    }

    @Test
    void deleteProduct_whenExists_returnsTrueAndRemoves() {
        Product product1 = new Product();
        product1.setProductId("p-1");
        product1.setProductName("First");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("p-2");
        product2.setProductName("Second");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        boolean result = productRepository.deleteById("p-1");

        assertTrue(result);
        assertNull(productRepository.findById("p-1"));
        assertNotNull(productRepository.findById("p-2"));
    }

    @Test
    void deleteProduct_whenNotExists_returnsFalse() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Original");
        product.setProductQuantity(10);
        productRepository.create(product);

        boolean result = productRepository.deleteById("p-2");

        assertFalse(result);
        assertNotNull(productRepository.findById("p-1"));
    }

    @Test
    void create_whenProductIdNull_assignsId() {
        Product product = new Product();
        product.setProductName("Auto Id");
        product.setProductQuantity(1);

        Product created = productRepository.create(product);

        assertNotNull(created.getProductId());
    }

    @Test
    void findById_whenMissing_returnsNull() {
        assertNull(productRepository.findById("missing-id"));
    }
}
