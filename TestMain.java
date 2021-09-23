package com.java.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.SystemUtils;

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		File files = SystemUtils.getJavaIoTmpDir();
		File OUT_FILE = new File("C:/Work/OQ/Docs/");
		Files.copy(new BufferedInputStream( new FileInputStream(files)), OUT_FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		
	}

}
