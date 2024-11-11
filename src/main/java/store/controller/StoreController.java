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

        MembershipService membershipService = new MembershipService();
        processPresent(inputHandler, membershipService);
        processStock();
        processResult(output, membershipService);
    }

    /**
     * 인삿말과 보유 상품들 출력 메소드
     */
    public void processNoticeInitial(Output output) {
        output.printHello();
        output.printProducts();
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
     * @param input
     */
    public void processPurchaseProducts(Input input) {
        String[] splitCommaInput = input.requestProducts();
        PurchaseProductRepository purchaseProductRepository = new PurchaseProductRepository(purchaseProducts);

        purchaseProductRepository.savePurchaseProducts(splitCommaInput);
    }

    /**
     * 구매한 물품들 중 프로모션 상품들 관련 행동 메소드
     */
    public void processPresent(InputHandler inputHandler, MembershipService membershipService) {
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
    public void processResult(Output output, MembershipService membershipService) {
        ReceiptService receiptService = new ReceiptService(products, purchaseProducts, membershipService, output);
        receiptService.processReceipt();
    }

}
