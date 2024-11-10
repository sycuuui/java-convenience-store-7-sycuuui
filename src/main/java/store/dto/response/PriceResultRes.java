package store.dto.response;

public record PriceResultRes(
        int totalQuantity,
        int totalPurchaseAmount,
        int totalDiscountAmount,
        int membershipDiscountAmount,
        int finalAmount
) {
}
