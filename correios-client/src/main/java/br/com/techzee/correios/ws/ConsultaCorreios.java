package br.com.techzee.correios.ws;

import org.reficio.ws.client.core.SoapClient;

import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoPacote;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;
import br.com.techzee.correios.ws.parser.CorreioResponseParser;
import br.com.techzee.correios.ws.util.ConsultaCorreiosUtils;

public class ConsultaCorreios {

	//parametros basicos para consulta apenas com CEP origem e destino
	private String codigoEmpresa = "";
	private String senha = "";
	private String codigoServico = String.valueOf(CorreiosTipoServico.SEDEX_VAREJO.getCodigo());
	private Integer codigoFormato = CorreiosTipoPacote.CAIXA_PACOTE.getCodigo();
	private String valorPeso = "1";
	private String valorComprimento = "16";
	private String valorAltura = "2";
	private String valorLargura = "11";
	private String valorDiametro = "1";
	private char flagEmMaos = IndicadorSN.NAO.getId();
	private String valorDeclarado = "0";
	private char flagAvisoRecebimento = IndicadorSN.NAO.getId();

	public ConsultaCorreios() {}

	public ConsultaCorreios codigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
		return this;
	}

	public ConsultaCorreios senha(String senha) {
		this.senha = senha;
		return this;
	}

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

	public ConsultaCorreios formato(CorreiosTipoPacote formato) {
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



	public static void main(String[] args) {

		try {

			CorreiosPrecoPrazo[] results = new ConsultaCorreios().calcularPrecoPrazo("06053040", "80540220");

			for (CorreiosPrecoPrazo result : results) {
				System.out.println("Preço do Frete: " + result.getPrecoFrete());
				System.out.println(String.format("Prazo de Entrega: %d dias", result.getPrazoEntrega()));
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
