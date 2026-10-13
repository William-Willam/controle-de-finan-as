package com.williamsantos.controlefinancas.repository;

import com.williamsantos.controlefinancas.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findByUsuarioId(Long usuarioId);
}