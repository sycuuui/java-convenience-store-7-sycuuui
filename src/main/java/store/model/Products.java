package store.model;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public boolean isExistProduct(String name) {
        return products.stream()
                .anyMatch(product -> product.equalName(name));
    }

    public boolean isNotOverProductsQuantity(String name, int quantity) {
        int totalQuantity = products.stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQunantity)
                .sum();

        return (quantity > totalQuantity);
    }
}
