package store.model;

import store.enumerate.PromotionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Products {
    private final List<Product> products;
    private final PromotionProducts promotionProducts;

    public Products() {
        this.products = new ArrayList<>();
        this.promotionProducts = new PromotionProducts();
    }

    public void addProduct(Product product, Promotion promotion) {
        if (promotion != null) {
            promotionProducts.addPromotionProduct(product, promotion);
        }
        products.add(product);
    }

    public boolean isExistProduct(String name) {
        return products.stream()
                .anyMatch(product -> product.equalName(name));
    }

    public int getTotalProductQuantity(String name) {
        return products.stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQuantity)
                .sum();
    }

    /**
     * @param name             상품 이름
     * @param purchaseQuantity 구매할 양
     * @return 구매할 양이 상품 개수보다 많으면 false
     **/
    public boolean isNotOverProductsQuantity(String name, int purchaseQuantity) {
        int totalQuantity = getTotalProductQuantity(name) + promotionProducts.getTotalPromotionProductQuantity(name);

        return purchaseQuantity <= totalQuantity;
    }

    public int getTotalByPromotionProducts(String name) {
        return promotionProducts.getTotalPromotionProductQuantity(name);
    }

    public Promotion getPromotionByPromotionProducts(String name) {
        return promotionProducts.getPromotionProductByName(name);
    }

    public boolean getIsExistByPromotionProducts(String name) {
        return promotionProducts.isExist(name);
    }

}
