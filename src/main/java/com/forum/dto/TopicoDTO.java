package com.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.forum.model.Topico;

public class TopicoDTO {

  private Long id;
  private String titulo;
  private String mendagem;
  private LocalDateTime dataCriacao;

  public TopicoDTO(Topico topico) {
    this.id = topico.getId();
    this.titulo = topico.getTitulo();
    this.mendagem = topico.getMensagem();
    this.dataCriacao = topico.getDataCriacao();
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getMendagem() {
    return mendagem;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public static List<TopicoDTO> converter(List<Topico> topicos) {
    return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList()); 
  }



  
  
}