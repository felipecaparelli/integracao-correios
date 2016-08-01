package br.com.techzee.correios.ws;

import org.reficio.ws.client.core.SoapClient;

import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import br.com.techzee.correios.ws.enumeration.CorreiosFormatoEmbalagem;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;
import br.com.techzee.correios.ws.parser.CorreioResponseParser;
import br.com.techzee.correios.ws.util.ConsultaCorreiosUtils;

public class ConsultaCorreios {

	//parametros basicos para consulta apenas com CEP origem e destino
	private String codigoEmpresa = "";
	private String senha = "";
	private String codigoServico = String.valueOf(CorreiosTipoServico.SEDEX_VAREJO.getCodigo());

	private Integer codigoFormato = CorreiosFormatoEmbalagem.CAIXA_PACOTE.getCodigo();
	private String valorPeso = CorreiosFormatoEmbalagem.CAIXA_PACOTE.getPeso();
	private String valorComprimento = CorreiosFormatoEmbalagem.CAIXA_PACOTE.getComprimento();
	private String valorAltura = CorreiosFormatoEmbalagem.CAIXA_PACOTE.getAltura();
	private String valorLargura = CorreiosFormatoEmbalagem.CAIXA_PACOTE.getLargura();
	private String valorDiametro = CorreiosFormatoEmbalagem.CAIXA_PACOTE.getDiametro();

	private char flagEmMaos = IndicadorSN.NAO.getId();
	private char flagAvisoRecebimento = IndicadorSN.NAO.getId();
	private String valorDeclarado = "0";

	public ConsultaCorreios() {	}

	/**
	 * Parametriza o c&oacute;digo da empresa (informado pelo ECT) para ser
	 * utilizado nas consultas do servi&ccedil;o do Correios.
	 *
	 * @param codigoEmpresa - conforme documenta&ccedil;&atilde;o do Correios, s&atilde;o os 8 primeiros digitos do CNPJ do cliente
	 *
	 * @return {@link ConsultaCorreios}
	 */
	public ConsultaCorreios codigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
		return this;
	}

	/**
	 * Parametriza a senha do cadastro da empresa nas consultas do servi&ccedil;o do Correios.
	 *
	 * @param senha - senha provida pelo Correios ap&oacute;s a contrata&ccedil;&atilde;o dos servi&ccedil;os
	 *
	 * @return {@link ConsultaCorreios}
	 */
	public ConsultaCorreios senha(String senha) {
		this.senha = senha;
		return this;
	}

	/**
	 * M&eacute;todo utilizado para adicionar um ou mais servi&ccedil;os para a realiza&ccedil;&atilde;o da consulta do Correios.
	 * Os valores v&aacute;lidos est&atilde;o dispon&iacute;veis no enum {@link CorreiosTipoServico}
	 *
	 * @param codigosServicos
	 *
	 * @return {@link ConsultaCorreios}
	 */
	public ConsultaCorreios servicos(CorreiosTipoServico ... codigosServicos) {

		if(codigosServicos == null) throw new IllegalArgumentException("Favor informar ao menos um tipo de servi\u00e7o v\u00e1lido");
		StringBuilder servicos = new StringBuilder();

		for (CorreiosTipoServico cod : codigosServicos) {
			servicos.append(cod.getCodigo()).append(',');
		}

		String codigoServicosConcat = servicos.substring(0, servicos.length() -1);
		this.codigoServico = codigoServicosConcat;

		return this;
	}

	/**
	 * Formato da embalagem/pacote a ser consultado.
	 *
	 * @param formato
	 * @return
	 */
	public ConsultaCorreios formato(CorreiosFormatoEmbalagem formato) {
		if(formato == null) throw new IllegalArgumentException("Favor informar ao menos um formato v\u00e1lido");
		this.codigoFormato = formato.getCodigo();
		return this;
	}

	public ConsultaCorreios peso(Double peso) {
		this.valorPeso = ConsultaCorreiosUtils.doubleToString(peso);
		return this;
	}

	public ConsultaCorreios comprimento(Double comprimento) {
		this.valorComprimento = ConsultaCorreiosUtils.doubleToString(comprimento);
		return this;
	}

	public ConsultaCorreios altura(Double altura) {
		this.valorAltura = ConsultaCorreiosUtils.doubleToString(altura);
		return this;
	}

	public ConsultaCorreios largura(Double largura) {
		this.valorLargura = ConsultaCorreiosUtils.doubleToString(largura);
		return this;
	}

	public ConsultaCorreios diametro(Double diametro) {
		this.valorDiametro = ConsultaCorreiosUtils.doubleToString(diametro);
		return this;
	}

	public ConsultaCorreios entregarEmMaos(IndicadorSN ind) {
		this.flagEmMaos = ind.getId();
		return this;
	}

	public ConsultaCorreios avisoRecebimento(IndicadorSN ind) {
		this.flagAvisoRecebimento = ind.getId();
		return this;
	}

	public ConsultaCorreios valorAdicionalDeclarado(Double valorAdicional) {
		if(valorAdicional != null && valorAdicional != 0d) {
			this.valorDeclarado = ConsultaCorreiosUtils.doubleToString(valorAdicional);
		}
		return this;
	}

	/**
	 * Realiza a chamada ao servi&ccedil;o dos Correios com todos os dados basicos,
	 * informando apenas o CEP origem (geralmente o do lojista) e o CEP destino
	 * (geralmente o do cliente).
	 *
	 * Se n&atilde;o houver nenhuma parametriza&ccedil;&atilde;o na classe {@link ConsultaCorreios}, o tipo de servi&ccedil;o utilizado como padr&atilde;o &eacute; o SEDEX
	 *
	 * @param cepOrigem - CEP do lojista
	 * @param cepDestino - CEP do comprador/cliente
	 *
	 * @return {@link CorreiosPrecoPrazo}[]
	 * @throws Exception
	 */
	public CorreiosPrecoPrazo[] calcularPrecoPrazo(String cepOrigem, String cepDestino) throws Exception {

        SoapClient client = SoapClient.builder()
                .endpointUri("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx")
                .build();

        String request = String.format("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<tem:CalcPrecoPrazo>" +
				"<tem:nCdEmpresa>%s</tem:nCdEmpresa>" +
				"<tem:sDsSenha>%s</tem:sDsSenha>" +
				"<tem:nCdServico>%s</tem:nCdServico>" +
				"<tem:sCepOrigem>%s</tem:sCepOrigem>" +
				"<tem:sCepDestino>%s</tem:sCepDestino>" +
				"<tem:nVlPeso>%s</tem:nVlPeso>" +
				"<tem:nCdFormato>%d</tem:nCdFormato>" +
				"<tem:nVlComprimento>%s</tem:nVlComprimento>" +
				"<tem:nVlAltura>%s</tem:nVlAltura>" +
				"<tem:nVlLargura>%s</tem:nVlLargura>" +
				"<tem:nVlDiametro>%s</tem:nVlDiametro>" +
				"<tem:sCdMaoPropria>%s</tem:sCdMaoPropria>" +
				"<tem:nVlValorDeclarado>%s</tem:nVlValorDeclarado>" +
				"<tem:sCdAvisoRecebimento>%s</tem:sCdAvisoRecebimento>" +
				"</tem:CalcPrecoPrazo>" +
				"</soapenv:Body>" +
				"</soapenv:Envelope>",
				this.codigoEmpresa,
				this.senha,
				this.codigoServico,
				cepOrigem,
				cepDestino,
				this.valorPeso,
				this.codigoFormato,
				this.valorComprimento,
				this.valorAltura,
				this.valorLargura,
				this.valorDiametro,
				this.flagEmMaos,
				this.valorDeclarado,
				this.flagAvisoRecebimento);

        String response = client.post("http://tempuri.org/CalcPrecoPrazo", request);

        return CorreioResponseParser.parseCorreiosPrecoPrazo(response);
	}

}
