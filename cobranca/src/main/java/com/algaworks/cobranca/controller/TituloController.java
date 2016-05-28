	package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	//Para criar uma instancia de Titulos
	@Autowired
	private Titulos titulos;

	//new Titulo, é para passar o objeto para a view
	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView modelAndView = new ModelAndView("CadastroTitulo");
		modelAndView.addObject(new Titulo());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors){
		ModelAndView modelAndView = new ModelAndView("CadastroTitulo");
		
		if(errors.hasErrors()){
			return modelAndView;
		}
		
		titulos.save(titulo);
		modelAndView.addObject("mensagem", "Título salvo com sucesso!");
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView pesquisar(){
		List<Titulo> todosTitulos = titulos.findAll();
		ModelAndView modelAndView = new ModelAndView("PesquisaTitulo");
		modelAndView.addObject("titulos", todosTitulos);
		return modelAndView;
	}
	
	//para listar os status do enum, dinamicamente.
	//essa string é que deve ser chamada na view
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulos(){
		return Arrays.asList(StatusTitulo.values());
	}
	
	
}



