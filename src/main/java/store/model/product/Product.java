package store.model.product;

import store.dto.request.ProductReq;
import store.dto.response.ProductInfo;

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

    public int payment(int purchaseQuantity) {
        return price * purchaseQuantity;
    }

    public void appliedSoldQuantity(int soldQuantity) {
        this.qunantity -= soldQuantity;
    }

    public ProductInfo getProductInfo(String promotionName) {
        return new ProductInfo(this.name, this.price, this.qunantity, promotionName);
    }
}
