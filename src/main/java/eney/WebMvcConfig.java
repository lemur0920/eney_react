package eney;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
//import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter  {

    private final long MAX_AGE_SECS = 3600;

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.setOrder(Ordered.LOWEST_PRECEDENCE);
//        registry.addViewController("/**").setViewName("forward:/build/index.html");
//    }

//    @Override
//    public void configureViewResolvers(final ViewResolverRegistry registry) {
//        registry.jsp("/build/", ".html");
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(MAX_AGE_SECS);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/", "classpath:/META-INF/resources/webjars/");
//        registry
//                .addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/", "classpath:/META-INF/resources/");
    }


//    @Bean
//    public ViewResolver getViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/");
//        resolver.setSuffix(".html");
//        return resolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }


//	@Bean
//	public TilesConfigurer tilesConfigurer(){
//		final TilesConfigurer configurer = new TilesConfigurer();
//
//		configurer.setDefinitions(new String[] {
//				"classpath:/config/layout.xml",
//				"/WEB-INF/jsp/**/views.xml"
//				});
//		configurer.setCheckRefresh(true);
//
//		return configurer;
//	}
//
//	@Bean
//	public TilesViewResolver tilesViewResolver(){
//		final TilesViewResolver resolver = new TilesViewResolver();
//		resolver.setViewClass(TilesView.class);
//
//		return resolver;
//	}

	/*@Bean
    public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();

		Properties props = new Properties();

		Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String absolutePath=(new File(Thread.currentThread().getContextClassLoader().getResource("").getFile())).getPath();//this goes to webapps directory

        props.put("resource.loader", "file");
        props.put("file.resource.loader.path", absolutePath + "/velocity/");
        props.put("file.resource.loader.cache", "false");

        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();
    }*/

}
