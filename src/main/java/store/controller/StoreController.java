package store.controller;

import store.handler.InputHandler;
import store.model.product.Products;
import store.model.promotion.Promotions;
import store.model.PurchaseProducts;
import store.repository.ProductRepository;
import store.service.MembershipService;
import store.service.ProductService;
import store.service.PromotionService;
import store.repository.PromotionRepository;
import store.repository.PurchaseProductRepository;
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
        PromotionRepository promotionRepository = new PromotionRepository(promotions);
        promotionRepository.readPromotionsFile();

        ProductRepository productRepository = new ProductRepository(products, promotions);
        productRepository.readProductsFile();
    }

    public void play() {
        readFiles();

        Input input = new Input(products);
        String[] splitCommaInput = input.requestProducts();

        InputHandler inputHandler = new InputHandler(input);

        PurchaseProductRepository storeService = new PurchaseProductRepository(purchaseProducts);
        storeService.savePurchaseProducts(splitCommaInput);

        PromotionService purchaseService = new PromotionService(products, purchaseProducts, inputHandler);
        purchaseService.applyPromotion();

        MembershipService membershipService = new MembershipService(inputHandler);
        membershipService.ask();

        ProductService productService = new ProductService(products, purchaseProducts);
        productService.updateProductsStock();
    }
}
