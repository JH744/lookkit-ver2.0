package synerjs.lookkit2nd.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.oauth2.CustomSuccessHandler;
import synerjs.lookkit2nd.oauth2.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final WebConfig webConfig;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService ,
        CustomSuccessHandler customSuccessHandler, JwtUtil jwtUtil,WebConfig webConfig) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.webConfig=webConfig;
    }





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http  // CSRF 비활성화
            .csrf(csrf -> csrf.disable());


        http  // 세션 관리 비활성화 (Stateless)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );


        http  // SecurityContextRepository 비활성화
            .securityContext(securityContext ->
                securityContext.securityContextRepository(new NullSecurityContextRepository())
        );


        http  // JWTFilter 추가
            .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);



        http  // URL 접근 권한 설정
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**", "/auth/**", "/kakao-login", "/oauth2/**", "/product/**", "/common/**", "/main/**", "/error/**", "/fail/**", "/mailsender").permitAll()
                .requestMatchers("/cart", "/mypage").authenticated()
                .anyRequest().authenticated()
        );


        http // 기본 폼 로그인 비활성화, CORS 필터 추가
            .formLogin(formLogin -> formLogin.disable())
                .addFilter(webConfig.corsFilter());



        http //oauth2 설정
            .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                    .userService(customOAuth2UserService))
                    .successHandler(customSuccessHandler));



        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/*.html", "/html/**");
    }
}