package com.example.assetService.service;

import com.example.assetService.model.Asset;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelUploadService {
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
    public static List<Asset> getAssetsDataFromExcel(InputStream inputStream){
        List<Asset> assets = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("assets");
            int rowIndex =0;
            for (Row row : sheet){
                Date createdAt = new Date();
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Asset asset = new Asset();
                asset.setDateInStored(createdAt);
                asset.setAssetStatus(Long.valueOf(0));
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> asset.setAssetName(cell.getStringCellValue());
                        case 1 -> asset.setAssetType(Math.round(cell.getNumericCellValue()));
                        case 2 -> asset.setPrice(cell.getNumericCellValue());
                        case 3 -> asset.setSerialNumber(cell.getStringCellValue());
                    }
                    cellIndex++;
                }
                assets.add(asset);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return assets;
    }

}