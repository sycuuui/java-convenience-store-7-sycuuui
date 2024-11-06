package store.model;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
    }

    public void addProduct(final Product product) {
        products.add(product);
    }
}
