package store.model.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import store.dto.request.PromotionReq;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    public Promotion(final PromotionReq promotionReq) {
        this.name = promotionReq.name();
        this.buy = promotionReq.buy();
        this.get = promotionReq.get();
        this.start_date = promotionReq.start_date();
        this.end_date = promotionReq.end_date();
    }

    public String getName() {
        return this.name;
    }

    public boolean isActive() {
        if (!start_date.isAfter(DateTimes.now().toLocalDate())) {
            return false;
        }
        if (!end_date.isBefore(DateTimes.now().toLocalDate())) {
            return false;
        }
        return true;
    }

    public int calculatorLimit() {
        return buy + get;
    }
}
