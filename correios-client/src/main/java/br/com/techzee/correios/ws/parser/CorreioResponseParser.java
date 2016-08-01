package br.com.techzee.correios.ws.parser;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;

/**
 * Classe respons&aacute;vel por trabalhar o retorno do servi&ccedil;o do Correios e construir
 * o objeto com os dados enriquecidos.
 *
 * @author felipe.caparelli
 *
 */
public class CorreioResponseParser {

	private static final String ERRO_SERVICO_INDISPONIVEL = "7";
	private static final String ERRO_VAZIO = "0";

	/**
	 * Transforma a String retornada em um documento XML.
	 *
	 * @param xml - string retornada pelo web service do Correios
	 *
	 * @return {@link Document}
	 *
	 * @throws Exception
	 */
	private static Document loadXMLFromString(String xml) throws Exception {

	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));

	    return builder.parse(is);
	}

	/**
	 * L&ecirc; o XML retornado pelo web service do Correios e constr&oacute;i o objeto com os dados preenchidos.
	 *
	 * @param xmlResponse - XML retornado pelo web service do Correios
	 *
	 * @return {@link CorreiosPrecoPrazo}
	 *
	 * @throws Exception
	 */
	public static CorreiosPrecoPrazo[] parseCorreiosPrecoPrazo(String xmlResponse) throws Exception {

		Document document = loadXMLFromString(xmlResponse);

		//lendo possiveis erros retornados do XML
		Node erro = document.getElementsByTagName("Erro").item(0);
		String codigoErro = erro.getTextContent();

		if(ERRO_SERVICO_INDISPONIVEL.equals(codigoErro)) {
			String mensagemErro = document.getElementsByTagName("MsgErro").item(0).getTextContent();
			throw new Exception(mensagemErro);
		}

		//para cada codigo de servico consultado retorna um elemento cServico
		NodeList servicosConsultados = document.getElementsByTagName("cServico");
		int itens = servicosConsultados.getLength();
		CorreiosPrecoPrazo[] retorno = new CorreiosPrecoPrazo[itens];

		for (int i = 0; i < itens; i++) {

			String codigoErro1 = document.getElementsByTagName("Erro").item(i).getTextContent();

			if(!ERRO_VAZIO.equals(codigoErro1)) {
				String mensagemErro = document.getElementsByTagName("MsgErro").item(i).getTextContent();
				throw new Exception(mensagemErro);
			}

			//lendo os nodes do xml retornado
			Node codigoServico = document.getElementsByTagName("Codigo").item(i);
			Node precoEntregaEmMaos = document.getElementsByTagName("ValorMaoPropria").item(i);
			Node precoAvisoRecebimento = document.getElementsByTagName("ValorAvisoRecebimento").item(i);
			Node precoValorDeclarado = document.getElementsByTagName("ValorValorDeclarado").item(i);
			Node precoFrete = document.getElementsByTagName("Valor").item(i);
			Node prazoEntrega = document.getElementsByTagName("PrazoEntrega").item(i);
			Node entregaDomiciliar = document.getElementsByTagName("EntregaDomiciliar").item(i);
			Node entregaSabado = document.getElementsByTagName("EntregaSabado").item(i);
			Node precoSemAdicionais = document.getElementsByTagName("ValorSemAdicionais").item(i);
			Node observacoes = document.getElementsByTagName("obsFim").item(i);

			//preenchendo os atributos do objeto
			CorreiosPrecoPrazo precoPrazo = new CorreiosPrecoPrazo();

			precoPrazo.setTipoServico(CorreiosTipoServico.getByCodigo(parseNodeToInt(codigoServico)));
			precoPrazo.setPrecoEntregaEmMaos(parseNodeToDouble(precoEntregaEmMaos));
			precoPrazo.setPrecoAvisoRecebimento(parseNodeToDouble(precoAvisoRecebimento));
			precoPrazo.setPrecoValorDeclarado(parseNodeToDouble(precoValorDeclarado));
			precoPrazo.setPrecoFrete(parseNodeToDouble(precoFrete));
			precoPrazo.setPrazoEntrega(parseNodeToInt(prazoEntrega));
			precoPrazo.setFlagEntregaDomiciliar(parseNodeToSN(entregaDomiciliar));
			precoPrazo.setFlagEntregaSabado(parseNodeToSN(entregaSabado));
			precoPrazo.setPrecoSemAdicionais(parseNodeToDouble(precoSemAdicionais));
			precoPrazo.setObservacoes(observacoes.getTextContent());

			retorno[i] = precoPrazo;
		}

		return retorno;
	}

	/* metodos auxiliares */

	private static int parseNodeToInt(Node node) {
		return Integer.parseInt(node.getTextContent());
	}

	private static Double parseNodeToDouble(Node node) {

		String nodeMoeda = node.getTextContent();

		if(nodeMoeda != null && !"".equals(nodeMoeda)) {
			nodeMoeda = nodeMoeda.replaceAll("\\.", "").replaceAll("\\,", "\\.");
			return Double.parseDouble(nodeMoeda);
		}

		return null;
	}

	private static IndicadorSN parseNodeToSN(Node node) {
		return IndicadorSN.getById(node.getTextContent());
	}

}
