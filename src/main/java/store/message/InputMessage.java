package store.message;

public enum InputMessage {
    INPUT_PRODUCT("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    CURRENT("\n현재 "),
    ADD_QUESTION("은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y /N)"),
    SHORTAGE_QUESTION("개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y /N)"),
    MEMBERSHIP_QUESTION("\n멤버십 할인을 받으시겠습니까? (Y /N)");

    private String message;

    InputMessage(String message) {
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