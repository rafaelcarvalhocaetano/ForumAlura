package com.forum.repository;

import com.forum.model.Curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

  Curso findByNome(String nomeCurso);
}
