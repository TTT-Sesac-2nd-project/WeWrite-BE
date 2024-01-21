package site.hyundai.wewrite.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer{
    @Bean
    public Docket api() {

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization")
                .description("token")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
                .build();

        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(parameterList)
                .select().apis(RequestHandlerSelectors.any())

                .paths(PathSelectors.any())
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("Bearer +accessToken", "Authorization", "header");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
