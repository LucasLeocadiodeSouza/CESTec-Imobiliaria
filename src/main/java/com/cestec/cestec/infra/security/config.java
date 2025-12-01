package com.cestec.cestec.infra.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class config {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .authorizeHttpRequests(authorize -> authorize
                                                     .requestMatchers("/").permitAll()
                                                     .requestMatchers("/impressao/**").permitAll()
                                                     .requestMatchers(HttpMethod.GET, "/impressao").permitAll()
                                                     .requestMatchers(HttpMethod.POST, "/impressao").permitAll()
                                                     .requestMatchers(HttpMethod.HEAD, "/impressao").permitAll()
                                                     .requestMatchers(HttpMethod.OPTIONS, "/impressao").permitAll()
                                                     .requestMatchers(HttpMethod.POST, "/webhook", "/impressao").permitAll()
                                                     .requestMatchers(HttpMethod.GET, "/login").permitAll()
                                                     .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                                     .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
                                                     .requestMatchers(HttpMethod.GET, "/icons/**", "/css/**", "/js/**").permitAll()
                                                     .requestMatchers(HttpMethod.GET,"/contratosCadastroPropri","/contratosCadastroClientes").hasRole("ADMIN")
                                                     .requestMatchers(HttpMethod.GET, "/contratosCadastro","/contratosCadastroContrato").hasRole("SALER")
                                                     .anyRequest().hasAnyRole("USER") 
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
            return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
