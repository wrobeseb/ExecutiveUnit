package pwr.tin.tip.sw.pd.eu.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		AppContext.getInstance().setContext(new ClassPathXmlApplicationContext("classpath:applicationContext.xml"));
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
