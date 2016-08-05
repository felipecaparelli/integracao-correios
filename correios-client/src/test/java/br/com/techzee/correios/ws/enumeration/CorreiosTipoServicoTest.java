package br.com.techzee.correios.ws.enumeration;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CorreiosTipoServicoTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGetByCodigo() {
		assertNull(CorreiosTipoServico.getByCodigo(0));
		assertEquals(CorreiosTipoServico.PAC_VAREJO, CorreiosTipoServico.getByCodigo(41106));
		assertEquals(CorreiosTipoServico.SEDEX_VAREJO, CorreiosTipoServico.getByCodigo(40010));
	}

}
