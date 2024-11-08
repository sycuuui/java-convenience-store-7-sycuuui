package store.controller;

import store.handler.InputHandler;
import store.model.product.Products;
import store.model.promotion.Promotions;
import store.model.PurchaseProducts;
import store.service.MembershipService;
import store.service.PromotionService;
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

        InputHandler inputHandler = new InputHandler(input);

        StoreService storeService = new StoreService(purchaseProducts);
        storeService.savePurchaseProducts(splitCommaInput);

        PromotionService purchaseService = new PromotionService(products, purchaseProducts, inputHandler);
        purchaseService.applyPromotion();

        MembershipService membershipService = new MembershipService(inputHandler);
        membershipService.ask();

    }
}
