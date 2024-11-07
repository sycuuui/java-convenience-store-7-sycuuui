package store.util;

import store.enumerate.PromotionStatus;
import store.model.Promotion;

public class CheckPromotion {
    public int calculatePromotionCount(Promotion promotion, int purchaseQuantity) {
        return purchaseQuantity / (getPromotionGet(promotion) + 1);
    }

    public int calculateNonPromotionCount(Promotion promotion, int purchaseQuantity, int countPromotion) {
        return purchaseQuantity - (countPromotion * (getPromotionGet(promotion) + 1));
    }

    public int getPromotionGet(Promotion promotion) {
        int get = 0;
        PromotionStatus promotionStatus = promotion.getPromotionStatus();

        if (promotionStatus.equals(PromotionStatus.ONE_PLUS_ONE)) {
            get = 1;
        }
        if (promotionStatus.equals(PromotionStatus.TWO_PLUS_ONE)) {
            get = 2;
        }

        return get;
    }
}
