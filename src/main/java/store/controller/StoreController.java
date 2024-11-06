package store.controller;

import store.enumerate.FileValues;
import store.model.Products;
import store.model.Promotions;
import store.service.ReadFile;

public class StoreController {
    private Products products;
    private Promotions promotions;


    public StoreController() {
        this.products = new Products();
        this.promotions = new Promotions();
    }

    public void readFiles() {
        ReadFile readFile = new ReadFile(products, promotions);
        readFile.readPromotionsFile();
        readFile.readProductsFile();
    }

    public void start() {
        readFiles();
    }
}
