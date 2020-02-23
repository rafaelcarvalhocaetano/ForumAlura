package com.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.forum.model.Topico;
import org.springframework.data.domain.Page;

public class TopicoDTO {

  private Long id;
  private String titulo;
  private String mensagem;
  private LocalDateTime dataCriacao;

  public TopicoDTO(Topico topico) {
    this.id = topico.getId();
    this.titulo = topico.getTitulo();
    this.mensagem = topico.getMensagem();
    this.dataCriacao = topico.getDataCriacao();
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getMensagem() {
    return mensagem;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public static Page<TopicoDTO> converter(Page<Topico> topicos) {
    return topicos.map(TopicoDTO::new);
//    return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
  }



  
  
}