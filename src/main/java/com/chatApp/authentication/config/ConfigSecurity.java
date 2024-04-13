package com.chatApp.authentication.config;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter  {
    @Autowired
    UserDetailedService userDetailedService;
    @Autowired
    JwtFilterForReq jwtFilterForReq;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/registeruser","/user/login","/user/authentincate")// Public endpoints
                .permitAll() .antMatchers("/user/authentincate").hasAuthority("USER")
                // .anyRequest().authenticated().and().httpBasic();

                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(jwtFilterForReq, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        // return new BCryptPasswordEncoder(20);
        // return  NoOpPasswordEncoder.getInstance();
        return  new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  auth.inMemoryAuthentication().withUser("moin").password(this.passwordEncoder().encode("momin")).roles("normal");
        auth.userDetailsService(userDetailedService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
