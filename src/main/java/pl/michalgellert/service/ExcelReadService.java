package pl.michalgellert.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelReadService
{
    public List<XSSFWorkbook> readExcelFiles() throws IOException, InvalidFormatException, NullPointerException
    {
        String dirPath = "C:\\Users\\mgellert\\Priv\\mtg\\shop_files\\input";
        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();
        
        List<XSSFWorkbook> list = new ArrayList<>();
        for (File file : Objects.requireNonNull(fileList)) {
            if (file.isFile() && file.getName().endsWith(".xlsx")) {
                list.add(new XSSFWorkbook(file));
            }
        }
        return list;
    }
}
