package com.forum.controller;

import com.forum.config.jwt.TokenService;
import com.forum.dto.LoginDTO;
import com.forum.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AutenticacaoController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenDTO> autenticacao(@RequestBody @Valid LoginDTO auth) {
    System.out.println("EMAIL: " + auth.getEmail() + " SENHA: " + auth.getSenha());
    UsernamePasswordAuthenticationToken login = auth.converte();

    try {
      Authentication authentication = authenticationManager.authenticate(login);
      String token = tokenService.gerarToken(authentication);
      return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
    } catch (AuthenticationException authExp) {
      return ResponseEntity.badRequest().build();
    }

  }
}
