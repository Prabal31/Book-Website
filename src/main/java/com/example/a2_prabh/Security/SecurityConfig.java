package com.example.a2_prabh.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector
            introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        return http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvc.pattern("/secure/**")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern("/**")).permitAll()
                        .requestMatchers(mvc.pattern("/books")).permitAll()
                        .requestMatchers(mvc.pattern("/cart")).permitAll()
                        .requestMatchers(mvc.pattern("/Checkout")).permitAll()
                        .requestMatchers(mvc.pattern("/itemadded")).permitAll()
                        .requestMatchers(mvc.pattern("/details")).permitAll()
                        .requestMatchers(mvc.pattern("/addToCart/**")).permitAll()
                        .requestMatchers(mvc.pattern("/deleteCart/**")).permitAll()
                        .requestMatchers(mvc.pattern("/secure/bookdeleted")).permitAll()
                        .requestMatchers(mvc.pattern("/secure/deleteBookById/**")).permitAll()
                        .requestMatchers(mvc.pattern("/secure/editbook/**")).permitAll()
                        .requestMatchers(mvc.pattern("/secure/edit/**")).permitAll()
                        .requestMatchers(mvc.pattern("/secure/addbook")).permitAll()
                        .requestMatchers(mvc.pattern("/secure/bookadded")).permitAll()
                        .requestMatchers(mvc.pattern("/js/**")).permitAll()
                        .requestMatchers(mvc.pattern("/css/**")).permitAll()
                        .requestMatchers(mvc.pattern("/images/**")).permitAll()
                        .requestMatchers(mvc.pattern("/register")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/register")).permitAll()
                        .requestMatchers(mvc.pattern("/permission-denied")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(mvc.pattern("/**")).denyAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .formLogin(form -> form.loginPage("/login").permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/permission-denied"))
                .logout(logout -> logout.permitAll())
                .build();
    }


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
