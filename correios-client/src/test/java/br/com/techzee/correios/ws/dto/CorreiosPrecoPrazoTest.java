package br.com.techzee.correios.ws.dto;

import org.junit.Before;
import org.junit.Test;

import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;
import junit.framework.TestCase;

public class CorreiosPrecoPrazoTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCorreiosPrecoPrazo() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrecoFrete());
		assertNull(correiosPrecoPrazo.getPrazoEntrega());
	}

	@Test
	public void testCorreiosPrecoPrazoDoubleInteger() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo(12.10, 2);
		assertNotNull(correiosPrecoPrazo.getPrecoFrete());
		assertNotNull(correiosPrecoPrazo.getPrazoEntrega());
	}

	@Test
	public void testGetSetPrecoFrete() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrecoFrete());
		correiosPrecoPrazo.setPrecoFrete(10.25);
		assertNotNull(correiosPrecoPrazo.getPrecoFrete());
		assertEquals(10.25, correiosPrecoPrazo.getPrecoFrete());
	}

	@Test
	public void testGetPrazoEntrega() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrazoEntrega());
		correiosPrecoPrazo.setPrazoEntrega(12);
		assertNotNull(correiosPrecoPrazo.getPrazoEntrega());
		assertEquals(new Integer(12), correiosPrecoPrazo.getPrazoEntrega());
	}

	@Test
	public void testGetTipoServico() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getTipoServico());
		correiosPrecoPrazo.setTipoServico(CorreiosTipoServico.PAC_VAREJO);
		assertNotNull(correiosPrecoPrazo.getTipoServico());
		assertEquals(CorreiosTipoServico.PAC_VAREJO, correiosPrecoPrazo.getTipoServico());
	}

	@Test
	public void testGetPrecoEntregaEmMaos() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrecoEntregaEmMaos());
		correiosPrecoPrazo.setPrecoEntregaEmMaos(11.11);
		assertNotNull(correiosPrecoPrazo.getPrecoEntregaEmMaos());
		assertEquals(11.11, correiosPrecoPrazo.getPrecoEntregaEmMaos());
	}

	@Test
	public void testGetPrecoAvisoRecebimento() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrecoAvisoRecebimento());
		correiosPrecoPrazo.setPrecoAvisoRecebimento(18.13);
		assertNotNull(correiosPrecoPrazo.getPrecoAvisoRecebimento());
		assertEquals(18.13, correiosPrecoPrazo.getPrecoAvisoRecebimento());
	}

	@Test
	public void testGetPrecoValorDeclarado() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrecoValorDeclarado());
		correiosPrecoPrazo.setPrecoValorDeclarado(15.11);
		assertNotNull(correiosPrecoPrazo.getPrecoValorDeclarado());
		assertEquals(15.11, correiosPrecoPrazo.getPrecoValorDeclarado());
	}

	@Test
	public void testGetPrecoSemAdicionais() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getPrecoSemAdicionais());
		correiosPrecoPrazo.setPrecoSemAdicionais(24.50);
		assertNotNull(correiosPrecoPrazo.getPrecoSemAdicionais());
		assertEquals(24.50, correiosPrecoPrazo.getPrecoSemAdicionais());
	}

	@Test
	public void testGetFlagEntregaDomiciliar() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getFlagEntregaDomiciliar());
		correiosPrecoPrazo.setFlagEntregaDomiciliar(IndicadorSN.SIM);
		assertNotNull(correiosPrecoPrazo.getFlagEntregaDomiciliar());
		assertEquals(IndicadorSN.SIM, correiosPrecoPrazo.getFlagEntregaDomiciliar());
	}

	@Test
	public void testGetFlagEntregaSabado() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNull(correiosPrecoPrazo.getFlagEntregaSabado());
		correiosPrecoPrazo.setFlagEntregaSabado(IndicadorSN.SIM);
		assertNotNull(correiosPrecoPrazo.getFlagEntregaSabado());
		assertEquals(IndicadorSN.SIM, correiosPrecoPrazo.getFlagEntregaSabado());
	}


	@Test
	public void testPrecoPreenchidoPeloConstrutor() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo(12.10, 2);
		assertNotNull(correiosPrecoPrazo.getPrecoFrete());
		assertEquals(12.10, correiosPrecoPrazo.getPrecoFrete());
	}

	@Test
	public void testPrazoPreenchidoPeloConstrutor() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo(12.10, 2);
		assertNotNull(correiosPrecoPrazo.getPrazoEntrega());
		assertEquals(new Integer(2), correiosPrecoPrazo.getPrazoEntrega());
	}

	@Test
	public void testToStringNotNull() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo();
		assertNotNull(correiosPrecoPrazo.toString());
	}

}
