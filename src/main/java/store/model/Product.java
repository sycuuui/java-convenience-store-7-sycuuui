package store.model;

import store.dto.request.ProductReq;

public class Product {
    private final String name;
    private final int price;
    private final int qunantity;
    private final String promotion;

    public Product(final ProductReq productReq) {
        this.name = productReq.name();
        this.price = productReq.price();
        this.qunantity = productReq.quantity();
        this.promotion = productReq.promotion();
    }
}
