package com.mobile.search.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.mobile.search.handler.CustomAccessDeniedHandler;
import com.mobile.search.security.JwtAuthenticationEntryPoint;
import com.mobile.search.security.JwtSecurityConfigurer;
import com.mobile.search.security.JwtTokenProvider;


/**
 * This class is security configuration class.
 * 
 * @author Suresh Thakare
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  UserDetailsService customUserDetailsService;


  @Autowired
  JwtAuthenticationEntryPoint unauthorizedHandler;


  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowedHeaders("*");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().httpBasic().disable().csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
            "/**/*.html", "/**/*.css", "/**/*.js", "/v2/api-docs",
            "/swagger-resources/configuration/ui", "/swagger-resources",
            "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**")
        .permitAll().antMatchers("/mobile/auth/**").permitAll()
        .anyRequest().authenticated()
        .and().exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    // Add our custom JWT security filter
  }
}
