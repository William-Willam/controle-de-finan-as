package com.williamsantos.controlefinancas.repository;

import com.williamsantos.controlefinancas.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    List<Orcamento> findByUsuarioIdAndMesReferencia(Long usuarioId, LocalDate mesReferencia);
}