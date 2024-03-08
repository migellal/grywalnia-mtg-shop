package pl.michalgellert.service;

import com.google.gson.Gson;
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.michalgellert.model.ExtendedCard;
import pl.michalgellert.model.MtgSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MtgDataService
{
    private Gson gson = new Gson();
    public List<MtgSource> prepareSourceData(List<XSSFWorkbook> excelFiles)
    {
        List<MtgSource> sourceDataList = new ArrayList<>();
        for (XSSFWorkbook workbook : excelFiles)
        {
            XSSFSheet sheet = workbook.getSheetAt(0);
            String contact = "";
            if(Objects.nonNull(sheet.getRow(1).getCell(5)))
            {
                contact = sheet.getRow(1).getCell(5).getStringCellValue();
            }
            for (Row row : sheet)
            {
                if (row.getRowNum() == 0)
                {
                    continue;
                }
                if(Strings.isBlank(row.getCell(0).getStringCellValue()))
                {
                    break;
                }
                String name = row.getCell(0).getStringCellValue();
                CellType cellType = row.getCell(1).getCellType();
                boolean foil = false;
                switch (cellType)
                {
                    case STRING:
                        foil = row.getCell(1).getStringCellValue().equalsIgnoreCase("1");
                        break;
                    case NUMERIC:
                        foil = row.getCell(1).getNumericCellValue() == 1;
                        break;
                    default:
                        break;
                }
                String comment = row.getCell(2).getStringCellValue();
                Double price = row.getCell(3).getNumericCellValue();
                sourceDataList.add(new MtgSource(name, foil, comment, price, contact));
            }
        }
        return sourceDataList;
    }

    public void updateMtgCache(List<MtgSource> sourceDataList) throws IOException
    {
        List<ExtendedCard> allCards = new ArrayList<>();
        sourceDataList.forEach(s -> {
            List<String> singleton = List.of("name="+s.getName());
            List<Card> downloadedCards = CardAPI.getAllCards(singleton);
            if(CollectionUtils.isNotEmpty(downloadedCards))
            {
                System.out.println("Downloaded card: " + downloadedCards.getFirst().getName());
                downloadedCards.forEach(c -> {
                    ExtendedCard extendedCard = new ExtendedCard(c);
                    extendedCard.setPrice(s.getPrice());
                    extendedCard.setFoil(s.isFoil());
                    extendedCard.setComment(s.getComment());
                    extendedCard.setContact(s.getContact());
                    allCards.add(extendedCard);
                });
            }
            else {
                System.out.println("Card not found: " + s.getName());
                ExtendedCard extendedCard = new ExtendedCard();
                extendedCard.setName(s.getName());
                extendedCard.setPrice(s.getPrice());
                extendedCard.setFoil(s.isFoil());
                extendedCard.setComment(s.getComment());
                extendedCard.setContact(s.getContact());
                allCards.add(extendedCard);
            }
        });

        String json = gson.toJson(allCards);
        String dirPath = "C:\\Users\\mgellert\\Priv\\mtg\\shop_files\\cache\\cards.json";
        Files.write(Paths.get(dirPath), json.getBytes());
    }
}
