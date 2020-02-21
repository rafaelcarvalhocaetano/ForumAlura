package com.forum.dto;

import com.forum.model.Curso;
import com.forum.model.Topico;
import com.forum.repository.CursoRepository;

public class TopicoRequest {

  private String titulo;
  private String mendagem;
  private String nomeCurso;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getMendagem() {
    return mendagem;
  }

  public void setMendagem(String mendagem) {
    this.mendagem = mendagem;
  }

  public String getCurso() {
    return nomeCurso;
  }

  public void setCurso(String nomeCurso) {
    this.nomeCurso = nomeCurso;
  }

  public Topico converter(CursoRepository cursoRepository) {
    Curso curso = cursoRepository.finByNome(nomeCurso);
    return new Topico(titulo, mendagem, curso);
  }

}
