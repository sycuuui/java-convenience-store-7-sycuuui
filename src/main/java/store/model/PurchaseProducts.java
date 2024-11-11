package store.model;

import store.dto.response.QuantityRes;

import java.util.HashMap;


public class PurchaseProducts {
    //사용자가 초기에 입력한 구매 상품과 수량 데이터
    private final HashMap<String, Integer> initialPurchaseDetails;
    //사용자가 입력한 구매 상품 중 프로모션 상품에 대한 상품들만 있는 데이터
    private final HashMap<String, QuantityRes> purchasePromotionDetails;

    public PurchaseProducts() {
        this.initialPurchaseDetails = new HashMap<>();
        this.purchasePromotionDetails = new HashMap<>();
    }

    public void putInitialPurchaseDetails(String productName, Integer purchaseQuantity) {
        initialPurchaseDetails.put(productName, purchaseQuantity);
    }

    public void putPurchasePromotionDetail(String productName, QuantityRes quantityRes) {
        purchasePromotionDetails.put(productName, quantityRes);
    }

    public void addPurchaseQunatity(String productName) {
        int initQunatity = initialPurchaseDetails.get(productName);
        int quantity = initQunatity + 1;
        initialPurchaseDetails.put(productName, quantity);
    }

    /**
     * 사용자가 프로모션 미적용 상품에 대한 구매 의사가 없을 시 적용되는 메소드
     *
     * @param productName                미적용 상품 이름
     * @param UnappliedPromotionQuantity 미적용 상품 수량
     */
    public void minusUnappliedPromotionQuantity(String productName, int UnappliedPromotionQuantity) {
        int quantity = initialPurchaseDetails.get(productName);
        initialPurchaseDetails.put(productName, (quantity - UnappliedPromotionQuantity));
    }

    /**
     * 구매 리스트에 있는지 판단 메소드
     */
    public boolean hasPurchasePromotion(String productName) {
        return purchasePromotionDetails.containsKey(productName);
    }

    public int getAppliedPromotionQuantity(String productName) {
        return purchasePromotionDetails.get(productName).appliedPromotionQuantity();
    }

    public HashMap<String, Integer> getIntitalPurchaseDetails() {
        return initialPurchaseDetails;
    }

    public HashMap<String, QuantityRes> getPurchasePromotionDetails() {
        return purchasePromotionDetails;
    }
}
