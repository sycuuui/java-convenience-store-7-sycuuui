package store.model;

import store.enumerate.PromotionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Products {
    private final List<Product> products;
    private final HashMap<Product, Promotion> promotionProducts;

    public Products() {
        this.products = new ArrayList<>();
        this.promotionProducts = new HashMap<>();
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public void addPromotionProduct(final Product product, final Promotion promotion) {
        promotionProducts.put(product, promotion);
    }

    public boolean isExistProduct(String name) {
        return products.stream()
                .anyMatch(product -> product.equalName(name));
    }

    /**
     * @param name             상품 이름
     * @param purchaseQuantity 구매할 양
     * @return 구매할 양이 상품 개수보다 많으면 false
     **/
    public boolean isNotOverProductsQuantity(String name, int purchaseQuantity) {
        int totalProductQuantity = products.stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQuantity)
                .sum();

        int totalPromotionProductQuantity = promotionProducts.keySet().stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQuantity)
                .sum();

        return (purchaseQuantity >= (totalProductQuantity + totalPromotionProductQuantity));
    }

    public Promotion getPromotionProductByName(String name) {
        return (Promotion) promotionProducts.entrySet().stream()
                .filter(entry -> entry.getKey().equalName(name))
                .map(Map.Entry::getValue);
    }
}
