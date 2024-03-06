package pl.michalgellert.service;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.michalgellert.model.MtgSource;

import java.util.ArrayList;
import java.util.List;

public class MtgDataService
{
    public List<MtgSource> findSourceData(List<XSSFWorkbook> excelFiles)
    {
        List<MtgSource> sourceDataList = new ArrayList<>();
        for (XSSFWorkbook workbook : excelFiles)
        {
            XSSFSheet sheet = workbook.getSheetAt(0);
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
                boolean foil = row.getCell(1).getStringCellValue().equals("1");
                String comment = row.getCell(2).getStringCellValue();
                Double price = row.getCell(3).getNumericCellValue();
                sourceDataList.add(new MtgSource(name, foil, comment, price));
            }
        }
        return sourceDataList;
    }
}
