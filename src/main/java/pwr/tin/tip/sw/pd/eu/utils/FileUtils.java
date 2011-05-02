package pwr.tin.tip.sw.pd.eu.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public class FileUtils {

	public static void writeMetaInfFile(Algorithm algorithmApp, File destinationFile) {
		OutputStreamWriter outStreamWriter = null;
		BufferedWriter writer = null;
		try {
			outStreamWriter = new OutputStreamWriter(new FileOutputStream(destinationFile));
			writer = new BufferedWriter(outStreamWriter);
			writer.write("algorithm.id = " + algorithmApp.getAlgorithmId()); writer.newLine();
			writer.write("algorithm.name = " + algorithmApp.getName()); writer.newLine();
			writer.write("algorithm.run.path = " + algorithmApp.getRunPath()); writer.newLine();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (writer != null)
					writer.close();
				if (outStreamWriter != null)
					outStreamWriter.close();
			}
			catch (IOException ioEx) {
				
			}
		}
	}
	
	public static void moveFileIntoDir(File file, File destinationDir) {
		try {
			org.apache.commons.io.FileUtils.copyFileToDirectory(file, destinationDir);
			file.delete();
		}
		catch (IOException e) {
			
		}
	}
	
	public static void moveDirIntoDestination(File dir, File destDir) throws IOException {
		org.apache.commons.io.FileUtils.copyDirectory(dir, destDir);
		org.apache.commons.io.FileUtils.deleteDirectory(dir);
	}
	
	
}
