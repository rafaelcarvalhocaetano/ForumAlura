package com.forum.config.security;

import com.forum.config.jwt.TokenService;
import com.forum.model.Usuario;
import com.forum.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {


  private TokenService tokenService;
  private UserRespository userRespository;

  public AutenticacaoTokenFilter(
      TokenService tokenService,
      UserRespository userRespository) {
    this.tokenService = tokenService;
    this.userRespository = userRespository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
    
    String token = recuperarToken(request);
    boolean valido = tokenService.isTokenValido(token);

    if (valido) {
      autenticarCliente(token);
    }
    filter.doFilter(request, response);
  }

  private void autenticarCliente(String token) {
    Long idUsuario = tokenService.getIdUsuario(token);
    Usuario user = userRespository.findById(idUsuario).get();
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

  private String recuperarToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }
    return token.substring(7, token.length());
  }
}
