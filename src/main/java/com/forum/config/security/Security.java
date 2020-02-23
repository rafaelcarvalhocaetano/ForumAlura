package com.forum.config.security;

import com.forum.config.jwt.TokenService;
import com.forum.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configurable
public class Security extends WebSecurityConfigurerAdapter {

  @Autowired
  private AutenticacaoService autenticacaoService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRespository useRepository;

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  // CONFIG OF AUTHENTICATION
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
  }

  // CONFIG OF AUTORIZATION
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/topicos").permitAll()
//        .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
        .antMatchers(HttpMethod.POST, "/auth").permitAll()
        .anyRequest().authenticated()
        .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(new AutenticacaoTokenFilter(tokenService, useRepository), UsernamePasswordAuthenticationFilter.class);
//        .and().formLogin();
  }

  // CONFIG OF REC(JS, CSS, IMG, ETC)
  // LIBERANDO SWAGGER
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/application.properties", "/resources/img", "/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
  }

  // 155443c0-07a1-4534-9e32-9dadb07fe9fd
  // $2a$10$GxrfKXyHU70mlgBO4f3kQ.YpMaer/Ot8yIydmsnYP4XD2b9ZhZ0yq
  // eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgRk9SVU0gUkFGQUVMIiwic3ViIjoiMSIsImlhdCI6MTU4MjQyNjk1OCwiZXhwIjoxNTgyNTEzMzU4fQ.7kf0YxwYpZ0CrLnnUdBzdZE-augHzuhliZAkiLMqxY0

}
