package store.dto.request;

public record ProductReq(
        String name,
        int price,
        int quantity,
        String promotion
) {
}
