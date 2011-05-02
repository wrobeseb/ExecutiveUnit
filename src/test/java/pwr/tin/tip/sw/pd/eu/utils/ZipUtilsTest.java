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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.kahadb.util.ByteArrayInputStream;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZipUtilsTest {

	private File zippedFile;
	private byte[] zippedFileByteArray;
	private File outputDir;
	
	@Before
	public void init() throws IOException {
		zippedFile = new File("F:/cygwin/opt/git/repository/ExecutiveUnit/src/test/resources/pwr/tin/tip/sw/pd/eu/utils/test.zip");
		outputDir = zippedFile.getParentFile();
		
		InputStream inStream = new BufferedInputStream(new FileInputStream(zippedFile));
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		int len;
		
		while ((len = inStream.read(buffer)) >= 0) {
			outStream.write(buffer, 0, len);
		}
		
		inStream.close();
		outStream.close();
		
		zippedFileByteArray = outStream.toByteArray();
		
	}
	
	@Test
	public void fileExistsTest() {
		assertTrue(zippedFile.exists());
	}
	
	@Test
	public void unZipFileTest() throws ZipException, IOException {
		ZipFile compressedFile = new ZipFile(zippedFile);
		
		Enumeration<?> e = compressedFile.entries();
		
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry)e.nextElement();
			processEntry(entry, compressedFile.getInputStream(entry));
		}
		
		asserts();
	}
	
	@Test
	public void unZipFromByteArrayTest() throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zippedFileByteArray));
	
		ZipEntry entry;
		while ((entry = zipInputStream.getNextEntry()) != null) {
			processEntry(entry, zipInputStream);
		}
		zipInputStream.close();
		
		asserts();
	}
	
	private void asserts() throws IOException {
		assertTrue(new File(outputDir + "/test").exists());
		assertTrue(new File(outputDir + "/test/test1").exists());
		assertTrue(new File(outputDir + "/test/test1/test2").exists());
		assertTrue(new File(outputDir + "/test/test_1").exists());
		assertTrue(new File(outputDir + "/test/test_2").exists());
		assertTrue(new File(outputDir + "/test/test1/test1_1").exists());
		assertTrue(new File(outputDir + "/test/test1/test1_2").exists());
		assertTrue(new File(outputDir + "/test/test1/test2/test2_1").exists());
		assertTrue(new File(outputDir + "/test/test1/test2/test2_2").exists());
		assertTrue(new File(outputDir + "/test/test1/test2/test2_3").exists());
		
		File uncompressedDir = new File (outputDir +"/test");
		if (uncompressedDir.exists()) {
			FileUtils.deleteDirectory(uncompressedDir);
		}
	}
	
	private void processEntry(ZipEntry entry, InputStream inStream) throws IOException {
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
