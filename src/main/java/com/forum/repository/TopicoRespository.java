package com.forum.repository;

import java.util.List;

import com.forum.model.Topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRespository extends JpaRepository<Topico, Long> {

	List<Topico> findByCursoNome(String nomeCurso);

  
  
}