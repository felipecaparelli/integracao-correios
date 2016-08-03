package br.com.techzee.correios.ws.dto;

import java.io.Serializable;

import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;

/**
 * Objeto que cont\u00e9m os dados de pre\u00e7o e prazo de entrega obtidos
 * junto ao web service do Correios.
 *
 * @author felipe.caparelli
 */
public class CorreiosPrecoPrazo implements Serializable {

	private static final long serialVersionUID = -2021187185196440312L;

	private Double precoFrete;
	private Integer prazoEntrega;
	private CorreiosTipoServico tipoServico;
	private Double precoEntregaEmMaos;
	private Double precoAvisoRecebimento;
	private Double precoValorDeclarado;
	private Double precoSemAdicionais;
	private IndicadorSN flagEntregaDomiciliar;
	private IndicadorSN flagEntregaSabado;
	private String observacoes;

	public CorreiosPrecoPrazo() {
	}

	public CorreiosPrecoPrazo(Double precoFrete, Integer prazoEntrega) {
		this.precoFrete = precoFrete;
		this.prazoEntrega = prazoEntrega;
	}

	public Double getPrecoFrete() {
		return this.precoFrete;
	}

	public Integer getPrazoEntrega() {
		return this.prazoEntrega;
	}

	public CorreiosTipoServico getTipoServico() {
		return this.tipoServico;
	}

	public Double getPrecoEntregaEmMaos() {
		return this.precoEntregaEmMaos;
	}

	public Double getPrecoAvisoRecebimento() {
		return this.precoAvisoRecebimento;
	}

	public Double getPrecoValorDeclarado() {
		return this.precoValorDeclarado;
	}

	public Double getPrecoSemAdicionais() {
		return this.precoSemAdicionais;
	}

	public IndicadorSN getFlagEntregaDomiciliar() {
		return this.flagEntregaDomiciliar;
	}

	public IndicadorSN getFlagEntregaSabado() {
		return this.flagEntregaSabado;
	}

	public void setPrecoFrete(Double precoFrete) {
		this.precoFrete = precoFrete;
	}

	public void setPrazoEntrega(Integer prazoEntrega) {
		this.prazoEntrega = prazoEntrega;
	}

	public void setTipoServico(CorreiosTipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public void setPrecoEntregaEmMaos(Double precoEntregaEmMaos) {
		this.precoEntregaEmMaos = precoEntregaEmMaos;
	}

	public void setPrecoAvisoRecebimento(Double precoAvisoRecebimento) {
		this.precoAvisoRecebimento = precoAvisoRecebimento;
	}

	public void setPrecoValorDeclarado(Double precoValorDeclarado) {
		this.precoValorDeclarado = precoValorDeclarado;
	}

	public void setPrecoSemAdicionais(Double precoSemAdicionais) {
		this.precoSemAdicionais = precoSemAdicionais;
	}

	public void setFlagEntregaDomiciliar(IndicadorSN flagEntregaDomiciliar) {
		this.flagEntregaDomiciliar = flagEntregaDomiciliar;
	}

	public void setFlagEntregaSabado(IndicadorSN flagEntregaSabado) {
		this.flagEntregaSabado = flagEntregaSabado;
	}

	public String getObservacoes() {
		return this.observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String toString() {
		return "{ \"precoFrete\": " + precoFrete +
			   ", \"prazoEntrega\": " + prazoEntrega +
			   ", \"tipoServico\": \"" + ((tipoServico != null) ? tipoServico.getNome() : null)+ "\"" +
			   ", \"precoEntregaEmMaos\": " + precoEntregaEmMaos +
			   ", \"precoAvisoRecebimento\": " + precoAvisoRecebimento +
			   ", \"precoValorDeclarado\": " + precoValorDeclarado +
			   ", \"precoSemAdicionais\": " + precoSemAdicionais +
			   ", \"flagEntregaDomiciliar\": \"" + flagEntregaDomiciliar + "\"" +
			   ", \"flagEntregaSabado\": \"" + flagEntregaSabado + "\"" +
			   ", \"observacoes\": \"" + observacoes + "\"}";
	}



}
