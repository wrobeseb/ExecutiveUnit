package pwr.tin.tip.sw.pd.eu.core;

public abstract class CycleHelper {
	
	private Object obj = new Object();
	
	public void stop(long delay) {
		synchronized (obj) { try { obj.wait(delay); } catch (InterruptedException e) {} }
	}
}
