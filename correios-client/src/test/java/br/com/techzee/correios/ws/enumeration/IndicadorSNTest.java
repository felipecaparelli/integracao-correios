package br.com.techzee.correios.ws.enumeration;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class IndicadorSNTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGetById_SIM() {
		assertEquals(IndicadorSN.getById("S"), IndicadorSN.SIM);
		assertEquals(IndicadorSN.getById("s"), IndicadorSN.SIM);
		assertNull(IndicadorSN.getById("y"));
	}

	@Test
	public void testGetById_NAO() {
		assertEquals(IndicadorSN.getById("N"), IndicadorSN.NAO);
		assertEquals(IndicadorSN.getById("n"), IndicadorSN.NAO);
		assertNull(IndicadorSN.getById("w"));
	}

}
