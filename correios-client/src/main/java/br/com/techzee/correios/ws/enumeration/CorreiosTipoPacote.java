package br.com.techzee.correios.ws.enumeration;

/**
 * Representa os tipos de pacote aceitos como parametro para a consulta ao
 * servi&ccedil;o dos Correios.
 *
 * <ul>
 * <li>1 – Formato caixa/pacote</li>
 * <li>2 – Formato rolo/prisma</li>
 * <li>3 - Envelope</li>
 * </ul>
 *
 * @author felipe.caparelli
 *
 */
public enum CorreiosTipoPacote {

	//cada formato possui parametros default (minimos) para a execucao das chamadas
	CAIXA_PACOTE(1, "Formato caixa/pacote", "1", "16", "2", "11", "1"),
	ROLO_PRISMA(2, "Formato rolo/prisma", "1", "16", "2", "11", "1"),
	ENVELOPE(3, "Envelope", "1", "16", "2", "11", "1");

	private int codigo;
	private String nome;
	private String peso;
	private String comprimento;
	private String altura;
	private String largura;
	private String diametro;

	private CorreiosTipoPacote(int codigo, String nome, String peso, String comprimento, String altura, String largura,
			String diametro) {
		this.codigo = codigo;
		this.nome = nome;
		this.peso = peso;
		this.comprimento = comprimento;
		this.altura = altura;
		this.largura = largura;
		this.diametro = diametro;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getPeso() {
		return peso;
	}

	public String getComprimento() {
		return comprimento;
	}

	public String getAltura() {
		return altura;
	}

	public String getLargura() {
		return largura;
	}

	public String getDiametro() {
		return diametro;
	}



}
