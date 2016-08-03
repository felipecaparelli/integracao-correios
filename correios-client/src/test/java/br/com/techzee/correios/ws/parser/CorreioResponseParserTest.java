package br.com.techzee.correios.ws.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import junit.framework.TestCase;

public class CorreioResponseParserTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testDeveRetornarUmObjetoCorreiosPrecoPrazo() throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><CalcPrecoPrazoResponse xmlns=\"http://tempuri.org/\"><CalcPrecoPrazoResult><Servicos><cServico><Codigo>40010</Codigo><Valor>17,20</Valor><PrazoEntrega>1</PrazoEntrega><ValorMaoPropria>0,00</ValorMaoPropria><ValorAvisoRecebimento>0,00</ValorAvisoRecebimento><ValorValorDeclarado>0,00</ValorValorDeclarado><EntregaDomiciliar>S</EntregaDomiciliar><EntregaSabado>S</EntregaSabado><Erro>0</Erro><MsgErro /><ValorSemAdicionais>17,20</ValorSemAdicionais><obsFim /></cServico></Servicos></CalcPrecoPrazoResult></CalcPrecoPrazoResponse></soap:Body></soap:Envelope>";
		CorreiosPrecoPrazo[] response = new CorreioResponseParser().parseCorreiosPrecoPrazo(xml);
		assertNotNull(response);
		assertEquals(1, response.length);
	}

	public void testDeveRetornarDoisObjetosCorreiosPrecoPrazo() throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><CalcPrecoPrazoResponse xmlns=\"http://tempuri.org/\"><CalcPrecoPrazoResult><Servicos><cServico><Codigo>41106</Codigo><Valor>16,10</Valor><PrazoEntrega>5</PrazoEntrega><ValorMaoPropria>0,00</ValorMaoPropria><ValorAvisoRecebimento>0,00</ValorAvisoRecebimento><ValorValorDeclarado>0,00</ValorValorDeclarado><EntregaDomiciliar>S</EntregaDomiciliar><EntregaSabado>N</EntregaSabado><Erro>0</Erro><MsgErro /><ValorSemAdicionais>16,10</ValorSemAdicionais><obsFim /></cServico><cServico><Codigo>40010</Codigo><Valor>17,20</Valor><PrazoEntrega>1</PrazoEntrega><ValorMaoPropria>0,00</ValorMaoPropria><ValorAvisoRecebimento>0,00</ValorAvisoRecebimento><ValorValorDeclarado>0,00</ValorValorDeclarado><EntregaDomiciliar>S</EntregaDomiciliar><EntregaSabado>S</EntregaSabado><Erro>0</Erro><MsgErro /><ValorSemAdicionais>17,20</ValorSemAdicionais><obsFim /></cServico></Servicos></CalcPrecoPrazoResult></CalcPrecoPrazoResponse></soap:Body></soap:Envelope>";
		CorreiosPrecoPrazo[] response = new CorreioResponseParser().parseCorreiosPrecoPrazo(xml);
		assertNotNull(response);
		assertEquals(2, response.length);
	}

	@Test(expected = SAXParseException.class)
	public void testRetornarSAXParseException() throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException {
		try {
			new CorreioResponseParser().parseCorreiosPrecoPrazo("");
		} catch (IllegalArgumentException e) {
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetornarErroNoParser() throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException {
		try {
			new CorreioResponseParser().parseCorreiosPrecoPrazo(null);
		} catch (IllegalArgumentException e) {
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}

}
