package com.forum.dto;

import com.forum.model.Topico;
import com.forum.repository.TopicoRespository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class TopicoRequestDTO {

  @NotEmpty
  @NotEmpty @Length(min = 5)
  private String titulo;

  private String mensagem;

  public String getTitulo() {
    return titulo;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public Topico atualizar(Long id, TopicoRespository repository) {
    Topico t = repository.getOne(id);
    t.setTitulo(this.titulo);
    t.setMensagem(this.mensagem);
    return t;
  }
}
