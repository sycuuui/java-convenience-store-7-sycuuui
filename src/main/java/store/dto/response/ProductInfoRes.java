package store.dto.response;

public record ProductInfoRes(
        String name,
        int price,
        int stock,
        String promotionName
) {
}
