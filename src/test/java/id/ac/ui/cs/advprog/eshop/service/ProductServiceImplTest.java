package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void create_whenProductNull_returnsNull() {
        assertNull(productService.create(null));
        verifyNoInteractions(productRepository);
    }

    @Test
    void create_whenNameBlank_returnsNull() {
        Product product = new Product();
        product.setProductName("  ");
        product.setProductQuantity(1);

        assertNull(productService.create(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void create_whenNameNull_returnsNull() {
        Product product = new Product();
        product.setProductName(null);
        product.setProductQuantity(1);

        assertNull(productService.create(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void create_whenNameEmpty_returnsNull() {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(1);

        assertNull(productService.create(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void create_whenQuantityInvalid_returnsNull() {
        Product product = new Product();
        product.setProductName("Valid Name");
        product.setProductQuantity(0);

        assertNull(productService.create(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void create_whenValid_callsRepository() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Valid Name");
        product.setProductQuantity(5);

        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertSame(product, result);
        verify(productRepository).create(product);
    }

    @Test
    void create_whenNameHasSpaces_callsRepository() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("  Valid Name  ");
        product.setProductQuantity(5);

        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertSame(product, result);
        verify(productRepository).create(product);
    }

    @Test
    void findAll_returnsAllProducts() {
        Product product1 = new Product();
        product1.setProductId("p-1");
        Product product2 = new Product();
        product2.setProductId("p-2");
        List<Product> products = Arrays.asList(product1, product2);
        Iterator<Product> iterator = products.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("p-1", result.get(0).getProductId());
        assertEquals("p-2", result.get(1).getProductId());
    }

    @Test
    void findById_delegatesToRepository() {
        Product product = new Product();
        product.setProductId("p-1");

        when(productRepository.findById("p-1")).thenReturn(product);

        Product result = productService.findById("p-1");

        assertSame(product, result);
    }

    @Test
    void update_whenProductNull_returnsNull() {
        assertNull(productService.update(null));
        verifyNoInteractions(productRepository);
    }

    @Test
    void update_whenNameBlank_returnsNull() {
        Product product = new Product();
        product.setProductName(" ");
        product.setProductQuantity(2);

        assertNull(productService.update(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void update_whenNameNull_returnsNull() {
        Product product = new Product();
        product.setProductName(null);
        product.setProductQuantity(2);

        assertNull(productService.update(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void update_whenNameEmpty_returnsNull() {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(2);

        assertNull(productService.update(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void update_whenQuantityInvalid_returnsNull() {
        Product product = new Product();
        product.setProductName("Valid Name");
        product.setProductQuantity(-1);

        assertNull(productService.update(product));
        verifyNoInteractions(productRepository);
    }

    @Test
    void update_whenRepositoryReturnsNull_returnsNull() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Valid Name");
        product.setProductQuantity(3);

        when(productRepository.update(product)).thenReturn(null);

        assertNull(productService.update(product));
        verify(productRepository).update(product);
    }

    @Test
    void update_whenValid_returnsUpdatedProduct() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Valid Name");
        product.setProductQuantity(3);

        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertSame(product, result);
        verify(productRepository).update(product);
    }

    @Test
    void deleteById_delegatesToRepository() {
        when(productRepository.deleteById("p-1")).thenReturn(true);

        boolean result = productService.deleteById("p-1");

        assertTrue(result);
        verify(productRepository).deleteById("p-1");
    }
}
