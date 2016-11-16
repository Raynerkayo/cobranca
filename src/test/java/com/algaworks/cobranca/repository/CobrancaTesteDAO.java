package com.algaworks.cobranca.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.algaworks.cobranca.CobrancaApplication;
import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.service.CadastroTituloService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CobrancaApplication.class)
@WebIntegrationTest
public class CobrancaTesteDAO {

	@Autowired
	private CadastroTituloService service;
	
	@Test
	public void salvarUmaNovaCobranca() {
		
		//salvar novo título
		Titulo novoTitulo = new Titulo();
		novoTitulo.setDataVencimento(new Date());
		novoTitulo.setDescricao("M Jakeline S S");
		novoTitulo.setStatus(StatusTitulo.PENDENTE);
		novoTitulo.setValor(new BigDecimal("52.00"));
		service.salvar(novoTitulo);

		//agora encontrar este título pela descrição
		Titulo titulo = service.buscarPorDescricao("Jakeline");
		System.out.println("AAAAAAAAA" + new Date());
		
		assertEquals(new Date(), titulo.getDataVencimento());
		assertEquals("M Jakeline S S", titulo.getDescricao());
		assertEquals("PENDENTE", titulo.getStatus().getDescricao());
		assertEquals("52.00", titulo.getValor());
		
		
	}

}
