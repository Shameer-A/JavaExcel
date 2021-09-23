package com.java.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StrSubstitutor;

public class Sample {

	private static String TEMPLATE ="How are you ${first_name}. This is ${place}";
	private static String CUSTOMER_SCRIPT_TEMPLATE ="UPDATE MOB_CUSTOMERS SET STR_DISPLAY_NAME = '${displayName}' where id_customer = ${customerId};";
	private static String ADDRESS_SCRIPT_TEMPLATE ="UPDATE MOB_ADDRESSES SET STR_FIRST_NAME ='${firstName}', STR_MIDDLE_NAME ='${middleName}', '${lastName}' where id_customer =${customerId};";
	public static void main(String[] args) {
		
		String name ="Ooredoo";
		String place="Doha";
		
		String firstName="Abdul";
		String middleName="Shameer";
		String lastName="Abdul Khader";
		String customerId="100001";
		
			
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("first_name", name);
		parameters.put("place", place);
		
		parameters.put("firstName", firstName);
		parameters.put("middleName", middleName);
		parameters.put("lastName", lastName);
		parameters.put("customerId", customerId);
		parameters.put("displayName", name);
		
		StrSubstitutor sub = new StrSubstitutor(parameters);
		
		String message = sub.replace(TEMPLATE);
		
		String customerScript = sub.replace(CUSTOMER_SCRIPT_TEMPLATE);
		
		String addressScript = sub.replace(ADDRESS_SCRIPT_TEMPLATE);
		 
		System.out.println(message);
		
		System.out.println(customerScript);
		
		System.out.println(addressScript);
	}

}
