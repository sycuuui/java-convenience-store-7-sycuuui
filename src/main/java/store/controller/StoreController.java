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
    private final Input input;
    private final Output output;
    private final Products products;
    private final Promotions promotions;

    private InputHandler inputHandler;
    private PurchaseProducts purchaseProducts;
    private MembershipService membershipService;

    public StoreController() {
        this.products = new Products();
        this.promotions = new Promotions();
        this.input = new Input();
        this.output = new Output();
    }

    public void play() {
        processFile();

        boolean isContinue;
        do {
            processNoticeInitial();
            inputHandler = new InputHandler(input);
            purchaseProducts = new PurchaseProducts();
            processPurchaseProducts();
            membershipService = new MembershipService();
            processPresent();
            processStock();
            processResult();

            isContinue = inputHandler.askAboutContinue();
        }while (isContinue);
    }

    /**
     * 인삿말과 보유 상품들 출력 메소드
     */
    public void processNoticeInitial() {
        output.printHello();
        output.printProducts(products);
    }

    /**
     * 데이터 파일 관련 행동하는 메소드
     */
    public void processFile() {
        PromotionRepository promotionRepository = new PromotionRepository(promotions);
        promotionRepository.readPromotionsFile();

        ProductRepository productRepository = new ProductRepository(products, promotions);
        productRepository.readProductsFile();
    }

    /**
     * 구매하려는 물품 관련 행동하는 메소드
     */
    public void processPurchaseProducts() {
        String[] splitCommaInput = input.requestProducts(products);
        PurchaseProductRepository purchaseProductRepository = new PurchaseProductRepository(purchaseProducts);

        purchaseProductRepository.savePurchaseProducts(splitCommaInput);
    }

    /**
     * 구매한 물품들 중 프로모션 상품들 관련 행동 메소드
     */
    public void processPresent() {
        PromotionService purchaseService = new PromotionService(products, purchaseProducts, inputHandler);
        purchaseService.applyPromotion();

        boolean isAppliedMembership = inputHandler.askAboutMembership();
        membershipService.setApply(isAppliedMembership);
    }

    /**
     * 구매한 물품들 저장 메소드
     */
    public void processStock() {
        ProductService productService = new ProductService(products, purchaseProducts);
        productService.updateProductsStock();
    }

    /**
     * 구매 결과 관련 행동하는 메소드
     */
    public void processResult() {
        ReceiptService receiptService = new ReceiptService(products, purchaseProducts, membershipService, output);
        receiptService.processReceipt();
    }

}
