package br.com.techzee.correios.ws.dto;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CorreiosPrecoPrazoTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testPrecoPreenchido() {
		CorreiosPrecoPrazo correiosPrecoPrazo = new CorreiosPrecoPrazo(12.10, 2);
		assertNotNull(correiosPrecoPrazo.getPrecoFrete());
		assertEquals(12.10, correiosPrecoPrazo.getPrecoFrete());
	}

	@Test
	public void testPrazoPreenchido() {
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
