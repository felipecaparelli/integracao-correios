package br.com.techzee.correios.ws.parser;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
	 * @throws IllegalArgumentException caso o atributo recebido seja nulo
	 * @throws ParserConfigurationException caso o parser nao seja configurado corretamente
	 * @throws IOException caso nao seja recebido conteudo na String
	 * @throws SAXException caso o parser receba um XML invalido
	 */
	private static Document loadXMLFromString(String xml) throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException {

		if(xml == null) throw new IllegalArgumentException("retorno do servi\u00e7o retornou null");

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
	 * @throws IllegalArgumentException caso o atributo recebido seja nulo
	 * @throws ParserConfigurationException caso o parser nao seja configurado corretamente
	 * @throws IOException caso nao seja recebido conteudo na String
	 * @throws SAXException caso o parser receba um XML invalido
	 */
	public CorreiosPrecoPrazo[] parseCorreiosPrecoPrazo(String xmlResponse) throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException {

		Document documentoXML = CorreioResponseParser.loadXMLFromString(xmlResponse);

		//lendo possiveis erros retornados do XML
		String primeiroErro = getPrimeiroNodePeloNome(documentoXML, "Erro").getFirstChild().getNodeValue();

		if(ERRO_SERVICO_INDISPONIVEL.equals(primeiroErro)) {
			String mensagemErro = getPrimeiroNodePeloNome(documentoXML, "MsgErro").getNodeValue();
			throw new RemoteException(mensagemErro);
		}

		//para cada codigo de servico consultado retorna um elemento cServico
		NodeList servicosConsultados = documentoXML.getElementsByTagName("cServico");
		int itens = servicosConsultados.getLength();
		CorreiosPrecoPrazo[] retorno = new CorreiosPrecoPrazo[itens];

		for (int indice = 0; indice < itens; indice++) {

			String erro = getNodeByName(documentoXML, "Erro", indice).getFirstChild().getNodeValue();

			if(!ERRO_VAZIO.equals(erro)) {
				String mensagemErro = getNodeByName(documentoXML, "MsgErro", indice).getNodeValue();
				throw new RemoteException(mensagemErro);
			}

			//lendo os nodes do xml retornado
			Node codigoServico = getNodeByName(documentoXML, "Codigo", indice).getFirstChild();
			Node precoEntregaEmMaos = getNodeByName(documentoXML, "ValorMaoPropria", indice).getFirstChild();
			Node precoAvisoRecebimento = getNodeByName(documentoXML, "ValorAvisoRecebimento", indice).getFirstChild();
			Node precoValorDeclarado = getNodeByName(documentoXML, "ValorValorDeclarado", indice).getFirstChild();
			Node precoFrete = getNodeByName(documentoXML, "Valor", indice).getFirstChild();
			Node prazoEntrega = getNodeByName(documentoXML, "PrazoEntrega", indice).getFirstChild();
			Node entregaDomiciliar = getNodeByName(documentoXML, "EntregaDomiciliar", indice).getFirstChild();
			Node entregaSabado = getNodeByName(documentoXML, "EntregaSabado", indice).getFirstChild();
			Node precoSemAdicionais = getNodeByName(documentoXML, "ValorSemAdicionais", indice).getFirstChild();
			Node observacoes = getNodeByName(documentoXML, "obsFim", indice).getFirstChild();

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
			if(observacoes != null) {
				precoPrazo.setObservacoes(observacoes.getNodeValue());
			}

			retorno[indice] = precoPrazo;
		}

		return retorno;
	}

	private Node getPrimeiroNodePeloNome(Document document, String nomeName) {
		return getNodeByName(document, nomeName, 0);
	}

	private Node getNodeByName(Document document, String nomeName, int index) {
		return document.getElementsByTagName(nomeName).item(index);
	}

	/* metodos auxiliares */

	private static int parseNodeToInt(Node node) {
		return Integer.parseInt(node.getNodeValue());
	}

	private static Double parseNodeToDouble(Node node) {

		String nodeMoeda = node.getNodeValue();

		if(nodeMoeda != null && !"".equals(nodeMoeda)) {
			nodeMoeda = nodeMoeda.replaceAll("\\.", "").replaceAll("\\,", "\\.");
			return Double.parseDouble(nodeMoeda);
		}

		return null;
	}

	private static IndicadorSN parseNodeToSN(Node node) {
		return IndicadorSN.getById(node.getNodeValue());
	}

}
