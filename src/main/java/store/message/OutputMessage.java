package store.message;

public enum OutputMessage {
    HELLO("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    PURCHASE_SECTION_HEADER("==============W 편의점================"),
    PRESENT_SECTION_HEADER("=============증\t\t정==============="),
    PRICE_SECTION_HEADER("====================================");

    private String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
