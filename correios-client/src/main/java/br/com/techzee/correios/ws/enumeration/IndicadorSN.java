package br.com.techzee.correios.ws.enumeration;

/**
 * Indicador SIM | NAO utilizado nos retornos do servi\u00e7o do Correios. Como
 * n\u00e3o h\u00e1 um padr\u00e3o (as vezes mai\u00fasculo outras
 * min\u00fasculo) utilizei como refer\u00eancia o min\u00fasculo.
 *
 * @author felipe.caparelli
 */
public enum IndicadorSN {

	SIM('s', "Sim"),
	NAO('n', "N\u00e3o");

	private char id;
	private String nome;

	IndicadorSN(char id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public char getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public static IndicadorSN getById(String id) {
		if(id == null || id.trim().isEmpty()) return null;
		char idC = id.toLowerCase().charAt(0);
		for (IndicadorSN ind : IndicadorSN.values()) {
			if(ind.id == idC) return ind;
		}
		return null;
	}



}
