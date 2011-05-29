package pwr.tin.tip.sw.pd.eu.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pwr.tin.tip.sw.pd.eu.ShutdownHook;


public class AppContext {

	private static AppContext _instance;
	
	private ApplicationContext _appliactionContext;

	private AppContext() {}
	
	public static AppContext getInstance() {
		if (_instance == null) {
			_instance = new AppContext();
		}
		return _instance;
	}
	
	public static void initContext() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Runtime.getRuntime().addShutdownHook(
	                new Thread(new ShutdownHook(context)));
		AppContext.getInstance().setContext(context);
	}
	
	public ApplicationContext getContext() {
		//synchronized (_appliactionContext) {
			return _appliactionContext;
		//}
	}
	
	public void setContext(ApplicationContext applicationContext) {
		_appliactionContext = applicationContext;
	}
}
