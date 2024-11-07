package store.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Products {
    private final List<Product> products;
    private final HashMap<Product, String> promotionProducts;

    public Products() {
        this.products = new ArrayList<>();
        this.promotionProducts = new HashMap<>();
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public void addPromotionProduct(final Product product, final String promotion) {
        promotionProducts.put(product, promotion);
    }

    public boolean isExistProduct(String name) {
        return products.stream()
                .anyMatch(product -> product.equalName(name));
    }

    public boolean isNotOverProductsQuantity(String name, int quantity) {
        int totalProductQuantity = products.stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQuantity)
                .sum();

        int totalPromotionProductQuantity = promotionProducts.keySet().stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQuantity)
                .sum();

        return (quantity > (totalProductQuantity + totalPromotionProductQuantity));
    }
}
