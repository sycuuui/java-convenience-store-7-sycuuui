package store.controller;

import store.enumerate.FileValues;
import store.model.Products;
import store.model.Promotions;
import store.service.ReadFile;
import store.service.StoreService;
import store.view.Input;

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

    public void play() {
        readFiles();

        Input input = new Input(products);
        String[] splitCommaInput = input.requestProducts();

        StoreService storeService = new StoreService(products,promotions);
        storeService.savePurchaseProducts(splitCommaInput);
    }
}
