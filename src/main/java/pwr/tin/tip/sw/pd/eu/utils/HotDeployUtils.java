package pwr.tin.tip.sw.pd.eu.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.kahadb.util.ByteArrayInputStream;

import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;

public class HotDeployUtils {

	public static Algorithm readMetaInfFromFile(File metaInfFile) {
		Algorithm algorithm = new Algorithm();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(metaInfFile));
			
			byte[] buffer = new byte[1024];
			
			int len;
			
			while ((len = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, len);	
			}
			
			readMetaInf(algorithm, outStream);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return algorithm;
	}
	
	public static void readMetaInf(Algorithm algApp, ByteArrayOutputStream metaInf) throws FileNotFoundException {
		BufferedReader inReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(metaInf.toByteArray())));
		String line = "";
		try {
			while ((line = inReader.readLine()) != null) {
				if (line.contains("algorithm.id")) {
					String id = line.split("=")[1].trim();
					algApp.setAlgorithmId(Integer.parseInt(id));
				}
				else 
					if (line.contains("algorithm.name")) {
						String name = line.split("=")[1].trim();
						algApp.setName(name);
					}
					else 
						if (line.contains("algorithm.run.path")) {
							String runPath = line.split("=")[1].trim();
							algApp.setRunPath(runPath);
						}
			}
		} catch (IOException e) {
			
		}
	}
}
