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

    private int getProductQuantity(String productName) {
        return products.get(productName).getQuantity();
    }

    private int getPromotionProductQuantity(String productName) {
        return findPromotionProduct(productName).getProductQuantity();
    }

    public void putProduct(String productName, Product product, Promotion promotion) {
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
        int totalQuantity = getProductQuantity(name)
                + getPromotionProductQuantity(name);

        return purchaseQuantity <= totalQuantity;
    }

    public int getPresentQuantityByPromotionProduct(String productName, int purchaseQuantity) {
        return findPromotionProduct(productName).calculatePresentQuantity(purchaseQuantity);
    }

    public int getAppliedPromotionQuantityByPromotionProduct(String productName, int presentQuantity) {
        return findPromotionProduct(productName).calculateAppliedPromotionQuantity(presentQuantity);
    }

    public boolean getIsNeedQuestionAboutAddByPromotionProduct(String productName, int purchaseQuantity) {
        return findPromotionProduct(productName).isNeedQuestionAboutAdd(purchaseQuantity);
    }

    public boolean isOverPromotionProductQuantity(String productName, int purchaseQuantity) {
        return (getPromotionProductQuantity(productName) < purchaseQuantity);
    }

    public void appliedPurchaseQuantityToPromotionProduct(String productName, int soldQuantity) {
        promotionProducts.get(productName).appliedSoldQuantity(soldQuantity);
    }

    public void appliedPurchaseQuantityToProduct(String productName, int soldQuantity) {
        products.get(productName).appliedSoldQuantity(soldQuantity);
    }

}
