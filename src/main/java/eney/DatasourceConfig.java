package eney;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
	
	@Configuration
	@MapperScan(basePackages="com.eney.portal.mapper", sqlSessionFactoryRef=WebDatasourceConfig.SQL_SESSION_FACTORY_NAME)
	public class WebDatasourceConfig {

		public static final String SQL_SESSION_FACTORY_NAME = "sessionFactoryWeb";
		public static final String SQL_SESSION_NAME = "sqlSessionWeb";
		public static final String TX_MANAGER = "txManagerWeb";

		@Bean(name="webDatasource")
		@Primary
		@ConfigurationProperties(prefix="datasource.web")
		public DataSource primaryDataSource() {
			return DataSourceBuilder.create().build();
		}
		
		@Bean(name=TX_MANAGER)
		@Primary
		public PlatformTransactionManager txManagerKm() {
			return new DataSourceTransactionManager(primaryDataSource());
		}
		
		@Bean(name=WebDatasourceConfig.SQL_SESSION_FACTORY_NAME)
		@Primary
		public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/config/mybatis-conf.xml"));
			sqlSessionFactoryBean.setDataSource(primaryDataSource());
			sqlSessionFactoryBean.setMapperLocations(
					new PathMatchingResourcePatternResolver()
						.getResources("classpath*:eney/mapper/*.xml"));
			
			return sqlSessionFactoryBean.getObject();
		}
		
		@Bean(name=WebDatasourceConfig.SQL_SESSION_NAME)
		@Primary
		public SqlSession SqlSessionBean() throws Exception{
			return new SqlSessionTemplate(sqlSessionFactoryBean());
		}
	}

	
/*	@Configuration
	@MapperScan(basePackages="com.eney.portal.mapper2", sqlSessionFactoryRef=IvrDatasourceConfig.SQL_SESSION_FACTORY_NAME)
	public class IvrDatasourceConfig {
		
		public static final String SQL_SESSION_FACTORY_NAME = "sessionFactoryIvr";
		public static final String SQL_SESSION_NAME = "sqlSessionIvr";
		public static final String TX_MANAGER = "txManagerIvr";
		
		@Bean(name="datasourceIvr")
		@ConfigurationProperties(prefix = "datasource.ivr")
		public DataSource dataSourceIvr() {
			return DataSourceBuilder.create().build();
		}
		
		@Bean(name=TX_MANAGER)
		public PlatformTransactionManager txManagerIvr() {
			return new DataSourceTransactionManager(dataSourceIvr());
		}
		
		@Bean(name=IvrDatasourceConfig.SQL_SESSION_FACTORY_NAME)
		public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/config/mybatis-conf.xml"));
			sqlSessionFactoryBean.setDataSource(dataSourceIvr());
			sqlSessionFactoryBean.setMapperLocations(
					new PathMatchingResourcePatternResolver()
						.getResources("/com/eney/portal/mapper2/*.xml"));
			
			return sqlSessionFactoryBean.getObject();
		}
		
		@Bean(name=IvrDatasourceConfig.SQL_SESSION_NAME)
		public SqlSession SqlSessionBean() throws Exception{
			return new SqlSessionTemplate(sqlSessionFactoryBean());
		}
	}
	
	@Configuration
	@MapperScan(basePackages="com.eney.portal.mapper3", sqlSessionFactoryRef=IvrDatasourceConfig.SQL_SESSION_FACTORY_NAME)
	public class Ivr2DatasourceConfig {
		
		public static final String SQL_SESSION_FACTORY_NAME = "sessionFactoryIvr";
		public static final String SQL_SESSION_NAME = "sqlSessionIvr";
		public static final String TX_MANAGER = "txManagerIvr";
		
		@Bean(name="datasourceIvr")
		@ConfigurationProperties(prefix = "datasource.ivr2")
		public DataSource dataSourceIvr() {
			return DataSourceBuilder.create().build();
		}
		
		@Bean(name=TX_MANAGER)
		public PlatformTransactionManager txManagerIvr() {
			return new DataSourceTransactionManager(dataSourceIvr());
		}
		
		@Bean(name=IvrDatasourceConfig.SQL_SESSION_FACTORY_NAME)
		public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			
			sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/config/mybatis-conf.xml"));
			sqlSessionFactoryBean.setDataSource(dataSourceIvr());
			sqlSessionFactoryBean.setMapperLocations(
					new PathMatchingResourcePatternResolver()
						.getResources("/com/eney/portal/mapper3/*.xml"));
			
			return sqlSessionFactoryBean.getObject();
		}
		
		@Bean(name=IvrDatasourceConfig.SQL_SESSION_NAME)
		public SqlSession SqlSessionBean() throws Exception{
			return new SqlSessionTemplate(sqlSessionFactoryBean());
		}
	}*/
}
