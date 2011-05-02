package pwr.tin.tip.sw.pd.eu.core.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import pwr.tin.tip.sw.pd.eu.db.model.Algorithm;
import pwr.tin.tip.sw.pd.eu.db.model.Job;

public class RootContainer {
	
	private Hashtable<Integer, Job> jobSpace;
	private Hashtable<Integer, Collection<Job>> deploymentWaitingJobSpace;
	private Queue<Job> blockingQueueOverflowJobSpace;
	private Hashtable<Integer, Algorithm> algorithmSpace;
	private Hashtable<Integer, Collection<Integer>> runningInstancesSpace;
	private Hashtable<Integer, Algorithm> hotdeploySpace;
	
	public boolean checkIfAlgorithmHasRunningInstances(Integer algorithmId) {
		if (runningInstancesSpace.containsKey(algorithmId)) {
			Collection<Integer> runningInstancesList = runningInstancesSpace.get(algorithmId);
			synchronized (runningInstancesList) {
				if (runningInstancesList.size() != 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else 
			return false;
	}
	
	public void putIdIntoInstanceSpace(Integer algorithmId, Integer instanceId) {
		
		if (runningInstancesSpace.containsKey(algorithmId)) {
			Collection<Integer> instancesList = runningInstancesSpace.get(algorithmId);
			synchronized (instancesList) {
				instancesList.add(instanceId);
			}
		}
		else {
			ArrayList<Integer> instancesList = new ArrayList<Integer>();
			instancesList.add(instanceId);
			runningInstancesSpace.put(algorithmId, Collections.synchronizedCollection(instancesList));
		}
	}
	
	public void removeIdFromInstanceSpace(Integer algorithmId, Integer instanceId) {
		if (runningInstancesSpace.containsKey(algorithmId)) {
			Collection<Integer> instancesList = runningInstancesSpace.get(algorithmId);
			synchronized (instancesList) {
				Integer index = null;
				Iterator<Integer> iter = instancesList.iterator();
				while (iter.hasNext()) {
					Integer instanceIdFromList = iter.next();
					if (instanceIdFromList.equals(instanceId)) {
						index = instanceIdFromList;
					}
				}
				if (index != null) {
					instancesList.remove(index);
				}
			}
		}
	}
	
	public void putJobIntoBlockingQueueOverflowJobsSpace(Job job) {
		synchronized (blockingQueueOverflowJobSpace) {
			blockingQueueOverflowJobSpace.add(job);
		}
	}
	
	public Job pullJobFromBlockingQueueOverflowJobsSpace() {
		synchronized (blockingQueueOverflowJobSpace) {
			return blockingQueueOverflowJobSpace.poll();
		}
	}
	
	public void putJobIntoTempSpace(Job job) {
		Integer algorithmId = job.getAlgorithm().getAlgorithmId();
		if (deploymentWaitingJobSpace.containsKey(algorithmId)) {
			Collection<Job> jobsList = deploymentWaitingJobSpace.get(algorithmId);
			synchronized (jobsList) {
				jobsList.add(job);
			}
		}
		else {
			ArrayList<Job> jobsList = new ArrayList<Job>();
			jobsList.add(job);
			deploymentWaitingJobSpace.put(algorithmId, jobsList);
		}
	}
	
	public void removeJobFromTempSpace(Integer algorithmId, Integer jobId) {
		if (deploymentWaitingJobSpace.containsKey(algorithmId)) {
			Collection<Job> jobsList = deploymentWaitingJobSpace.get(algorithmId);
			synchronized (jobsList) {
				Job index = null;
				Iterator<Job> iter = jobsList.iterator();
				while (iter.hasNext()) {
					Job job = iter.next();
					if (job.getId().equals(jobId)) {
						index = job;
					}					
				}
				if (index != null) {
					jobsList.remove(index);
				}
				if (jobsList.size() == 0) {
					deploymentWaitingJobSpace.remove(jobsList);
				}
			}
		}
	}
	
	public List<Job> getJobsByAlgorithmIdFromTempSpace(Integer algorithmId) {
		
		if (deploymentWaitingJobSpace.containsKey(algorithmId)) {
			return new ArrayList<Job>(deploymentWaitingJobSpace.get(algorithmId));
		}
		else {
			return new ArrayList<Job>();
		}
	}
	 
	public Hashtable<Integer, Algorithm> getHotDeploySpaceForSync() {
		return hotdeploySpace;
	}
	
	public Hashtable<Integer, Algorithm> getAlgorithmSpace() {
		return algorithmSpace;
	}
	
	public Boolean checkIfAlgorithmIsInDeployPhase(Integer id) {
		return hotdeploySpace.containsKey(id);
	}
	
	public void setAlgorithmForDeployment(Algorithm algorithmApp) {
		hotdeploySpace.put(algorithmApp.getAlgorithmId(), algorithmApp);
	}
	
	public void algorithmDeployed(Algorithm algorithmApp) {
		algorithmSpace.remove(algorithmApp.getAlgorithmId());
		algorithmSpace.put(algorithmApp.getAlgorithmId(), algorithmApp);
		hotdeploySpace.remove(algorithmApp.getAlgorithmId());
	}
	
	public void putAlgorithmIntoSpace(Algorithm algApp) {
		algorithmSpace.put(algApp.getAlgorithmId(), algApp);
	}
	
	public Algorithm pullAlgorithmFromSpace(Integer id) {
		return algorithmSpace.get(id);
	}
	
	public Algorithm pullAlgorithmByAppNameFromSpace(String appName) {
		for (Algorithm algorithmApp : algorithmSpace.values()) {
			if (algorithmApp.getAppName().equals(appName)) {
				return algorithmApp;
			}
		}
		return null;
	}
	
	public void removeAlgorithmFromSpace(Integer id) {
		algorithmSpace.remove(id);
	}
	
	public void putJobIntoSpace(Job job) {
		jobSpace.put(job.getId(), job);
	}
	
	public Job pullJobFromSpace(Integer id) {
		return jobSpace.get(id);
	}
	
	public void removeJobFromSpace(Integer id) {
		jobSpace.remove(id);
	}
	
	public void init() {
		jobSpace = new Hashtable<Integer, Job>();
		blockingQueueOverflowJobSpace = new PriorityQueue<Job>();
		deploymentWaitingJobSpace = new Hashtable<Integer, Collection<Job>>();
		algorithmSpace = new Hashtable<Integer, Algorithm>();
		hotdeploySpace = new Hashtable<Integer, Algorithm>();
		runningInstancesSpace = new Hashtable<Integer, Collection<Integer>>();
	}
}
