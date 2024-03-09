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
import java.util.Arrays;
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
                String rawName = row.getCell(0).getStringCellValue();
                String name = rawName;
                if(rawName.contains("(")) {
                    name = rawName.substring(0, rawName.lastIndexOf('(')).trim();
                }
                CellType cellType = row.getCell(1).getCellType();
                boolean foil = false;
                switch (cellType) {
                    case STRING -> foil = row.getCell(1).getStringCellValue().equalsIgnoreCase("1");
                    case NUMERIC -> foil = row.getCell(1).getNumericCellValue() == 1;
                    default -> {
                    }
                }
                String comment = row.getCell(2).getStringCellValue();
                Double price = row.getCell(3).getNumericCellValue();
                sourceDataList.add(new MtgSource(rawName, name, foil, comment, price, contact));
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
                System.out.println("Downloaded card: " + downloadedCards.get(0).getName());
                downloadedCards.forEach(c -> {
                    ExtendedCard extendedCard = new ExtendedCard(c);
                    extendedCard.setRawName(s.getRawName());
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
                extendedCard.setRawName(s.getRawName());
                extendedCard.setName(s.getName());
                extendedCard.setPrice(s.getPrice());
                extendedCard.setFoil(s.isFoil());
                extendedCard.setComment(s.getComment());
                extendedCard.setContact(s.getContact());
                allCards.add(extendedCard);
            }
        });

        String json = gson.toJson(allCards);
        String dirPath = "/Users/mgellert/Documents/dev/projects/mtg/shop_data/cache/cards.json";
        Files.write(Paths.get(dirPath), json.getBytes());
    }

    public List<ExtendedCard> findPossibleCards(List<MtgSource> sourceDataList) {
        List<ExtendedCard> allCards = new ArrayList<>();
        sourceDataList.forEach(s -> {
            List<String> singleton = List.of("name="+s.getName());
            List<Card> downloadedCards = CardAPI.getAllCards(singleton);
            if(CollectionUtils.isNotEmpty(downloadedCards))
            {
                System.out.println("Downloaded card: " + downloadedCards.get(0).getName());
                downloadedCards.forEach(c -> {
                    ExtendedCard extendedCard = new ExtendedCard(c);
                    extendedCard.setRawName(s.getRawName());
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
                extendedCard.setRawName(s.getRawName());
                extendedCard.setName(s.getName());
                extendedCard.setPrice(s.getPrice());
                extendedCard.setFoil(s.isFoil());
                extendedCard.setComment(s.getComment());
                extendedCard.setContact(s.getContact());
                allCards.add(extendedCard);
            }
        });
        return allCards;
    }

    public List<ExtendedCard> findShopCards(List<MtgSource> sourceDataList, List<ExtendedCard> possibleCards) {
        List<ExtendedCard> result = new ArrayList<>();
        sourceDataList.forEach(sc -> {
            List<ExtendedCard> availableCards = possibleCards.stream()
                    .filter(pc -> sc.getName().equalsIgnoreCase(pc.getName()))
                    .toList();
            result.add(findCard(sc, availableCards));
        });
        return result;
    }

    private ExtendedCard findCard(MtgSource sc, List<ExtendedCard> availableCards) {
        if(availableCards.size()==1) {
            return availableCards.get(0);
        } else {
            String probablySet = null;
            if(sc.getRawName().contains("(")) {
                probablySet = sc.getRawName().substring(sc.getRawName().lastIndexOf('('), sc.getRawName().lastIndexOf(')'));
            } else if(Strings.isNotEmpty(sc.getComment())) {
                probablySet = Arrays.stream(sc.getComment().split(" ")).findFirst().get();
            }
            String finalProbablySet = probablySet;
            return availableCards.stream()
                    .filter(ac -> ac.getSet().equalsIgnoreCase(finalProbablySet))
                    .findFirst()
                    .orElse(availableCards.get(0));
        }
    }
}
