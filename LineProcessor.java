package com.java.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.java.test.User;

public class LineProcessor {

	Sheet workSheet;
	//public static final String OUT_FILE= "C:/Work/Workspace/Learning/JavaExcel/src/resources/RegisterResponse.xlsx";
	
	
	  LineProcessor(Sheet responseSheet){
	  
	  try {
		 // final FileOutputStream outFile = new FileOutputStream(OUT_FILE);
		  this.workSheet = responseSheet;
		  
		  	Row responseHeader = workSheet.createRow(0);
			responseHeader.createCell(0).setCellValue("Name");
			responseHeader.createCell(1).setCellValue("Age");
			responseHeader.createCell(2).setCellValue("Grade");
			responseHeader.createCell(3).setCellValue("Id");
	  
	  
	  } catch (Exception e) { // TODO Auto-generated catch block
	  e.printStackTrace(); } }
	 
	
	
	public User processLine(Row row) throws Exception{
	
		
		Row responseRow = workSheet.createRow(row.getRowNum());
		
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
		
		System.out.println("Processing Row No : "+row.getRowNum()+" User : "+user);
		System.out.println("Response Row : "+responseRow.getLastCellNum()+" responseRow Last Value"+responseRow.getCell(3));
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



	public Sheet getWorkSheet() {
		return workSheet;
	}



	public void setWorkSheet(Sheet workSheet) {
		this.workSheet = workSheet;
	}
}
