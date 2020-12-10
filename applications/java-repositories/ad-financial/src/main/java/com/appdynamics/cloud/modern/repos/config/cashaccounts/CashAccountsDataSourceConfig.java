/**
 * 
 */
package com.appdynamics.cloud.modern.repos.config.cashaccounts;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.appdynamics.cloud.modern.repos.model.cashaccounts.CashAccount;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author James Schneider
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.appdynamics.cloud.modern.repos.repository.cashaccounts",
        entityManagerFactoryRef = "cashAccountsEntityManager",
        transactionManagerRef= "cashAccountsTransactionManager")
public class CashAccountsDataSourceConfig {
	 	
	/**
	 * 
	 */
	public CashAccountsDataSourceConfig() {
		
	}

	@Primary
	@Bean(name = "cashAccountsEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManager(
        EntityManagerFactoryBuilder builder) {
    return builder
            .dataSource(dataSource())
            .packages(CashAccount.class)
            .build();
    }
    
	@Primary
    @Bean(name = "cashAccountsDataSourceProperties")
    @ConfigurationProperties("app.datasource.cashaccounts")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
    
	@Primary
    @Bean(name = "cashAccountsDataSource")
    @ConfigurationProperties("app.datasource.cashaccounts.configuration")
    public DataSource dataSource() {
    	return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

	@Primary
	@Bean(name = "cashAccountsTransactionManager")
    public PlatformTransactionManager transactionManager(final @Qualifier("cashAccountsEntityManager") LocalContainerEntityManagerFactoryBean cashAccountsEntityManager) {
        return new JpaTransactionManager(cashAccountsEntityManager.getObject());
    }
   
}
