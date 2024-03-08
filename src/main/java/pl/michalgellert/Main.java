package pl.michalgellert;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.michalgellert.model.MtgSource;
import pl.michalgellert.service.ExcelReadService;
import pl.michalgellert.service.ExcelWriteService;
import pl.michalgellert.service.MtgDataService;

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

            List<XSSFWorkbook> excelFiles = excelReadService.readExcelFiles();
            List<MtgSource> sourceDataList = mtgDataService.prepareSourceData(excelFiles);
            mtgDataService.updateMtgCache(sourceDataList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}