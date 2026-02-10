package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        if (product == null) {
            return null;
        }
        String productId = normalizeProductId(product.getProductId());
        if (!isValidProductId(productId)) {
            return null;
        }
        if (!isValidQuantity(product.getProductQuantity())) {
            return null;
        }
        if (productRepository.findById(productId) != null) {
            return null;
        }
        product.setProductId(productId);
        productRepository.create(product);
        return product;
    }
    @Override
    public List<Product> findAll () {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product update(Product product) {
        if (product == null) {
            return null;
        }
        String productId = normalizeProductId(product.getProductId());
        if (!isValidProductId(productId)) {
            return null;
        }
        if (!isValidQuantity(product.getProductQuantity())) {
            return null;
        }
        product.setProductId(productId);
        return productRepository.update(product);
    }

    @Override
    public boolean deleteById(String productId) {
        return productRepository.deleteById(productId);
    }

    private String normalizeProductId(String productId) {
        if (productId == null) {
            return null;
        }
        return productId.trim();
    }

    private boolean isValidProductId(String productId) {
        return productId != null && productId.matches("\\d+");
    }

    private boolean isValidQuantity(int productQuantity) {
        return productQuantity > 0 ;
    }

}
