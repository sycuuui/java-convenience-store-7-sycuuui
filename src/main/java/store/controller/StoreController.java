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
import store.service.ReceiptService;
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
        processNoticeInitial(output);

        Input input = new Input(products);
        InputHandler inputHandler = new InputHandler(input);

        processPurchaseProducts(input);

        MembershipService membershipService = new MembershipService(inputHandler);
        processPresent(inputHandler, membershipService);
        processStock();
        processResult(output, membershipService);
    }

    public void processNoticeInitial(Output output) {
        output.printHello();
        output.printProducts();
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

    public void processPresent(InputHandler inputHandler, MembershipService membershipService) {
        PromotionService purchaseService = new PromotionService(products, purchaseProducts, inputHandler);
        purchaseService.applyPromotion();

        membershipService.ask();
    }

    public void processStock() {
        ProductService productService = new ProductService(products, purchaseProducts);
        productService.updateProductsStock();
    }

    public void processResult(Output output, MembershipService membershipService) {
        ReceiptService receiptService = new ReceiptService(products, purchaseProducts, membershipService, output);
        receiptService.processReceipt();
    }

}
