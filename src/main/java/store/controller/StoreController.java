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
import store.view.Output;

public class StoreController {
    private Products products;
    private Promotions promotions;
    private PurchaseProducts purchaseProducts;

    public StoreController() {
        this.products = new Products();
        this.promotions = new Promotions();
        this.purchaseProducts = new PurchaseProducts();
    }

    public void play() {
        processFile();

        Output output = new Output(products);
        output.printProducts();

        Input input = new Input(products);
        InputHandler inputHandler = new InputHandler(input);

        processPurchaseProducts(input);
        processBenefit(inputHandler);
        processStock();
    }

    public void processFile() {
        PromotionRepository promotionRepository = new PromotionRepository(promotions);
        promotionRepository.readPromotionsFile();

        ProductRepository productRepository = new ProductRepository(products, promotions);
        productRepository.readProductsFile();
    }

    public void processPurchaseProducts(Input input) {
        String[] splitCommaInput = input.requestProducts();
        PurchaseProductRepository purchaseProductRepository = new PurchaseProductRepository(purchaseProducts);

        purchaseProductRepository.savePurchaseProducts(splitCommaInput);
    }

    public void processBenefit(InputHandler inputHandler) {
        PromotionService purchaseService = new PromotionService(products, purchaseProducts, inputHandler);
        purchaseService.applyPromotion();

        MembershipService membershipService = new MembershipService(inputHandler);
        membershipService.ask();
    }

    public void processStock() {
        ProductService productService = new ProductService(products, purchaseProducts);
        productService.updateProductsStock();
    }

}
