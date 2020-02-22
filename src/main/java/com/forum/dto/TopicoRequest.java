package com.forum.dto;

import com.forum.model.Curso;
import com.forum.model.Topico;
import com.forum.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class TopicoRequest {

  @NotEmpty @NotEmpty @Length(min = 5)
  private String titulo;

  private String mensagem;

  private String nomeCurso;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public String getCurso() {
    return nomeCurso;
  }

  public void setCurso(String nomeCurso) {
    this.nomeCurso = nomeCurso;
  }

  public Topico converter(CursoRepository cursoRepository) {
    Curso curso = cursoRepository.findByNome(nomeCurso);
    return new Topico(titulo, mensagem, curso);
  }

}
