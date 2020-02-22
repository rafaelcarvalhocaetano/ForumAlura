package com.forum.controller;

import java.net.URI;

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

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/topicos")
public class TopicoController {

  @Autowired
  private TopicoRespository repository;

  @Autowired
  private CursoRepository cursoRepository;

  @GetMapping
  public Iterable<TopicoDTO> listAll() {
    return TopicoDTO.converter(repository.findAll());
  }

  @GetMapping(value = "/{nomeCurso}")
  public Iterable<TopicoDTO> list(@PathVariable("nomeCurso") String nomeCurso) {
    return TopicoDTO.converter(repository.findByCursoNome(nomeCurso));
  }

  @PostMapping
  public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoRequest topico, UriComponentsBuilder uriBuilder) {
    Topico tpc = topico.converter(cursoRepository);
    repository.save(tpc);
    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(tpc.getId()).toUri();
    return ResponseEntity.created(uri).body(new TopicoDTO(tpc));
  }

   
}