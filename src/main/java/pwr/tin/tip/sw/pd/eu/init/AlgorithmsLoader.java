package pwr.tin.tip.sw.pd.eu.init;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pwr.tin.tip.sw.pd.eu.core.container.RootContainer;
import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.service.IAlgorithmService;
import pwr.tin.tip.sw.pd.eu.utils.HotDeployUtils;

public class AlgorithmsLoader {

	private final static Logger log = LoggerFactory.getLogger(AlgorithmsLoader.class);
	
	private IAlgorithmService algorithmService;
	private RootContainer rootContainer;
	private String runtimeDirectory;

	public void setAlgorithmService(IAlgorithmService algorithmService) {
		this.algorithmService = algorithmService;
	}
	public void setRootContainer(RootContainer rootContainer) {
		this.rootContainer = rootContainer;
	}
	public void setRuntimeDirectory(String runtimeDirectory) {
		this.runtimeDirectory = runtimeDirectory;
	}
	
	public void load() {
		Hashtable<Integer, Algorithm> algorithmsSpace = rootContainer.getAlgorithmSpace();
		algorithmService.loadAlgorithmIntoSpace(algorithmsSpace);
		
		if (!checkCompliance(algorithmsSpace)) {
			fillSpaceFromRuntimeDir(algorithmsSpace);
		}
	}
	
	private boolean checkCompliance(Hashtable<Integer, Algorithm> algorithmsSpace) {
		List<Algorithm> listOfAlgorithms = getListOfAlgorithmsInRuntimeDir();
		
		if (algorithmsSpace.size() == 0) {
			return false;
		}
		if (algorithmsSpace.size() != listOfAlgorithms.size()) {
			return false;
		}
		for (Algorithm algorithm : listOfAlgorithms) {
			if (!algorithmsSpace.containsKey(algorithm.getAlgorithmId())) {
				return false;
			}
		}
		return true;
	}
	
	private void fillSpaceFromRuntimeDir(Hashtable<Integer, Algorithm> algorithmsSpace) {
		algorithmService.deleteAlgorithms();
		for (Algorithm algorithm : getListOfAlgorithmsInRuntimeDir()) {
			algorithmsSpace.put(algorithm.getAlgorithmId(), algorithm);
			algorithmService.saveAlgorithm(algorithm);
		}
	}
	
	private List<Algorithm> getListOfAlgorithmsInRuntimeDir() {
		File runtimeDir = new File(runtimeDirectory);
		File[] deployedAlgorithms = runtimeDir.listFiles();
		
		List<Algorithm> listOfAlgorithms = new ArrayList<Algorithm>();
		if (deployedAlgorithms != null) {
	 		for (File file : deployedAlgorithms) {
				File[] packageTree = file.listFiles();
				for (File fileInPackage : packageTree) {
					if (fileInPackage.getName().equals("META-INF")) {
						listOfAlgorithms.add(HotDeployUtils.readMetaInfFromFile(fileInPackage));
					}
				}
			}
		}
		else {
			log.warn("Nie znaleziono aplikacji algorytmow w katalogu runtime...");
		}
		return listOfAlgorithms;
	}
}
