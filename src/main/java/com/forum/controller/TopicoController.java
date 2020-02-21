package com.forum.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.forum.dto.TopicoDTO;
import com.forum.dto.TopicoRequest;
import com.forum.model.Topico;
import com.forum.repository.CursoRepository;
import com.forum.repository.TopicoRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/topico")
public class TopicoController {

  @Autowired
  private TopicoRespository repository;

  @Autowired
  private CursoRepository cursoRepository;

  @GetMapping(value = "/{nomeCurso}")
  public List<TopicoDTO> list(@PathVariable("nomeCurso") final String nomeCurso) {
    List<Topico> topicos = new ArrayList<>();
    if(nomeCurso == null) {
      topicos = repository.findAll();
      return TopicoDTO.converter(topicos);
    } else {
      topicos = repository.findByCursoNome(nomeCurso);
      return TopicoDTO.converter(topicos);
    }
  }

  @PostMapping
  public ResponseEntity<TopicoDTO> cadastrar(@RequestBody final TopicoRequest topico, final UriComponentsBuilder uriBuilder) {
    Topico tpc = topico.converter(cursoRepository);
    repository.save(tpc);
    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(tpc.getId()).toUri();
    return ResponseEntity.created(uri).body(new TopicoDTO(tpc));
  }

   
}