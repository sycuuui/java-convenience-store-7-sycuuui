package store.repository;

import store.dto.request.PromotionReq;
import store.enumerate.FileValues;
import store.message.ErrorMessage;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.view.Output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class PromotionRepository {
    private Promotions promotions;

    public PromotionRepository(Promotions promotions) {
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

        promotions.putPromotion(name, promotion);
    }
}
