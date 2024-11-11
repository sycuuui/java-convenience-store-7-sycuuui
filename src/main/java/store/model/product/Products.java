package store.model.product;

import store.dto.response.ProductInfoRes;
import store.model.promotion.Promotion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
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
        if(promotionProducts.containsKey(productName)){
            return promotionProducts.get(productName);
        }
        return null;
    }

    private int getGeneralProductQuantity(String productName) {
        if(generalProducts.containsKey(productName)){
            return generalProducts.get(productName).getQuantity();
        }
        return 0;
    }

    public int getPromotionProductQuantity(String productName) {
        if(findPromotionProduct(productName)!=null) {
            return findPromotionProduct(productName).getProductQuantity();
        }
        return 0;
    }

    public void putProduct(String productName, Product product, Promotion promotion) {
        if (promotion != null) {
            promotionProducts.put(productName, new PromotionProduct(product, promotion));
            return;
        }
        generalProducts.put(productName, product);
    }

    /**
     * 존재하는 상품인지 판별 메소드 (일반 상품과 프로모션 상품 모두 탐색)
     */
    public boolean isExistProducts(String productName) {
        return (generalProducts.containsKey(productName)) || (promotionProducts.containsKey(productName));
    }

    /**
     * 존재하는 상품인지 판별 메소드 (프로모션 상품에서만 탐색)
     */
    public boolean isExistPromotionProduct(String productName) {
        return promotionProducts.containsKey(productName);
    }

    /**
     * 프로모션 기간에 해당되는 프로모션 상품인지 판단 메소드
     */
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

    /**
     * 프로모션 상품의 수량이 구매 수량과 비교 메소드
     *
     * @param productName      상품 이름
     * @param purchaseQuantity 구매하려는 수량
     * @return true : 구매수량이 보유 수량보다 많을 경우
     */
    public boolean isOverPromotionProductQuantity(String productName, int purchaseQuantity) {
        return (getPromotionProductQuantity(productName) < purchaseQuantity);
    }

    /**
     * 프로모션 상품과 일반 상품들이 보유하고 있는 상품 이름들 리스트 생성 메소드
     *
     * @return 상품 이름 리스
     */
    public Set<String> getAllProductsNames() {
        Set<String> allProductNames = new HashSet<>(generalProducts.keySet());
        allProductNames.addAll(promotionProducts.keySet());

        return allProductNames;
    }

    public int getPresentQuantityByProduct(String productName) {
        return Objects.requireNonNull(findPromotionProduct(productName)).calculatePresentQuantityByProduct();
    }

    public int getPresentQuantityByPurchase(String productName, int purchaseQuantity) {
        return Objects.requireNonNull(findPromotionProduct(productName)).calculatePresentQuantityByPurchase(purchaseQuantity);
    }

    public int getAppliedPromotionQuantityByPromotionProduct(String productName, int presentQuantity) {
        return Objects.requireNonNull(findPromotionProduct(productName)).calculateAppliedPromotionQuantity(presentQuantity);
    }

    public boolean getIsNeedQuestionAboutAddByPromotionProduct(String productName, int purchaseQuantity) {
        return Objects.requireNonNull(findPromotionProduct(productName)).isNeedQuestionAboutAdd(purchaseQuantity);
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

    /**
     * 구매한 프로모션 상품 수량 보유 리스트에 적용 메소드
     */
    public void appliedPurchaseQuantityToPromotionProduct(String productName, int soldQuantity) {
        promotionProducts.get(productName).appliedSoldQuantity(soldQuantity);
    }

    /**
     * 구매한 상품 수량 보유 리스트에 적용 메소드
     */
    public void appliedPurchaseQuantityToGeneralProduct(String productName, int soldQuantity) {
        generalProducts.get(productName).appliedSoldQuantity(soldQuantity);
    }

}
