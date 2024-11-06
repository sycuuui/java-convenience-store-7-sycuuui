package store.controller;

import store.enumerate.FileValues;
import store.service.ReadFile;

public class StoreController {
    private ReadFile readFile;

    public StoreController() {
        this.readFile = new ReadFile();
    }

    public void readFiles() {
        readFile.readPromotionsFile();
        readFile.readProductsFile();
    }

    public void start() {
        readFiles();
    }
}
