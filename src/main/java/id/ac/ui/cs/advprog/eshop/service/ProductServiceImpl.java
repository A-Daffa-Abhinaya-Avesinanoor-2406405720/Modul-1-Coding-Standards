package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.validation.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final List<ProductValidator> validators;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, List<ProductValidator> validators) {
        this.productRepository = productRepository;
        this.validators = validators;
    }

    @Override
    public Product create(Product product) {
        if (product == null) {
            return null;
        }
        if (!isValid(product)) {
            return null;
        }
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
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
        if (!isValid(product)) {
            return null;
        }
        return productRepository.update(product);
    }

    @Override
    public boolean deleteById(String productId) {
        return productRepository.deleteById(productId);
    }

    private boolean isValid(Product product) {
        return validators.stream()
                .filter(Objects::nonNull)
                .allMatch(validator -> validator.isValid(product));
    }

}
