package com.forum.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginDTO {

  private String email;
  private String senha;

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getEmail() {
    return email;
  }

  public String getSenha() {
    return senha;
  }

  public UsernamePasswordAuthenticationToken converte() {
    return new UsernamePasswordAuthenticationToken(email, senha);
  }
}
