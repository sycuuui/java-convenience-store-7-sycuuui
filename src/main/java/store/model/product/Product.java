package store.model.product;

import store.dto.request.ProductReq;

public class Product {
    private final String name;
    private final int price;
    private int qunantity;

    public Product(final ProductReq productReq) {
        this.name = productReq.name();
        this.price = productReq.price();
        this.qunantity = productReq.quantity();
    }

    public int getQuantity() {
        return this.qunantity;
    }

    public void appliedSoldQuantity(int soldQuantity) {
        this.qunantity -= soldQuantity;
    }
}
