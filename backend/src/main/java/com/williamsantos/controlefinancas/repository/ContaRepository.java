package com.williamsantos.controlefinancas.repository;

import com.williamsantos.controlefinancas.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByUsuarioId(Long usuarioId);
}