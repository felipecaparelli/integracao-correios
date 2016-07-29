package br.com.techzee.correios.ws.enumeration;

/**
 * Enum que representa os Tipos de Servi&ccedil;o oferecidos pelo Correios, utilizados para comunica&ccedil;&atilde;o com o web service do c&aacute;lculo de frete.
 *
 * Segue tabela com os C&oacute;digos de Servi&ccedil;o obtidos no site dos Correios:
 *
 *<ul>
 *	<li>40010, 	SEDEX Varejo</li>
 *	<li>40045, 	SEDEX a Cobrar Varejo</li>
 *	<li>40215, 	SEDEX 10 Varejo</li>
 *	<li>40290, 	SEDEX Hoje Varejo</li>
 *	<li>41106, 	PAC Varejo</li>
 *</ul>
 *
 * @author felipe.caparelli
 *
 */
public enum CorreiosTipoServico {

	SEDEX_VAREJO(40010, "SEDEX Varejo"),
	SEDEX_A_COBRAR_VAREJO(40045, "SEDEX a Cobrar Varejo"),
	SEDEX_10_VAREJO(40215, "SEDEX 10 Varejo"),
	SEDEX_HOJE_VAREJO(40290, "SEDEX Hoje Varejo"),
	PAC_VAREJO(41106, "PAC Varejo");

	private int codigo;
	private String nome;

	CorreiosTipoServico(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public static CorreiosTipoServico getByCodigo(Integer codigo) {
		for (CorreiosTipoServico tipoServico : CorreiosTipoServico.values()) {
			if(tipoServico.codigo == codigo) return tipoServico;
		}
		return null;
	}

}
