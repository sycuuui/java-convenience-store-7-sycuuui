package store.model.product;

import store.dto.response.ProductInfoRes;
import store.model.promotion.Promotion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Products {
    //일반 상품
    private final HashMap<String, Product> generalProducts;
    //프로모션 상품
    private final HashMap<String, PromotionProduct> promotionProducts;

    public Products() {
        this.generalProducts = new HashMap<>();
        this.promotionProducts = new HashMap<>();
    }

    private PromotionProduct findPromotionProduct(String productName) {
        return promotionProducts.get(productName);
    }

    private int getGeneralProductQuantity(String productName) {
        return generalProducts.get(productName).getQuantity();
    }

    private int getPromotionProductQuantity(String productName) {
        return findPromotionProduct(productName).getProductQuantity();
    }

    public void putProduct(String productName, Product product, Promotion promotion) {
        if (promotion != null) {
            promotionProducts.put(productName, new PromotionProduct(product, promotion));
        }
        generalProducts.put(productName, product);
    }

    public boolean isExistProducts(String productName) {
        return (generalProducts.containsKey(productName)) && (promotionProducts.containsKey(productName));
    }

    public boolean isExistPromotionProduct(String productName) {
        return promotionProducts.containsKey(productName);
    }

    public boolean isExistGeneralProduct(String productName) {
        return generalProducts.containsKey(productName);
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
        int totalQuantity = getGeneralProductQuantity(name)
                + getPromotionProductQuantity(name);

        return purchaseQuantity <= totalQuantity;
    }

    public boolean isOverPromotionProductQuantity(String productName, int purchaseQuantity) {
        return (getPromotionProductQuantity(productName) < purchaseQuantity);
    }

    public Set<String> getAllProductsNames() {
        Set<String> allProductNames = new HashSet<>(generalProducts.keySet());
        allProductNames.addAll(promotionProducts.keySet());

        return allProductNames;
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

    public int getPaymentToGeneralProduct(String productName, int purchaseQuantity) {
        return generalProducts.get(productName).payment(purchaseQuantity);
    }

    public int getPaymentToPromotionProduct(String productName, int purchaseQuantity) {
        return promotionProducts.get(productName).getPayment(purchaseQuantity);
    }

    public ProductInfoRes getInfoToGeneralProduct(String productName) {
        Product product = generalProducts.get(productName);
        if (product != null) {
            return product.getProductInfo(null);
        }
        return null;
    }

    public ProductInfoRes getInfoToPromotionProduct(String productName) {
        PromotionProduct promotionProduct = promotionProducts.get(productName);
        if (promotionProduct != null) {
            return promotionProduct.getPromotionProductInfo();
        }
        return null;
    }

    public void appliedPurchaseQuantityToPromotionProduct(String productName, int soldQuantity) {
        promotionProducts.get(productName).appliedSoldQuantity(soldQuantity);
    }

    public void appliedPurchaseQuantityToGeneralProduct(String productName, int soldQuantity) {
        generalProducts.get(productName).appliedSoldQuantity(soldQuantity);
    }

}
