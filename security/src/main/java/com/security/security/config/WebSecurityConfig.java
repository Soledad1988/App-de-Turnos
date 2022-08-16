
package com.security.security.config;

import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        //.httpBasic(withDefaults())  // (1)
        .httpBasic(withDefaults()) // (2)
        .authorizeRequests().anyRequest().authenticated();
        
  }

}
