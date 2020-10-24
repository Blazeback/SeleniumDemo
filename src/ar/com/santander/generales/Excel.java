package ar.com.santander.generales;

import java.io.File;
import java.io.FileInputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	
	public static Map<String, String> getData(String sheetName, String fileName) {

		Map<String, String> map = new HashMap<>();

		String filePath = "Excels\\" + fileName + ".xlsx";
		
		try (FileInputStream file = new FileInputStream(new File(filePath))) {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			for (int i = 1; i <= (Integer.valueOf(sheet.getLastRowNum())); i++) {
				if(!(sheet.getRow(i)==null))
					map.put(sheet.getRow(i).getCell(1).getStringCellValue(),
						sheet.getRow(i).getCell(2).getStringCellValue());
			}
			workbook.close();
		} catch (Exception e) {
			System.out.println("----");
			e.printStackTrace();
			System.out.println("Error when retrieving Excel data.");
			System.out.println("----");
		}
		return map;
	}

	public static Map<String,Map<String,String>> retrieveLongData(String fileName, String sheetName) {
		
		Map<String,Map<String,String>> data = new HashMap<>();
				
		String filePath = "Excels\\" + fileName + ".xlsx";
		
		try (FileInputStream file = new FileInputStream(new File(filePath))) {
			
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			
			for (int i = 1; i <= (Integer.valueOf(sheet.getLastRowNum())); i++) {
				
				Map<String, String> map = new HashMap<>();
				
				for (int j = 0; j < (Integer.valueOf(sheet.getRow(i).getLastCellNum())); j++) {
					
					if(!(sheet.getRow(i)==null)){
						
						try {
							
							map.put(sheet.getRow(0).getCell(j).getStringCellValue(),
									sheet.getRow(i).getCell(j).getStringCellValue());
							
						} catch (Exception e) {
							
							map.put(sheet.getRow(0).getCell(j).getStringCellValue(),
									"");
							
						}
						
					}
				
				}
				
			    data.put(sheet.getRow(i).getCell(0).getStringCellValue(), map);
					
			}
			
			workbook.close();
			
		} catch (Exception e) {
			
			System.out.println("----");
			e.printStackTrace();
			System.out.println("Error when retrieving Excel data.");
			System.out.println("----");
			
		}
		
		return data;
		
	}
	
	public static int rowAmount(String fileName, String sheetName) {
		
		String filePath = "Excels\\" + fileName + ".xlsx";
		try (FileInputStream file = new FileInputStream(new File(filePath))) {
			
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			
			return Integer.valueOf(sheet.getLastRowNum());
			
		} catch(Exception e) {
			
			System.out.println("----");
			e.printStackTrace();
			System.out.println("Error when retrieving Excel data.");
			System.out.println("----");
			
			return -1;
			
		}
		
	}
	
}
