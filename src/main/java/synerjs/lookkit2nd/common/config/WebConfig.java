package synerjs.lookkit2nd.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${DOMAIN_URI}")
    private String domainUri;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 도메인과 퍼블릭 ip Cors 허용
        config.addAllowedOrigin("http://www.lookkit.store:8081");
        config.addAllowedOrigin("http://www.lookkit.store");
        config.addAllowedOrigin("http://lookkit.store");
        config.addAllowedOrigin("http://localhost:8081/");
        config.addAllowedOrigin(domainUri);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(3600)  // 캐싱 1시간
            .resourceChain(true)
            .addResolver(new VersionResourceResolver()
                .addContentVersionStrategy("/**"));
    }


}
