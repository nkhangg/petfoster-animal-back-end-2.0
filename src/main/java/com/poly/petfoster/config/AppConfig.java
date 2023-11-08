package com.poly.petfoster.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
    
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }

        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeHttpRequests(Authorize -> 
            Authorize
                    .antMatchers("/api/admin/authorize").hasRole("SUPER_ADMIN")
                    .antMatchers("/api/admin/product/update-product").hasRole("SUPER_ADMIN, ADMIN")
                    .antMatchers("/api/admin/product/delete-product").hasRole("SUPER_ADMIN, ADMIN")
                    .antMatchers("/api/admin/**").hasAnyRole("SUPER_ADMIN, ADMIN, STAFF")
                    .antMatchers("/api/user/**").authenticated()
                    .anyRequest().permitAll())
        .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
        .csrf().disable()
        .httpBasic().and().formLogin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
