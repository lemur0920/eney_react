package eney;

import eney.filter.CustomEncodingFilter;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import eney.filter.CustomEncodingFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.concurrent.Executor;
@EnableScheduling
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages="com.eney.portal.mapper")
@EntityScan
public class EneyApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		System.setProperty("spring.devtools.livereload.enable", "true");
		SpringApplication.run(EneyApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EneyApplication.class);
	}
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}

//	@Bean
//	public HealthIndicator dataSourceHealth(DataSource dataSource) {
//		if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
//			org.apache.tomcat.jdbc.pool.DataSource tcDataSource =
//					(org.apache.tomcat.jdbc.pool.DataSource) dataSource;
//			return new AbstractHealthIndicator() {
//				@Override
//				protected void doHealthCheck(Health.Builder healthBuilder) throws Exception {
//					healthBuilder.up().withDetail("active", tcDataSource.getActive())
//							.withDetail("max_active", tcDataSource.getMaxActive())
//							.withDetail("idle", tcDataSource.getIdle())
//							.withDetail("max_idle", tcDataSource.getMaxIdle())
//							.withDetail("min_idle", tcDataSource.getMinIdle())
//							.withDetail("wait_count", tcDataSource.getWaitCount())
//							.withDetail("max_wait", tcDataSource.getMaxWait());
//				}
//			};
//		}
//		return null;
//	}


	/* Encoding Filter */
	@Bean
	public Filter characterEncodingFilter(){
		CustomEncodingFilter filter = new CustomEncodingFilter();

		filter.setEncoding("utf-8");
		filter.setForceEncoding(true);

		return filter;
	}

	/**
	 * LucyXSS 필터 생성
	 * @return
	 */
	@Bean
	public Filter xssEscapeServletFilter(){
		XssEscapeServletFilter escapeServletFilter = new XssEscapeServletFilter();

		return escapeServletFilter;
	}
}
