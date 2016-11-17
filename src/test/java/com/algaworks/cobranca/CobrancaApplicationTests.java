package com.algaworks.cobranca;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.CadastroTituloService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CobrancaApplication.class)
@WebAppConfiguration
@Transactional//serve para não alterar o banco de dados a cada execução. Faz um rolback no banco.
public class CobrancaApplicationTests {

	@Autowired
	private CadastroTituloService service;
	
	@Test
	public void contextLoads() {
	}
	
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
	
	//Este teste é para verificar se um determinado título está como recebido 
	@Test
	public void verificarPagamentoPorTitulo(){
		Titulo titulo = service.buscarPorId(12L);
		assertEquals("Emprestimo João", titulo.getDescricao());
		assertEquals("Recebido", titulo.getStatus().getDescricao());
		assertEquals(new BigDecimal("100.00"), titulo.getValor());
	}
	
	//Este teste é para verificar se um determinado título não está como pendente
	@Test
	public void verificarDebitoPorTitulo(){
		Titulo titulo = service.buscarPorId(42L);
		assertEquals("Jacqueline", titulo.getDescricao());
		assertEquals("Pendente", titulo.getStatus().getDescricao());
		assertEquals(new BigDecimal("52.00"), titulo.getValor());
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
