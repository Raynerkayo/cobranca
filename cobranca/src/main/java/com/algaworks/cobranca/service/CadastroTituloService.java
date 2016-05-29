package com.algaworks.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

/*
 * TODO: Classe de serviço, que deverá conter regras de negocio 
 * quando necessárias.
*/
@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo){
		try{
			this.titulos.save(titulo);
		} catch(DataIntegrityViolationException exception){
			throw new IllegalArgumentException("Formato inválido de data!");
		}
	}

	public void excluir(Long codigo) {
		// TODO Auto-generated method stub
		titulos.delete(codigo);
	}
	
	
	
}
