package pwr.tin.tip.sw.pd.eu.context.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

@Configuration
public class HibernateConfig {

	private @Value("${hibernate.hbm2ddl.auto}") String hbm2ddl;
	
	private @Value("${hibernate.connection.driver_class}") String driverClass;
	private @Value("${hibernate.dialect}") String dialect;
	
	private @Value("${hibernate.c3p0.min_size}") Integer c3p0MinSize;
	private @Value("${hibernate.c3p0.max_size}") Integer c3p0MaxSize;
	private @Value("${hibernate.c3p0.max_statements}") Integer c3p0MaxStatements; 
	
	private @Value("${jdbc.url}") String url;
	private @Value("${jdbc.username}") String username;
	private @Value("${jdbc.password}") String password;
	
	private static final Properties jdbcDefaultProperties;
	
	static {
		jdbcDefaultProperties = new Properties();
		//jdbcDefaultProperties.put("url", "jdbc:hsqldb:mem:ias");
		jdbcDefaultProperties.put("url", "jdbc:log4jdbc:hsqldb:mem:ias");
		jdbcDefaultProperties.put("user", "sa");
		jdbcDefaultProperties.put("password", "");
	}
	
	private static final Properties hibernateDefaultProperties;
	
	static {		
		hibernateDefaultProperties = new Properties();
		//hibernateDefaultProperties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		hibernateDefaultProperties.put("hibernate.connection.driver_class", "net.sf.log4jdbc.DriverSpy");
		hibernateDefaultProperties.put("hibernate.c3p0.min_size", 1);
		hibernateDefaultProperties.put("hibernate.c3p0.max_size", 5);
		hibernateDefaultProperties.put("hibernate.c3p0.max_statements", 0);
		hibernateDefaultProperties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		
		hibernateDefaultProperties.put("hibernate.hbm2ddl.auto", "validate");
		hibernateDefaultProperties.put("hibernate.show_sql", true);
		hibernateDefaultProperties.put("hibernate.connection.autocommit", true);
	}
	
	@SuppressWarnings("rawtypes")
	private static final Class[] annotatedClasses;
	
	static {
		annotatedClasses = new Class[] {pwr.tin.tip.sw.pd.eu.db.model.Job.class,
										pwr.tin.tip.sw.pd.eu.db.model.Algorithm.class};
	}
	
	public @Bean SessionFactory sessionFactory() {
		return sessionFactoryBean().getObject();
	}
	
	public @Bean LocalSessionFactoryBean sessionFactoryBean() {
		AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
		sessionFactory.setAnnotatedClasses(annotatedClasses);
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	public @Bean HibernateTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory());
	}
	
	public @Bean DataSource dataSource() {
		Properties jdbcProperties = jdbcProperties();
		return new DriverManagerDataSource(jdbcProperties.getProperty("url"), jdbcProperties.getProperty("user"), jdbcProperties.getProperty("password"));
	}
	
	private Properties jdbcProperties() {
		Properties properties = new Properties(jdbcDefaultProperties);
		if (url != null) {
			properties.put("url", url);
		}
		if (username != null) {
			properties.put("user", username);
		}
		if (password != null) {
			properties.put("password", password);
		}
		return properties;
	}
	
	private Properties hibernateProperties() {
		Properties properties = new Properties(hibernateDefaultProperties);
		if (driverClass != null) {
			properties.put("hibernate.connection.driver_class", driverClass);
		}
		if (dialect != null) {
			properties.put("hibernate.dialect", dialect);
		}
		if (c3p0MinSize != null) {
			properties.put("hibernate.c3p0.min_size", c3p0MinSize);
		}
		if (c3p0MaxSize != null) {
			properties.put("hibernate.c3p0.max_size", c3p0MaxSize);
		}
		if (c3p0MaxStatements != null) {
			properties.put("hibernate.c3p0.max_statements", c3p0MaxStatements);
		}
		if (hbm2ddl != null) {
			properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		}
		return properties;
	}
}
