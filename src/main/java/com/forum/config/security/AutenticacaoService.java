package com.forum.config.security;

import com.forum.model.Usuario;
import com.forum.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

  @Autowired
  private UserRespository userRespository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<Usuario> user = userRespository.findByEmail(s);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UsernameNotFoundException("Dados invalidos !!!!!");
  }
}
