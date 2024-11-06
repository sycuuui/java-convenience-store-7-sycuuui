package store.message;

public enum ErrorMessage {
    FILE_NOT_FOUND("파일을 열 수 없습니다."),
    FILE_IO("파일 입출력에 오류가 있습니다."),
    INPUT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NO_EXISTS("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ETC("잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
