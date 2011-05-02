package pwr.tin.tip.sw.pd.eu.hotdeploy.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import pwr.tin.tip.sw.pd.eu.utils.ZipUtils;

public class HotDeployUtils {

	private static final String META_INF = "algorithm.id = <algorithm.id>\n" +
										   "algorithm.name = <algorithm.name>\n" +
										   "algorithm.run.path = <algorithm.run.path>\n";
	
	public static File getZippedAppPackage(Integer algorithmId, String algorithmName) {
		File appDirectory = new File("/" + algorithmName);
		//File app = new File(appDirectory, "app");
		File metaInf = new File(appDirectory, "META-INF");
		//File oFile = new File(app, algorithmName + ".o");
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(metaInf, true)));
			
			String metaInfBody = META_INF;
			metaInfBody.replace("<algorithm.id>", algorithmId.toString());
			metaInfBody.replace("<algorithm.name>", algorithmName);
			metaInfBody.replace("<algorithm.run.path>", algorithmName + "/app/" + algorithmName + ".o");
			
			writer.write(metaInfBody);
			
			writer.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		return ZipUtils.zipFile(appDirectory);
	}
}
