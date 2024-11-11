package store.service;

import store.dto.response.*;
import store.message.OutputMessage;
import store.model.PurchaseProducts;
import store.model.product.Products;
import store.view.Output;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiptService {
    private final Products products;
    private final PurchaseProducts purchaseProducts;
    private final MembershipService membershipService;
    private final Output output;

    public ReceiptService(Products products, PurchaseProducts purchaseProducts, MembershipService membershipService, Output output) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
        this.membershipService = membershipService;
        this.output = output;
    }


    public void processReceipt() {
        //구매하려는 상품 출력
        output.printOutPutMessage(OutputMessage.PURCHASE_SECTION_HEADER);
        output.printCategory();
        TotalPurchaseRes totalPurchaseRes = processTotalPurchase();
        //증정 분야 계산 및 출력
        output.printOutPutMessage(OutputMessage.PRESENT_SECTION_HEADER);
        PromotionAmountRes promotionAmountRes = processTotalDiscount();
        //가격 분야 출력
        output.printOutPutMessage(OutputMessage.PRICE_SECTION_HEADER);
        showPriceSection(totalPurchaseRes, promotionAmountRes);
    }

    private void showPriceSection(TotalPurchaseRes totalPurchaseRes, PromotionAmountRes promotionAmountRes) {
        int totalPurchaseAmount = totalPurchaseRes.totalPurchaseAmount();
        int totalPurchaseQuantity = totalPurchaseRes.totalPurchaseQuantity();

        int totalDiscountAmount = promotionAmountRes.totalDiscountAmount();
        int totalPromotionAmount = promotionAmountRes.totalPromotionAmount();

        int membershipDiscountAmount = calculateMembershipDiscountAmount(totalPurchaseAmount, totalPromotionAmount);
        int finalAmount = totalPurchaseAmount - totalDiscountAmount - membershipDiscountAmount;

        PriceResultRes priceResultRes = new PriceResultRes(totalPurchaseQuantity, totalPurchaseAmount, totalDiscountAmount, membershipDiscountAmount, finalAmount);

        output.printPriceResultSection(priceResultRes);
    }

    /**
     * 총 구매액 계산 메소드 (초기 구매 리스트 사용)
     *
     * @return 총 구매액
     */
    public TotalPurchaseRes processTotalPurchase() {
        AtomicInteger totalPurchaseAmount = new AtomicInteger(0);
        AtomicInteger totalPurchaseQuantity = new AtomicInteger(0);

        HashMap<String, Integer> intitalPurchaseDetails = purchaseProducts.getIntitalPurchaseDetails();
        intitalPurchaseDetails.forEach((productName, quantity) -> {
            int payment = products.getPaymentToGeneralProduct(productName, quantity);
            totalPurchaseAmount.addAndGet(payment);
            totalPurchaseQuantity.addAndGet(quantity);

            output.printPurchaseProduct(productName, quantity, payment);
        });

        return new TotalPurchaseRes(totalPurchaseAmount.get(), totalPurchaseQuantity.get());
    }

    /**
     * 각 상품별 행사 할인 금액과 프로모션 적용된 상품들에 대한 금액 계산 및 출력 메소드 (프로모션 구매 리스트 사용)
     *
     * @return 총 할인액과 프로모션 적용된 상품들에 대한 금액
     */
    public PromotionAmountRes processTotalDiscount() {
        AtomicInteger totalDiscountAmount = new AtomicInteger(0);
        AtomicInteger totalPromotionAmount = new AtomicInteger(0);

        HashMap<String, QuantityRes> purchasePromotionDetails = purchaseProducts.getPurchasePromotionDetails();
        purchasePromotionDetails.forEach((productName, quantityRes) -> {
            output.printPresentProduct(productName, quantityRes.presentQuantity());

            int discountAmount = products.getPaymentToPromotionProduct(productName, quantityRes.presentQuantity());
            int promotionAmount = products.getPaymentToPromotionProduct(productName, quantityRes.appliedPromotionQuantity());

            totalDiscountAmount.addAndGet(discountAmount);
            totalPromotionAmount.addAndGet(promotionAmount);
        });

        return new PromotionAmountRes(totalDiscountAmount.get(), totalPromotionAmount.get());
    }

    private int calculateMembershipDiscountAmount(int totalPurchaseAmount, int totalPromotionAmount) {
        return membershipService.processMembership(totalPurchaseAmount, totalPromotionAmount);
    }
}
