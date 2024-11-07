package store.controller;

import store.model.Products;
import store.model.Promotions;
import store.model.PurchaseProducts;
import store.service.PurchaseService;
import store.service.ReadFile;
import store.service.StoreService;
import store.view.Input;

public class StoreController {
    private Products products;
    private Promotions promotions;
    private PurchaseProducts purchaseProducts;

    public StoreController() {
        this.products = new Products();
        this.promotions = new Promotions();
        this.purchaseProducts = new PurchaseProducts();
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

        StoreService storeService = new StoreService(purchaseProducts);
        storeService.savePurchaseProducts(splitCommaInput);

        PurchaseService purchaseService = new PurchaseService(products, purchaseProducts, input);
        purchaseService.applyPromotion();
    }
}
