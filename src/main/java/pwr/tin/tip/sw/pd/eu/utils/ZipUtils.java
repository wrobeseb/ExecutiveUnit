package pwr.tin.tip.sw.pd.eu.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.kahadb.util.ByteArrayInputStream;

import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public class ZipUtils {

	public static void unpackApplicationDir(byte[] zippedAppByteArray, File destinationDir) {
		try {
			ZipInputStream input = new ZipInputStream(new ByteArrayInputStream(zippedAppByteArray));
		
			ZipEntry entry;
			while ((entry = input.getNextEntry()) != null) {
				processEntry(entry, input, destinationDir.getAbsolutePath());
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Algorithm getAppFromZipPackage(File zipFile) throws ZipException, IOException {
		Algorithm algorithmApp = new Algorithm();
		
		ZipFile compressedFile = new ZipFile(zipFile);
		for (Enumeration<?> e = compressedFile.entries(); e.hasMoreElements();) {
			ZipEntry entry = (ZipEntry)e.nextElement();
			if (entry.getName().equals("META-INF")) {
				HotDeployUtils.readMetaInf(algorithmApp, unzipFile(compressedFile, entry));
			}
			else 
				if (entry.getName().endsWith(".zip")) {
					algorithmApp.setAppPackage(unzipFile(compressedFile, entry).toByteArray());
				}
		}
		
		compressedFile.close();
		return algorithmApp;
	}
	
	public static File zipFile (File file) {
		
		File zippedFile = new File(file.getName() + ".zip");
		
		try {
			FileOutputStream outStream = new FileOutputStream(zippedFile);
			ZipOutputStream zipOutStream = new ZipOutputStream(outStream);
	
			File[] fileList = file.listFiles();
			
			byte[] data = new byte[1024];
			
			for (File file2 : fileList) {
				FileInputStream inStream = new FileInputStream(file2);
				
				ZipEntry zipEntry = new ZipEntry(file2.getName());
				
				zipOutStream.putNextEntry(zipEntry);
				
				BufferedInputStream bInStream = new BufferedInputStream(inStream, 1024);
				
				int count;
				while ((count = bInStream.read(data)) != -1) {
					zipOutStream.write(data, 0, count);
				}
				
				bInStream.close();
			}
			
			zipOutStream.close();
			outStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return zippedFile;
	}
	
	private static ByteArrayOutputStream unzipFile(ZipFile compressedFile, ZipEntry entry) {
		BufferedInputStream inStream = null;
		BufferedOutputStream outStream = null;
		
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		try {
			inStream = new BufferedInputStream(compressedFile.getInputStream(entry));
			outStream = new BufferedOutputStream(byteArray);
		
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, len);
			}
		}
		catch (IOException ioEx) {
			
		}
		finally {
			try {
				if (inStream != null) { 
					inStream.close();
				}
				if (outStream != null) {
					outStream.close();
				}
				byteArray.close();
			}
			catch (IOException e) {}
		}		
		return byteArray;
	}
	
	private static void processEntry(ZipEntry entry, InputStream inStream, String outputDir) throws IOException {
		if (entry.isDirectory()) {
			(new File(outputDir, entry.getName())).mkdirs();
		}
		else {
			File file = new File(outputDir, entry.getName());
			File parentDir = file.getParentFile();
			if (parentDir != null) parentDir.mkdirs();
			OutputStream outStream = new BufferedOutputStream(new FileOutputStream(file));
		
			IOUtils.copy(inStream, outStream);
			
			if (!(inStream instanceof ZipInputStream)) {
				inStream.close();
			}
			outStream.close();
		}
	}
}
