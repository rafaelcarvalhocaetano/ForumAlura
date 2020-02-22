package com.forum.controller;

import java.net.URI;
import java.util.Optional;

import com.forum.dto.DetalhesTopicoDTO;
import com.forum.dto.TopicoDTO;
import com.forum.dto.TopicoRequest;
import com.forum.dto.TopicoRequestDTO;
import com.forum.model.Topico;
import com.forum.repository.CursoRepository;
import com.forum.repository.TopicoRespository;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping(value = "/{id}")
  public ResponseEntity<DetalhesTopicoDTO> findById(@PathVariable("id") Long id) {
    Optional<Topico> topico = repository.findById(id);
    if (topico.isPresent()) {
      return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoRequest topico, UriComponentsBuilder uriBuilder) {
    Topico tpc = topico.converter(cursoRepository);
    repository.save(tpc);
    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(tpc.getId()).toUri();
    return ResponseEntity.created(uri).body(new TopicoDTO(tpc));
  }

  @PutMapping(value = "/{id}")
  @Transactional
  public ResponseEntity<TopicoDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid TopicoRequestDTO form) {
    Optional<Topico> topico = repository.findById(id);
    if (topico.isPresent()) {
      Topico t = form.atualizar(id, repository);
      return ResponseEntity.ok(new TopicoDTO(t));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> remover(@PathVariable("id") Long id) {
    Optional<Topico> topico = repository.findById(id);
    if (topico.isPresent()) {
      repository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

   
}