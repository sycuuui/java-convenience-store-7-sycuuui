package store.model.product;

import store.dto.request.ProductReq;

public class Product {
    private final String name;
    private final int price;
    private final int qunantity;

    public Product(final ProductReq productReq) {
        this.name = productReq.name();
        this.price = productReq.price();
        this.qunantity = productReq.quantity();
    }

    public int getQuantity() {
        return this.qunantity;
    }

    public boolean equalName(String name) {
        return name.equals(this.name);
    }

    /**
     * @return ture - 수량이 구매수량보다 작음 false - 수량이 구매수량보다 작거나 같음
     */
    public boolean isLessProductQuantity(int purchaseQuantity) {
        return (qunantity < purchaseQuantity);
    }
}
