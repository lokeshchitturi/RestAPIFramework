package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Utility {

	public static void main(String[] args) throws IOException {

		List<Map<String, String>> data = readData();

	}
	
	public static List<Map<String, String>> readData() throws IOException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {	
			FileInputStream fis = new FileInputStream(PropertyReader.prop.getProperty("ExcelSheetPath")+".xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getLastRowNum();
			int cols = sheet.getRow(0).getLastCellNum();

			for (int i = 0; i < rows; i++) {
				Map<String, String> data = new HashMap<String, String>();
				for (int j = 0; j < cols; j++) {
					String header = sheet.getRow(0).getCell(j).getStringCellValue();

					String value = sheet.getRow(i + 1).getCell(j).getStringCellValue();
					if (value.length() != 0)
						value = value.trim();
					System.out.println("header is :   " + header + "        Column is :  " + value);

					data.put(header, value);

				}
				list.add(data);
				System.out.println("-------------------------------------------------------------------------------");
			}
			System.out.println(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
	
	
	public static void writeToFile(String response)
	{
		FileOutputStream fop = null;
		File file;

		try {
			DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
			LocalDateTime sysytemTime=LocalDateTime.now();
			
			
			file = new File(PropertyReader.prop.getProperty("outputReponseLocation")+"Response_"+sysytemTime.format(formatter)+".json");
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = response.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}