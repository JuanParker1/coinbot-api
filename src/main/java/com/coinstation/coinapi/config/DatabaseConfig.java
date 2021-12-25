package com.coinstation.coinapi.config;

import com.coinstation.coinapi.CoinApiConstants;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
public class DatabaseConfig {
	private final static String DB_COIN_STATION = "dbCoinStation";
	private final static String SQL_COIN_STATION = "sqlCoinStation";
	
	@Primary
	@Bean(name = DB_COIN_STATION)
	@ConfigurationProperties(prefix = "spring.datasource.coin-station")
	public DataSource contentsHubDataSource() {
	    return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Primary
	@Bean(name = SQL_COIN_STATION)
	public SqlSessionFactory contentsHubSqlSessionFactory(@Qualifier(DB_COIN_STATION)DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resolver.getResources("classpath:query/*.xml"));
		return sessionFactory.getObject();
	}

	@Primary
	@Bean(name = CoinApiConstants.DB_SOURCE_COIN_STATION)
	public SqlSessionTemplate contentsHubSqlSessionTemplate(@Qualifier(SQL_COIN_STATION)SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}