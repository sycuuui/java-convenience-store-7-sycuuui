package store.dto.request;

import java.time.LocalDate;

public record PromotionReq(
        int buy,
        int get,
        LocalDate start_date,
        LocalDate end_date
) {
}
