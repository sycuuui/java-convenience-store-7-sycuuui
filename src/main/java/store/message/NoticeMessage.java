package store.message;

public enum NoticeMessage {
    HELLO("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다."),
    INPUT_PRODUCT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    CURRENT("현재 "),
    ADD_QUESTION("은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y /N)"),
    SHORTAGE_QUESTION("개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y /N)"),
    MEMBERSHIP_QUESTION("멤버십 할인을 받으시겠습니까? (Y /N)");

    private String message;

    NoticeMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getAddMessage(String name) {
        return CURRENT.getMessage()
                + name
                + ADD_QUESTION.getMessage();
    }

    public String getShortageMessage(String name, int count) {
        return CURRENT.getMessage()
                + name + " "
                + count
                + SHORTAGE_QUESTION.getMessage();
    }

}
