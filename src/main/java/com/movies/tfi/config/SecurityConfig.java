package com.movies.tfi.config;

import com.movies.tfi.security.JwtAuthenticationEntryPoint;
import com.movies.tfi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private JwtAuthenticationFilter authenticationFilter;
    private CorsConfig corsConfig;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
                          UserDetailsService userDetailsService,
                          JwtAuthenticationFilter authenticationFilter,
                          CorsConfig corsConfig){
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.corsConfig = corsConfig;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        //Here the user details and the password encoder are directly handled by the spring not need to pass explicitly
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize
//                            .requestMatchers("/tfi/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/tfi/records/**").permitAll()
//                            .requestMatchers(HttpMethod.POST,"tfi/**").permitAll()
                            .anyRequest().authenticated();
                })
                .cors(c -> c.configurationSource(corsConfig))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}
