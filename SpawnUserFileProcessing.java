package com.java.excel;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import javax.sql.rowset.Joinable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.test.User;

public class SpawnUserFileProcessing {

	public static final String IN_FILE= "C:/Work/Workspace/Learning/JavaExcel/src/resources/Register.xlsx";
	public static final String OUT_FILE= "C:/Work/Workspace/Learning/JavaExcel/src/resources/RegisterResponse.xlsx";
	public static final String OUT_FILE_TXT= "C:/Work/Workspace/Learning/JavaExcel/src/resources/RegisterResponse.txt";
	
	public static void main(String[] args) throws Exception {

		FileInputStream file = new FileInputStream(IN_FILE);

		final FileOutputStream outFile = new FileOutputStream(OUT_FILE);
		
		Workbook requestWorkbook = new XSSFWorkbook(file);
		Sheet inSheet = requestWorkbook.getSheetAt(0);
				
		int inFirstRowNum = inSheet.getFirstRowNum();
		int inLastRownNum = inSheet.getLastRowNum();
		
		
		 Workbook responseBook = new XSSFWorkbook();
		 Sheet responseSheet = responseBook.createSheet("Response");
		
		final LineProcessor fileProcessorObj = new LineProcessor(responseSheet);
		
		
		for(int i = inFirstRowNum+1; i <= inLastRownNum; i++) {
			
		
			 final Row requestRow =  inSheet.getRow(i);
			
			Thread fileProcessingThread = new Thread(new Runnable(){

				public void run() {
					
					try {
						
						 fileProcessorObj.processLine(requestRow);
						
					} catch (Exception e) {
						System.out.println(e);
					}
					
				}
			}
			);
			
		
			fileProcessingThread.start();
			fileProcessingThread.join();
		}
		System.out.println("Closing filess");
		requestWorkbook.close();
		responseBook.write(outFile);
		responseBook.close();
		outFile.close();
		
	}

}
