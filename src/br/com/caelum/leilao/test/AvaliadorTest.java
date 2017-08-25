package br.com.caelum.leilao.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Avaliador;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}

	@After
	public void finaliza() {
		System.out.println("fim");
	}

	@BeforeClass
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}

	@Test
	public void lancesEmOrdemCrescente() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 250)
	            .lance(jose, 300)
	            .lance(maria, 400)
	            .constroi();

	        leiloeiro.avalia(leilao);

	        assertThat(leiloeiro.getMenorDeTodos(), equalTo(250.0));
	        assertThat(leiloeiro.getMaiorDeTodos(), equalTo(400.0));
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new CriadorDeLeilao()
		        .para("Playstation 3 Novo")
		        .lance(joao, 1000)
		        .constroi();

		leiloeiro.avalia(leilao);

		// veja que não precisamos mais da palavra Assert!
//		assertEquals(1000, leiloeiro.getMaiorDeTodos(), 0.0001);
//		assertEquals(1000, leiloeiro.getMenorDeTodos(), 0.0001);
		
		 assertThat(leiloeiro.getMenorDeTodos(), equalTo(leiloeiro.getMaiorDeTodos()));
	}

	@Test
	public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
		Usuario joao = new Usuario("Joao");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 450.0));
		leilao.propoe(new Lance(joao, 120.0));
		leilao.propoe(new Lance(maria, 700.0));
		leilao.propoe(new Lance(joao, 630.0));
		leilao.propoe(new Lance(maria, 230.0));

		leiloeiro.avalia(leilao);

		assertEquals(700.0, leiloeiro.getMaiorDeTodos(), 0.0001);
		assertEquals(120.0, leiloeiro.getMenorDeTodos(), 0.0001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 100)
	            .lance(maria, 200)
	            .lance(joao, 300)
	            .lance(maria, 400)
	            .constroi();

	        leiloeiro.avalia(leilao);

	        List<Lance> maiores = leiloeiro.getTresMaiores();
	        assertEquals(3, maiores.size());

	        assertThat(maiores, hasItems(
	                new Lance(maria, 400), 
	                new Lance(joao, 300),
	                new Lance(maria, 200)
	        ));  
		
	}

	@Test
	public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
		Leilao leilao = new CriadorDeLeilao().para("PlayStation 4").lance(joao, 100).lance(maria, 200).lance(jose, 400)
				.constroi();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(2, maiores.size());
		assertEquals(400, maiores.get(0).getValor(), 0.00001);
		assertEquals(200, maiores.get(1).getValor(), 0.00001);
	}

	@Test
	public void deveDevolverListaVaziaCasoNaoHajaLances() {
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(0, maiores.size());
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("PlayStation").constroi();
		leiloeiro.avalia(leilao);
	}

}
