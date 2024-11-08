package store.model.product;

import store.model.promotion.Promotion;

import java.util.HashMap;

public class Products {
    private final HashMap<String, Product> products;
    private final HashMap<String, PromotionProduct> promotionProducts;

    public Products() {
        this.products = new HashMap<>();
        this.promotionProducts = new HashMap<>();
    }

    private PromotionProduct findPromotionProduct(String productName) {
        return promotionProducts.get(productName);
    }

    private int getTotalProductQuantity(String productName) {
        return products.get(productName).getQuantity();
    }

    private int getTotalPromotionProductQuantity(String productName) {
        return findPromotionProduct(productName).getProductQuantity();
    }

    public void addProduct(String productName, Product product, Promotion promotion) {
        if (promotion != null) {
            promotionProducts.put(productName, new PromotionProduct(product, promotion));
        }
        products.put(productName, product);
    }

    public boolean isExistProduct(String productName) {
        return (products.get(productName) != null);
    }

    public boolean isPromotionPeriod(String productName) {
        return findPromotionProduct(productName).isPromotionPeriod();
    }

    /**
     * @param name             상품 이름
     * @param purchaseQuantity 구매할 양
     * @return 구매할 양이 상품 개수보다 많으면 false
     **/
    public boolean isNotOverProductsQuantity(String name, int purchaseQuantity) {
        int totalQuantity = getTotalProductQuantity(name)
                + getTotalPromotionProductQuantity(name);

        return purchaseQuantity <= totalQuantity;
    }

    public int getPresentQuantityByPromotionProduct(String productName, int purchaseQuantity) {
        return findPromotionProduct(productName).calculatePresentQuantity(purchaseQuantity);
    }

    public int getApplyPromotionQuantityByPromotionProduct(String productName, int presentQuantity) {
        return findPromotionProduct(productName).calculateApplyPromotionQuantity(presentQuantity);
    }

    public boolean getIsNeedQuestionAboutAdd(String productName, int purchaseQuantity) {
        return findPromotionProduct(productName).isNeedQuestionAboutAdd(purchaseQuantity);
    }

}
