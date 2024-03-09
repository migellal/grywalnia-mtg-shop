package pl.michalgellert;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.michalgellert.model.ExtendedCard;
import pl.michalgellert.model.MtgSource;
import pl.michalgellert.service.ExcelReadService;
import pl.michalgellert.service.ExcelWriteService;
import pl.michalgellert.service.MtgDataService;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            // auto download excel file
            ExcelReadService excelReadService = new ExcelReadService();
            ExcelWriteService excelWriteService = new ExcelWriteService();
            MtgDataService mtgDataService = new MtgDataService();

//            List<XSSFWorkbook> excelFiles = excelReadService.readExcelFiles();
//            List<MtgSource> sourceDataList = mtgDataService.prepareSourceData(excelFiles);
//            mtgDataService.updateMtgCache(sourceDataList);
//            List<ExtendedCard> possibleCards = mtgDataService.findPossibleCards(sourceDataList);
//            List<ExtendedCard> shopCards = mtgDataService.findShopCards(sourceDataList, possibleCards);
            List<ExtendedCard> shopCards2 = new ArrayList<>();
            ExtendedCard card1 = new ExtendedCard();
            ExtendedCard card2 = new ExtendedCard();
            card1.setRawName("Karta 1");
            card1.setText("Tkest na karcie");
            card1.setManaCost("{2}{R}");
            card1.setComment("wiadomość");
            card1.setFoil(true);
            card1.setContact("kontakt");
            card1.setType("kriczer");
            card1.setImageUrl("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=409741&type=card");

            card2.setRawName("Karta 2");
            card2.setText("Tkest na karcie");
            card2.setManaCost("{2}{R}");
            card2.setComment("wiadomość");
            card2.setFoil(false);
            card2.setContact("kontakt");
            card2.setType("kriczer");
            card2.setImageUrl("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=409741&type=card");

            shopCards2.add(card1);
            shopCards2.add(card2);
            excelWriteService.createShopExcel(shopCards2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}