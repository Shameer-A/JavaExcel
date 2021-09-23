package com.java.excel;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.test.User;

public class SyncExcelProcessing {

	public static final String IN_FILE= "C:/Work/Workspace/Learning/JavaExcel/src/resources/Register.xlsx";
	public static final String OUT_FILE= "C:/Work/Workspace/Learning/JavaExcel/src/resources/RegisterResponse.xlsx";
	
	public static void main(String[] args) throws Exception {

		SyncExcelProcessing obj = new SyncExcelProcessing(); 
		FileInputStream file = new FileInputStream(IN_FILE);
		FileOutputStream outFile = new FileOutputStream(OUT_FILE);
		
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
				
		int firstRowNum = sheet.getFirstRowNum();
		int lastRownNum = sheet.getLastRowNum();
		
		
		Workbook responseBook = new XSSFWorkbook();
		Sheet responseSheet = responseBook.createSheet("Response");
		
		Row responseHeader = responseSheet.createRow(0);
		responseHeader.createCell(0).setCellValue("Name");
		responseHeader.createCell(1).setCellValue("Age");
		responseHeader.createCell(2).setCellValue("Grade");
		responseHeader.createCell(3).setCellValue("Id");
		
		for(int i = firstRowNum+1; i <= lastRownNum; i++) {
			Row responseRow = responseSheet.createRow(i);
			 obj.processLine(sheet.getRow(i),responseRow);
			
		}
		
		responseBook.write(outFile);
		outFile.close();
	}
	
	private User processLine(Row row, Row responseRow) throws Exception{
		System.out.println("Processing Row No : "+row.getRowNum());
		int firstEntry = row.getFirstCellNum();
		int LastEntry = row.getLastCellNum();
		System.out.println("First Entry : "+firstEntry+" Value : "+row.getCell(firstEntry)+" Last Entry : "+LastEntry+" Value :"+row.getCell(LastEntry));
		
		User user = new User();
		
		user.setName(row.getCell(0).getStringCellValue());
		user.setAge((row.getCell(1).toString()));
		user.setGrade((row.getCell(2).toString()));
		
		Long id = saveUser(user,row.getRowNum());
		user.setId(id);
		
		responseRow.createCell(0).setCellValue(user.getName());
		responseRow.createCell(1).setCellValue(user.getAge());
		responseRow.createCell(2).setCellValue(user.getGrade());
		responseRow.createCell(3).setCellValue(user.getId());
		
		
		return user;
		//return "Success";
	}

	
	
	private Long saveUser(User user, int rowCount) throws Exception {
		Random random = new Random();
		long id = random.nextLong();
		while (id < 0) {
			id = random.nextLong();
		}
		user.setId(id+rowCount);
		return user.getId();
	}
}
