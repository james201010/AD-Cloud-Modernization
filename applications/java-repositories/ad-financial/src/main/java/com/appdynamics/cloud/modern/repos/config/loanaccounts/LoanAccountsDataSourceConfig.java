/**
 * 
 */
package com.appdynamics.cloud.modern.repos.config.loanaccounts;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.appdynamics.cloud.modern.repos.model.loanaccounts.LoanAccount;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author James Schneider
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.appdynamics.cloud.modern.repos.repository.loanaccounts",
        entityManagerFactoryRef = "loanAccountsEntityManager",
        transactionManagerRef= "loanAccountsTransactionManager")
public class LoanAccountsDataSourceConfig {
	 	
	/**
	 * 
	 */
	public LoanAccountsDataSourceConfig() {
		
	}


	@Bean(name = "loanAccountsEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManager(
        EntityManagerFactoryBuilder builder) {
    return builder
            .dataSource(dataSource())
            .packages(LoanAccount.class)
            .build();
    }
    

    @Bean(name = "loanAccountsDataSourceProperties")
    @ConfigurationProperties("app.datasource.loanaccounts")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
    

    @Bean(name = "loanAccountsDataSource")
    @ConfigurationProperties("app.datasource.loanaccounts.configuration")
    public DataSource dataSource() {
    	return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


	@Bean(name = "loanAccountsTransactionManager")
    public PlatformTransactionManager transactionManager(final @Qualifier("loanAccountsEntityManager") LocalContainerEntityManagerFactoryBean loanAccountsEntityManager) {
        return new JpaTransactionManager(loanAccountsEntityManager.getObject());
    }
   
}
