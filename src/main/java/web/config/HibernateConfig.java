package web.config;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import web.model.User;

import javax.sql.DataSource;
import java.util.Properties;

import org.springframework.core.env.Environment;

import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setJpaVendorAdapter(getJpaVendorAdapter());
        lcemfb.setDataSource(getDataSource());
        lcemfb.setPersistenceUnitName("JpaPersistenceUnit");
        lcemfb.setPackagesToScan("web.model");
        lcemfb.setJpaProperties(hibernateProperties());
        return lcemfb;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        return adapter;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
                getEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    /*
     * Bean`s settings to use SessionFactory (Hibernate)
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());

        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(User.class);
        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
    */

}