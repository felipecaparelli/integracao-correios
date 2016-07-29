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

	CAIXA_PACOTE(1, "Formato caixa/pacote"),
	ROLO_PRISMA(2, "Formato rolo/prisma"),
	ENVELOPE(3, "Envelope");

	private int codigo;
	private String nome;

	private CorreiosTipoPacote(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

}
