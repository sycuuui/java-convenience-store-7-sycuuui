package store.enumerate;

public enum FileValues {
    PRODUCTS("src/main/resources/products.md"),
    PROMOTIONS("src/main/resources/promotions.md");

    private String path;

    FileValues(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
