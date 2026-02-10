package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void createProductPage_returnsCreateView() {
        Model model = new ExtendedModelMap();

        String view = productController.createProductPage(model);

        assertEquals("createProduct", view);
        assertTrue(model.containsAttribute("product"));
    }

    @Test
    void createProductPost_whenValid_redirectsToList() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Product Name");
        product.setProductQuantity(3);

        when(productService.create(product)).thenReturn(product);
        Model model = new ExtendedModelMap();

        String view = productController.createProductPost(product, model);

        assertEquals("redirect:/product/list", view);
    }

    @Test
    void createProductPost_whenInvalid_returnsCreateView() {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(0);

        when(productService.create(product)).thenReturn(null);
        Model model = new ExtendedModelMap();

        String view = productController.createProductPost(product, model);

        assertEquals("createProduct", view);
        assertTrue(model.containsAttribute("errorMessage"));
    }

    @Test
    void productListPage_returnsListView() {
        when(productService.findAll()).thenReturn(Collections.emptyList());
        Model model = new ExtendedModelMap();

        String view = productController.productListPage(model);

        assertEquals("productList", view);
        assertTrue(model.containsAttribute("products"));
    }

    @Test
    void editProductPage_whenFound_returnsEditView() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Product Name");
        product.setProductQuantity(3);

        when(productService.findById("p-1")).thenReturn(product);
        Model model = new ExtendedModelMap();

        String view = productController.editProductPage("p-1", model);

        assertEquals("editProduct", view);
        assertTrue(model.containsAttribute("product"));
    }

    @Test
    void editProductPage_whenNotFound_redirectsToList() {
        when(productService.findById("p-1")).thenReturn(null);
        Model model = new ExtendedModelMap();

        String view = productController.editProductPage("p-1", model);

        assertEquals("redirect:/product/list", view);
    }

    @Test
    void editProductPost_whenValid_redirectsToList() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("Product Name");
        product.setProductQuantity(3);

        when(productService.update(product)).thenReturn(product);
        Model model = new ExtendedModelMap();

        String view = productController.editProductPost(product, model);

        assertEquals("redirect:/product/list", view);
    }

    @Test
    void editProductPost_whenInvalid_returnsEditView() {
        Product product = new Product();
        product.setProductId("p-1");
        product.setProductName("");
        product.setProductQuantity(0);

        when(productService.update(product)).thenReturn(null);
        Model model = new ExtendedModelMap();

        String view = productController.editProductPost(product, model);

        assertEquals("editProduct", view);
        assertTrue(model.containsAttribute("errorMessage"));
    }

    @Test
    void deleteProduct_whenServiceReturnsTrue_redirectsToList() {
        when(productService.deleteById("p-1")).thenReturn(true);

        String view = productController.deleteProduct("p-1");

        assertEquals("redirect:/product/list", view);
        verify(productService).deleteById("p-1");
    }

    @Test
    void deleteProduct_whenServiceReturnsFalse_stillRedirectsToList() {
        when(productService.deleteById("p-1")).thenReturn(false);

        String view = productController.deleteProduct("p-1");

        assertEquals("redirect:/product/list", view);
        verify(productService).deleteById("p-1");
    }
}
