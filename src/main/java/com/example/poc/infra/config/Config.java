package com.example.poc.infra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@EnableJpaRepositories(basePackages = "com.example", enableDefaultTransactions = false)
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class Config {


    private DataSource dataSourceRw() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("1234");
        config.setReadOnly(Boolean.FALSE);
        config.setAutoCommit(Boolean.TRUE);
        return new HikariDataSource(config);
    }



    private DataSource dataSourceRo() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/postgres");
        config.setUsername("postgres");
        config.setPassword("1234");
        config.setReadOnly(Boolean.TRUE);
        config.setAutoCommit(Boolean.FALSE);
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return new RoutingDsConfig(dataSourceRw() , dataSourceRo());
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        managerFactoryBean.setPackagesToScan("com.example");
        managerFactoryBean.setDataSource(dataSource);

        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("spring.jpa.open-in-view", "false");
        managerFactoryBean.setJpaProperties(properties);
        managerFactoryBean.afterPropertiesSet();

        return managerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
