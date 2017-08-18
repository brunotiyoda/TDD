package br.com.caelum.leilao.teste;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Avaliador;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTeste {

	@Test
	public void lancesEmOrdemCrescente() {
		
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("PlayStation 4");
		leilao.propoe(new Lance(joao, 400));
		leilao.propoe(new Lance(jose, 300));
		leilao.propoe(new Lance(maria, 250));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		double maiorEsperado = 400;
		double menorEsperado = 250;
		
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorDeTodos(), 0.0001);
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorDeTodos(), 0.0001);
		
	}
}
