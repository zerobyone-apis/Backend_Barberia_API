package zero.our.piece.barbers.barbers_api._configuration
/*@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    @Bean
    ApiInfo apiInfo() {
        final ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("Art Experience API").version("1.0").license("ZeroByOne")
                .description("List of all endpoints used in API")
                .build();
        return builder.build();
    }

    //FIXME: Se debe implementar seguridad y restricciones de los endpoint del backend
    //       Autenticarlos y protegerlos.

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);

    return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(("com.art.experience.dev.web")))
                .paths(Predicates.or(PathSelectors.regex("/*"), PathSelectors.regex("/*")))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }



}
*/
