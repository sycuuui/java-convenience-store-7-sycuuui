package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.dto.request.PromotionReq;
import store.enumerate.PromotionStatus;

import java.time.LocalDate;

public class Promotion {
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    public Promotion(final PromotionReq promotionReq) {
        this.buy = promotionReq.buy();
        this.get = promotionReq.get();
        this.start_date = promotionReq.start_date();
        this.end_date = promotionReq.end_date();
    }

    public boolean isNowContainDuration() {
        if (!start_date.isAfter(DateTimes.now().toLocalDate())) {
            return false;
        }
        if (!end_date.isBefore(DateTimes.now().toLocalDate())) {
            return false;
        }
        return true;
    }

    public PromotionStatus getPromotionStatus() {
        if (get == 1 && buy == 1) {
            return PromotionStatus.ONE_PLUS_ONE;
        }
        return PromotionStatus.TWO_PLUS_ONE;
    }
}
