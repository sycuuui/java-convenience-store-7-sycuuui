package store.message;

public enum ErrorMessage {
    FILE_NOT_FOUND("파일을 열 수 없습니다."),
    FILE_IO("파일 입출력에 오류가 있습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
