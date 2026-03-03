package id.ac.ui.cs.advprog.eshop.validation;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductNameValidator implements ProductValidator {
    @Override
    public boolean isValid(Product product) {
        String productName = product.getProductName();
        return productName != null && !productName.trim().isEmpty();
    }
}
