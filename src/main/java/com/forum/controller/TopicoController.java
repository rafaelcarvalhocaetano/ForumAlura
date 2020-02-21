package com.forum.controller;

import java.util.List;

import com.forum.dto.TopicoDTO;
import com.forum.model.Topico;
import com.forum.repository.TopicoRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/topico")
public class TopicoController {

  @Autowired
  private TopicoRespository repository;

  @GetMapping(value = "/{nomeCurso}")
  public List<TopicoDTO> list(@PathVariable("nomeCurso") String nomeCurso) {
    if (nomeCurso == null) {
      List<Topico> topicos = repository.findAll();
      return TopicoDTO.converter(topicos);
    } else {
      List<Topico> topicos = repository.findByCursoNome(nomeCurso);
      return TopicoDTO.converter(topicos);
    }
  }
}