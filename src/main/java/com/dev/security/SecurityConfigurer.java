package com.dev.security;


import com.dev.filter.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.dev.constants.SecurityConfigConstants.*;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer {

	@Value("${CORS.AllowedOriginPatterns}")
	 private String[] AllowedOriginPatterns;

	 @Value("${CORS.AllowedHeaders}")
	 private String[] AllowedHeaders;

	 @Value("${CORS.AllowedMethods}")
	 private String[] AllowedMethods;
	 
    private final JwtRequestFilter jwtRequestFilter;
    public SecurityConfigurer(JwtRequestFilter jwtRequestFilter){
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(
                        AbstractHttpConfigurer::disable
                        )
                .authorizeHttpRequests(
                        (auth)-> auth
                                .antMatchers(CUSTOMER_REGISTER_URL
                                ).permitAll()
                                .anyRequest()
                                .authenticated()
                        )
                .exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new CorsFilter(corsConfigurationSource()), AbstractPreAuthenticatedProcessingFilter.class);
        return httpSecurity.build();
    }
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(AllowedOriginPatterns));
        configuration.setAllowedMethods(Arrays.asList(AllowedMethods));
        configuration.setMaxAge(10L);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(AllowedHeaders));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

