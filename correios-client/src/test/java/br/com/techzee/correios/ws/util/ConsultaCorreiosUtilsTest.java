package br.com.techzee.correios.ws.util;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ConsultaCorreiosUtilsTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDoubleToString() {
		assertEquals("17,20", ConsultaCorreiosUtils.doubleToString(17.2));
		assertEquals("3.230,25", ConsultaCorreiosUtils.doubleToString(3230.25));
	}

}
