package eney;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*API 문서 자동화를 위해 사용*/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
    TypeResolver typeResolver;

	@Bean
	public Docket api(){
	    return new Docket(DocumentationType.SWAGGER_2)
				.host("eney.co.kr:4005")
	    .select()
		    .apis(RequestHandlerSelectors.any())
		    .paths(springfox.documentation.builders.PathSelectors.regex("/apis/.*/.*/.*"))
		    .build()
	    .apiInfo(apiInfo())
	    .genericModelSubstitutes(ResponseEntity.class)
        .alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class,
                        typeResolver.resolve(ResponseEntity.class,
                                WildcardType.class)), typeResolver
                        .resolve(WildcardType.class)))
        .useDefaultResponseMessages(false)
	    .forCodeGeneration(true);
	}
	
	private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
        .title("eney API")
        .description("eney API 문서입니다.")
        .version("0.1")
        .build();
    }
}