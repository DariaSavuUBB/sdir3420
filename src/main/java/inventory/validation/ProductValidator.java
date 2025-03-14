package inventory.validation;

import inventory.model.Part;
import java.util.List;

public class ProductValidator {

    private ProductValidator() {
        throw new IllegalStateException("Utility class");
    }
    
    public static String validateProduct(String name, double price, int inStock, int min, int max, List<Part> parts) {
        StringBuilder errorMessage = new StringBuilder();

        if (name == null || name.trim().isEmpty()) {
            errorMessage.append("Product name cannot be empty.\n");
        }
        if (price <= 0) {
            errorMessage.append("Price must be greater than zero.\n");
        }
        if (inStock < 0) {
            errorMessage.append("Inventory must be a non-negative value.\n");
        }
        if (min > max) {
            errorMessage.append("Min value cannot be greater than Max value.\n");
        }
        if (inStock < min || inStock > max) {
            errorMessage.append("Inventory must be between Min and Max values.\n");
        }
        if (parts == null || parts.isEmpty()) {
            errorMessage.append("A product must have at least one associated part.\n");
        }

        return errorMessage.toString();
    }
}
