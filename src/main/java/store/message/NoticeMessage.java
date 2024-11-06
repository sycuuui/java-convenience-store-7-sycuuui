package store.message;

public enum NoticeMessage {
    HELLO("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다."),
    INPUT_PRODUCT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

    private String message;

    NoticeMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
