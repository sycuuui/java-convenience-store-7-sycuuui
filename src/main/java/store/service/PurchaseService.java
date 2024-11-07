package store.service;

import store.handler.InputHandler;
import store.model.Products;
import store.model.Promotion;
import store.model.Promotions;
import store.model.PurchaseProducts;
import store.util.CheckPromotion;
import store.view.Input;

import java.util.HashMap;

public class PurchaseService {
    private final Products products;
    private final PurchaseProducts purchaseProducts;
    private final CheckPromotion checkPromotion;
    private final InputHandler inputHandler;
    private HashMap<String, Integer> benefitQuantities;

    public PurchaseService(Products products, PurchaseProducts purchaseProducts, Input input) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
        this.checkPromotion = new CheckPromotion();
        this.inputHandler = new InputHandler(input);
        this.benefitQuantities = new HashMap<>();
    }

    /*
     * 구매할 상품들 각 프로모션 적용
     */
    public void applyPromotion() {
        purchaseProducts.getPurchaseDetails().forEach(this::processPurchaseProduct);
    }

    /*
     * 프로모션 적용 및 기간 확인
     */
    private void processPurchaseProduct(String productName, int purchaseQuantity) {
        if (products.getIsExistByPromotionProducts(productName)) {
            Promotion promotion = products.getPromotionByPromotionProducts(productName);
            if (promotion.isNowContainDuration()) {
                processPromotion(productName, purchaseQuantity, promotion);
            }
        }
    }

    private void processPromotion(String productName, int purchaseQuantity, Promotion promotion) {
        int countPromotion = checkPromotion.calculatePromotionCount(promotion, purchaseQuantity);

        if (purchaseQuantity <= products.getTotalByPromotionProducts(productName)) {
            if (purchaseQuantity % (countPromotion + 1) != (countPromotion - 1)) {
                boolean isAdd = inputHandler.askAboutBenefit(productName);
                if (isAdd) {
                    countPromotion++;
                }
            }
        }
        if (purchaseQuantity > products.getTotalByPromotionProducts(productName)) {
            int countNotPromotion = checkPromotion.calculateNonPromotionCount(promotion, purchaseQuantity, countPromotion);
            boolean isOk = inputHandler.askAboutShortage(productName, countNotPromotion);
            if (!isOk) {

            }
        }
        benefitQuantities.put(productName, countPromotion);
    }

}
