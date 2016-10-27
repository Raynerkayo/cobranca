package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;

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
		titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter tituloFilter){
		String descricao = tituloFilter.getDescricao() == null ? "%" : tituloFilter.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
	
	
	
}
