package com.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configurable
public class Security extends WebSecurityConfigurerAdapter {

  @Autowired
  private AutenticacaoService autenticacaoService;

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
        .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
        .anyRequest().authenticated()
        .and().formLogin();
  }

  // CONFIG OF REC(JS, CSS, IMG, ETC)
  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }

  // 155443c0-07a1-4534-9e32-9dadb07fe9fd
  // $2a$10$GxrfKXyHU70mlgBO4f3kQ.YpMaer/Ot8yIydmsnYP4XD2b9ZhZ0yq

}
