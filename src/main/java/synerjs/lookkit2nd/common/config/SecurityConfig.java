package synerjs.lookkit2nd.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import synerjs.lookkit2nd.oauth2.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private WebConfig webConfig;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 비활성화
        http.csrf(csrf -> csrf.disable());

        // 세션 관리 비활성화 (Stateless)
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // SecurityContextRepository 비활성화
        http.securityContext(securityContext ->
                securityContext.securityContextRepository(new NullSecurityContextRepository())
        );

        // JWT 필터 등록
        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);

        // URL 접근 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**", "/auth/**", "/kakao-login", "/oauth2/**", "/product/**", "/common/**", "/main/**", "/error/**", "/fail/**", "/mailsender").permitAll()
                .requestMatchers("/cart", "/mypage").authenticated()
                .anyRequest().authenticated()
        );

        // 기본 폼 로그인 비활성화, CORS 필터 추가
        http.formLogin(formLogin -> formLogin.disable())
                .addFilter(webConfig.corsFilter());

        // OAuth2 소셜 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login") // 별도 로그인 페이지 필요시 설정
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler((request, response, authentication) -> {
                    // SecurityContext에 인증 정보를 저장하지 않음
                    SecurityContextHolder.clearContext();
                    // 성공 후 리다이렉트 경로
                    response.sendRedirect("/login-success");
                })
                .failureUrl("/login?error=true") // 인증 실패 시 이동 경로
        );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/*.html", "/html/**");
    }
}