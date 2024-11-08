package store.repository;

import store.dto.request.ProductReq;
import store.enumerate.FileValues;
import store.message.ErrorMessage;
import store.model.product.Product;
import store.model.product.Products;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.view.Output;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProductRepository {
    private Products products;
    private Promotions promotions;

    public ProductRepository(Products products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public void readProductsFile() {
        try {
            FileReader fileReader = new FileReader(FileValues.PRODUCTS.getPath(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] datas = line.split(",");
                saveProduct(datas);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            Output.printErrorMessage(ErrorMessage.FILE_NOT_FOUND);
            throw new IllegalArgumentException();
        } catch (IOException e) {
            Output.printErrorMessage(ErrorMessage.FILE_IO);
            throw new IllegalArgumentException(e);
        }
    }

    public void saveProduct(String[] datas) {
        String name = datas[0];
        int price = Integer.parseInt(datas[1]);
        int quantity = Integer.parseInt(datas[2]);
        String promotion = datas[3];

        ProductReq productReq = new ProductReq(name, price, quantity);
        Product product = new Product(productReq);

        products.putProduct(name, product, findPromotion(promotion));
    }

    public Promotion findPromotion(String name) {
        return promotions.findPromotion(name);
    }
}
