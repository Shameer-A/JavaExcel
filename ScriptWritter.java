package com.java.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.text.StrSubstitutor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ScriptWritter {

	 private static final String FILE_NAME = "C:/Work/OQ/Docs/Request_Docs/New_KYC_Data.xlsx";
	 private static final String QUOTES ="'";
	 private static final String SPACE =" ";
	 private static final String EMPTY ="";
	 
	 private static final String OUT_FILE="C:/Work/OQ/Docs/Request_Docs/ResponseFile/03Query.txt";
	public static void main(String[] args) {
		
		 try {
			 	System.out.println("Read the file from the path :"+FILE_NAME+" Out Put File "+OUT_FILE);
			 	System.out.println("Write the file to the path :"+OUT_FILE);
			 	
	            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
	            
	            BufferedWriter writer = new BufferedWriter(new FileWriter(OUT_FILE));
	            
	            Workbook workbook = new XSSFWorkbook(excelFile);
	            
	            Sheet datatypeSheet = workbook.getSheetAt(0);
	            
	            Iterator<Row> iterator = datatypeSheet.iterator();
	            
	            int counter = 1;
	            
	            while (iterator.hasNext()) {
	            	

	                Row currentRow = iterator.next();
			       	
	            	String CUSTOMER_UPDATE_SCRIPT_TEMPLATE ="UPDATE MOB_CUSTOMERS CUSTOMERS SET CUSTOMERS.STR_DISPLAY_NAME ='${displayName}' \r\n" + 
	        				"WHERE CUSTOMERS.BOL_IS_ACTIVE ='Y' " + 
	        				"AND CUSTOMERS.INT_SPARE_2 NOT IN (4,5) " + 
	        				"AND CUSTOMERS.ID_CUSTOMER IN (SELECT IDENTITIES.ID_CUSTOMER FROM MOB_CUSTOMERS_IDENTITIES IDENTITIES WHERE " + 
	        				"IDENTITIES.id_identity_type  =2 " + 
	        				"AND IDENTITIES.bol_is_active ='Y' " + 
	        				"AND IDENTITIES.str_identity ='${qid}'); ";
	            	
	            	String ADDRESS_UPDATE_SCRIPT_TEMPLATE ="UPDATE MOB_ADDRESSES ADDRESS SET ADDRESS.STR_FIRST_NAME=${firstName}, ADDRESS.STR_MIDDLE_NAME=${middleName},"
	            			+ " ADDRESS.STR_LAST_NAME=${lastName} " + 
	            			"WHERE ID_CUSTOMER IN ( " + 
	            			"SELECT IDENTITIES.ID_CUSTOMER FROM MOB_CUSTOMERS_IDENTITIES IDENTITIES,  MOB_CUSTOMERS CUSTOMERS WHERE " + 
	            			"CUSTOMERS.BOL_IS_ACTIVE='Y' " + 
	            			"AND CUSTOMERS.INT_SPARE_2 NOT IN (4,5) " + 
	            			"AND CUSTOMERS.ID_CUSTOMER = IDENTITIES.ID_CUSTOMER " + 
	            			"AND IDENTITIES.id_identity_type = 2 " + 
	            			"and IDENTITIES.bol_is_active ='Y' " + 
	            			"AND IDENTITIES.str_identity ='${qid}'); ";
	            	
	            	Map<String,String> parameters = new HashMap<String,String>();
	            	
	            	
	            	            	
	            	String firstName = currentRow.getCell(1).getStringCellValue() ;
	            	String middleName = currentRow.getCell(2).getStringCellValue();
	            	String lastName = currentRow.getCell(3).getStringCellValue();
	            	
	            	StringBuffer displayName = exists(firstName) ? new StringBuffer(firstName.trim()): new StringBuffer("");
	            	if(exists(middleName)) {
	            		displayName.append(SPACE+middleName.trim());
	            	}
	            	if(exists(lastName)) {
	            		displayName.append(SPACE+lastName.trim());
	            	}
	            	
	            	
	        		parameters.put("firstName", exists(firstName)? QUOTES+firstName.trim()+QUOTES : QUOTES+EMPTY+QUOTES);
	        		parameters.put("middleName", exists(middleName)? QUOTES+middleName.trim()+QUOTES : QUOTES+EMPTY+QUOTES);
	        		parameters.put("lastName", exists(lastName)? QUOTES+lastName.trim()+QUOTES : QUOTES+EMPTY+QUOTES);
	        		parameters.put("displayName", displayName.substring(0, displayName.length() >80 ? 80 : displayName.length()));
	        		
	        			        		
	        		DataFormatter formatter = new DataFormatter();
	        		parameters.put("qid", formatter.formatCellValue(currentRow.getCell(0)));
	        		
	            	
	            	StrSubstitutor sub = new StrSubstitutor(parameters);
	        		
	            	String customerScript = sub.replace(CUSTOMER_UPDATE_SCRIPT_TEMPLATE);
	        		
	        		String addressScript = sub.replace(ADDRESS_UPDATE_SCRIPT_TEMPLATE);

	                System.out.println("Script Count  "+counter);

	                writer.write(customerScript);
	                writer.newLine();
	                writer.write(addressScript);
	                writer.newLine();
	                
				  counter++;

	            }
	            writer.close();
	            System.out.println("File Write is success to path :"+OUT_FILE);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}
	
	public static boolean exists(String s) {
		return s != null && s.trim().length() > 0;
	}

}
