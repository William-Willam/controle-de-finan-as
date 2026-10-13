package com.williamsantos.controlefinancas.repository;

import com.williamsantos.controlefinancas.model.Categoria;
import com.williamsantos.controlefinancas.model.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuarioId(Long usuarioId);

    List<Categoria> findByUsuarioIdAndTipo(Long usuarioId, TipoCategoria tipo);
}