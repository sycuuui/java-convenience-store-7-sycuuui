package store.service;

import store.dto.response.PromotionAmountRes;
import store.dto.response.QuantityRes;
import store.model.PurchaseProducts;
import store.model.product.Products;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiptService {
    private Products products;
    private PurchaseProducts purchaseProducts;
    private MembershipService membershipService;

    public ReceiptService(Products products, PurchaseProducts purchaseProducts, MembershipService membershipService) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
        this.membershipService = membershipService;
    }

    /**
     * 총 구매액 계산 메소드 (초기 구매 리스트 사용)
     *
     * @return 총 구매액
     */
    public int claculateTotalPurchaseAmount() {
        AtomicInteger totalPurchaseAmount = new AtomicInteger(0);

        HashMap<String, Integer> intitalPurchaseDetails = purchaseProducts.getIntitalPurchaseDetails();
        intitalPurchaseDetails.forEach((productName, quantity) -> {
            int payment = products.getPaymentToGeneralProduct(productName, quantity);
            totalPurchaseAmount.addAndGet(payment);
        });

        return totalPurchaseAmount.get();
    }

    /**
     * 행사 할인 금액과 프로모션 적용된 상품들에 대한 금액 계산 메소드 (프로모션 구매 리스트 사용)
     *
     * @return 총 할인액과 프로모션 적용된 상품들에 대한 금액
     */
    public PromotionAmountRes calculateTotalDiscountAmount() {
        AtomicInteger totalDiscountAmount = new AtomicInteger(0);
        AtomicInteger totalPromotionAmount = new AtomicInteger(0);

        HashMap<String, QuantityRes> purchasePromotionDetails = purchaseProducts.getPurchasePromotionDetails();
        purchasePromotionDetails.forEach((productName, quantityRes) -> {
            int discountAmount = products.getPaymentToPromotionProduct(productName, quantityRes.presentQuantity());
            int promotionAmount = products.getPaymentToPromotionProduct(productName, quantityRes.appliedPromotionQuantity());

            totalDiscountAmount.addAndGet(discountAmount);
            totalDiscountAmount.addAndGet(promotionAmount);
        });

        return new PromotionAmountRes(totalDiscountAmount.get(), totalPromotionAmount.get());
    }

    public int calculateMembershipDiscountAmount(int totalPurchaseAmount, int totalPromotionAmount) {
        return membershipService.processMembership(totalPromotionAmount, totalPromotionAmount);
    }
}
