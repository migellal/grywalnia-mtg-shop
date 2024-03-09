package pl.michalgellert.service;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.michalgellert.model.ExtendedCard;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriteService
{
    public void createShopExcel(List<ExtendedCard> shopCards) throws IOException {
        String path = "/Users/mgellert/Documents/dev/projects/mtg/shop_data/output/mtg_shop.xlsx";
        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Shop");
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell;
        headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Description");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Mana");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Comment");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Foil");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Contact");
        headerCell.setCellStyle(headerStyle);

        int rowCounter = 1;

        for(ExtendedCard card : shopCards) {
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(false);

            Row row = sheet.createRow(rowCounter);
            Cell cell;
            cell = row.createCell(0);
            cell.setCellValue(card.getRawName());
            cell.setCellStyle(style);
            if(card.getImageUrl()!=null) {
                Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
                link.setAddress(card.getImageUrl());
                CellStyle hlink_style = workbook.createCellStyle();
                Font hlink_font = workbook.createFont();
                hlink_font.setUnderline(Font.U_SINGLE);
                hlink_font.setColor(IndexedColors.BLUE.getIndex());
                hlink_style.setFont(hlink_font);
                cell.setHyperlink(link);
            }

            cell = row.createCell(1);
            cell.setCellValue(card.getType());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(card.getText());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(card.getManaCost());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(card.getComment());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(card.isFoil() ? "1" : "");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(card.getContact());
            cell.setCellStyle(style);

            rowCounter++;
        }

        FileOutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        workbook.close();
    }
}
