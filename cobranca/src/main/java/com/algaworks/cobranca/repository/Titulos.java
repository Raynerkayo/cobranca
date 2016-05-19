package com.algaworks.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.cobranca.model.Titulo;

//repositório para fazer a persistencia dos dados, 
//aqui estamos usando o spring dataJpa. Um repositório genérico.
//<classe, tipoDeDadoDoCódigoPK>
public interface Titulos extends JpaRepository<Titulo, Long> {
	
}
