package synerjs.lookkit2nd.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private WebConfig webConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());

        // 세션 관리 정책 설정
        http.sessionManagement((session) -> session
                // 세션 생성 및 상태 유지 OFF, Stateless 방식 설정
                // JWT 기반 인증 ->  서버가 클라이언트 세션 유지않도록 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        // jwt 필터 추가
        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);

        http.authorizeHttpRequests((authorize) ->
             // authorize.requestMatchers("/**").permitAll() // 모든 페이지 허용
                authorize.requestMatchers("/admin/**").hasRole("ADMIN") // 관리자페이지 "ADMIN" 권한 필요
                         .requestMatchers( "/api/**", "/auth/**", "/kakao-login", "/oauth2/**", "/product/**", "/common/**", "/main/**", "/error/**", "/fail/**", "/mailsender").permitAll() // 모두에게 허용되도록 설정
                         .requestMatchers("/cart", "/mypage").authenticated()
                         .anyRequest().authenticated() // 그 외의 모든 요청도 인증된 사용자만 접근 가능하도록 설정
        );


        http.formLogin((formLogin) -> formLogin.disable())   // 기본 제공되는 폼 기반 로그인 방식 비활성화
                .addFilter(webConfig.corsFilter());          // corsFilter 추가

        http.oauth2Login(oauth2 ->oauth2.failureUrl("/login?error=true"));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                // 정적 리소스에 대해 시큐리티 필터 제외 설정(정적 리소스에서 HTML은 미포함)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                // 특정 HTML 파일 및 HTML 디렉토리 내부 파일에 대해 인증 및 권한 검사 무시
                .requestMatchers("/*.html", "/html/**");
    }


}