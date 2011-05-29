package pwr.tin.tip.sw.pd.eu;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShutdownHook implements Runnable{
	 private ClassPathXmlApplicationContext context;
	    
	    public ShutdownHook(ClassPathXmlApplicationContext factory) {
	        this.context = factory;
	    }
	    
	    public void run() {
	        System.out.println("Destroying Singletons");
	        context.close();
	        System.out.println("Singletons Destroyed");
	    }
}