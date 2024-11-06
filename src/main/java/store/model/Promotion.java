package store.model;

import store.dto.request.PromotionReq;

import java.time.LocalDate;

public class Promotion {
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    public Promotion(PromotionReq promotionReq) {
        this.buy = promotionReq.buy();
        this.get = promotionReq.get();
        this.start_date = promotionReq.start_date();
        this.end_date = promotionReq.end_date();
    }
}
