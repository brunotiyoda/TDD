package br.com.caelum.leilao.dominio;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	double media = 0;

	public double getMaiorDeTodos() {
		return maiorDeTodos;
	}

	public double getMenorDeTodos() {
		return menorDeTodos;
	}

	public void avalia(Leilao l) {
		double total = 0;
		for (Lance lance : l.getLances()) {
			if (lance.getValor() > maiorDeTodos) {
				maiorDeTodos = lance.getValor();
			}
			if (lance.getValor() < menorDeTodos) {
				menorDeTodos = lance.getValor();
			}
			total = +lance.getValor();
		}
		media = total / l.getLances().size();
	}

	public double getMedia() {
		return media;
	}

}
