package id.ac.ui.cs.advprog.eshop.validation;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductQuantityValidator implements ProductValidator {
    @Override
    public boolean isValid(Product product) {
        return product.getProductQuantity() > 0;
    }
}
