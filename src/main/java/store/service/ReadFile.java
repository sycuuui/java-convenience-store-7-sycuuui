package store.service;

import store.dto.request.ProductReq;
import store.dto.request.PromotionReq;
import store.enumerate.FileValues;
import store.message.ErrorMessage;
import store.model.Product;
import store.model.Products;
import store.model.Promotion;
import store.model.Promotions;
import store.view.Output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class ReadFile {
    private Products products;
    private Promotions promotions;

    public ReadFile(Products products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public void readPromotionsFile() {
        try {
            FileReader fileReader = new FileReader(FileValues.PROMOTIONS.getPath(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] datas = line.split(",");
                savePromotion(datas);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            Output.printErrorMessage(ErrorMessage.FILE_NOT_FOUND);
            throw new IllegalArgumentException();
        } catch (IOException e) {
            Output.printErrorMessage(ErrorMessage.FILE_IO);
            throw new IllegalArgumentException(e);
        }
    }

    public void savePromotion(String[] datas) {
        String name = datas[0];
        int buy = Integer.parseInt(datas[1]);
        int get = Integer.parseInt(datas[2]);
        LocalDate start_date = LocalDate.parse(datas[3]);
        LocalDate end_date = LocalDate.parse(datas[4]);

        PromotionReq promotionReq = new PromotionReq(buy, get, start_date, end_date);
        Promotion promotion = new Promotion(promotionReq);

        promotions.addPromotion(name, promotion);
    }

    public void readProductsFile() {
        try {
            FileReader fileReader = new FileReader(FileValues.PRODUCTS.getPath(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] datas = line.split(",");
                saveProduct(datas);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            Output.printErrorMessage(ErrorMessage.FILE_NOT_FOUND);
            throw new IllegalArgumentException();
        } catch (IOException e) {
            Output.printErrorMessage(ErrorMessage.FILE_IO);
            throw new IllegalArgumentException(e);
        }
    }

    public void saveProduct(String[] datas) {
        String name = datas[0];
        int price = Integer.parseInt(datas[1]);
        int quantity = Integer.parseInt(datas[2]);
        String promotion = datas[3];

        ProductReq productReq = new ProductReq(name, price, quantity);
        Product product = new Product(productReq);

        if (promotion != null) {
            products.addPromotionProduct(product, promotion);
            return;
        }
        products.addProduct(product);
    }
}
