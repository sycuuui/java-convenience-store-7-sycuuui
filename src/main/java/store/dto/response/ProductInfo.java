package store.dto.response;

public record ProductInfo(
        String name,
        int price,
        int stock,
        String promotionName
) {
}
