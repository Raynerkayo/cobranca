package com.algaworks.cobranca.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.algaworks.cobranca.CobrancaApplication;
import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.CadastroTituloService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CobrancaApplication.class)
@WebIntegrationTest
@Transactional//serve para não alterar o banco de dados a cada execução. Faz um rolback no banco.
public class CobrancaTesteDAO {

	
	@Autowired
	private CadastroTituloService service;
	
	@Test
	public void salvarUmaNovaCobranca() {

		//salvar novo título
		Titulo novoTitulo = new Titulo();
		novoTitulo.setDataVencimento(new Date());
		novoTitulo.setDescricao("Jacqueline A");
		novoTitulo.setStatus(StatusTitulo.PENDENTE);
		novoTitulo.setValor(new BigDecimal("52.00"));
		service.salvar(novoTitulo);

		//agora encontrar este título pela descrição
		List<Titulo> titulos = service.buscarPorDescricao("Jacqueline A");
		for(Titulo titulo : titulos){
			assertEquals("Jacqueline A", titulo.getDescricao());
			assertEquals("Pendente", titulo.getStatus().getDescricao());
			assertEquals(new BigDecimal("52.00"), titulo.getValor());
		}
		
	}
	
	@Test
	public void retonarNullCasoNaoEncontreDescricaoDeCobranca(){
		List<Titulo> vazio = new ArrayList<>();
		TituloFilter filter = new TituloFilter();
		filter.setDescricao("Joana");
		List<Titulo> titulo = service.filtrar(filter);
		assertEquals(vazio, titulo);
	}
	
	//A descrição de uma conta pode ser o nome de uma pessoa
	@Test
	public void retornarQuantidadeDeCobrancasAbertasOuFechadasDeUmaDescricao(){
		TituloFilter filter = new TituloFilter();
		filter.setDescricao("Jacqueline");
		List<Titulo> titulos = service.filtrar(filter);
		int esperado = 1;
		assertEquals(esperado, titulos.size());
	}
	
	@Test
	public void atualizarDePendenteParaRecebido(){

		Titulo novoTitulo = new Titulo();
		novoTitulo.setCodigo(42L);
		service.receber(42L);
		
		Titulo titulo = service.buscarPorId(42L);
		
		assertEquals("Jacqueline", titulo.getDescricao());
		assertEquals("Recebido", titulo.getStatus().getDescricao());
		assertEquals(new BigDecimal("52.00"), titulo.getValor());
	}
	
	@Test
	public void deletarUmaCobranca(){
		service.excluir(42L);
		Titulo titulo = service.buscarPorId(42L);
		assertEquals(null, titulo);
	}

}







