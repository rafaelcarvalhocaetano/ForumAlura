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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
  @Cacheable(value = "listaDeTopicos")
  public Page<TopicoDTO> list(
      @RequestParam(required = false) @PathVariable("nomeCurso") String nomeCurso,
//      @RequestParam(required = true) int pagina,
//      @RequestParam(required = true) int qtd,
//      @RequestParam(required = true) String order
      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pag
      ) {
//    Pageable pag = PageRequest.of(pagina, qtd, Sort.Direction.ASC, order);
    if (nomeCurso == null) {
      Page<Topico> top = repository.findAll(pag);
      return TopicoDTO.converter(top);
    } else {
      return TopicoDTO.converter(repository.findByCursoNome(nomeCurso, pag));
    }
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
  @Transactional
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoRequest topico, UriComponentsBuilder uriBuilder) {
    Topico tpc = topico.converter(cursoRepository);
    repository.save(tpc);
    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(tpc.getId()).toUri();
    return ResponseEntity.created(uri).body(new TopicoDTO(tpc));
  }

  @PutMapping(value = "/{id}")
  @Transactional
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<TopicoDTO> atualizar(@PathVariable("id") Long id, @RequestBody @Valid TopicoRequestDTO form) {
    Optional<Topico> topico = repository.findById(id);
    if (topico.isPresent()) {
      Topico t = form.atualizar(id, repository);
      return ResponseEntity.ok(new TopicoDTO(t));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<?> remover(@PathVariable("id") Long id) {
    Optional<Topico> topico = repository.findById(id);
    if (topico.isPresent()) {
      repository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

   
}