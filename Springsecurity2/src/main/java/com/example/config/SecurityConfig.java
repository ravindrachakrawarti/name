package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		
		return new BCryptPasswordEncoder();
	}
  
	
	@Bean
	public UserDetailsService getdetDetailsService()
	{
		
		return new CustomUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		
		
		daoAuthenticationProvider.setUserDetailsService(this.getdetDetailsService());
		
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	
	{
		http.authorizeHttpRequests(authz->authz.requestMatchers("/user/**")
                .hasRole("USER").requestMatchers("/admin/**")
                .hasRole("ADMIN").requestMatchers("/**").permitAll())
		.formLogin(form->form.loginPage("/signin").loginProcessingUrl("/userlogin").defaultSuccessUrl("/user/profile"))
		.csrf(csrf->csrf.disable());
		
		
		   http.authenticationProvider(authenticationProvider());  
		
		return http.build();
	}
	
	
}
