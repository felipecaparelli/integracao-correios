package br.com.techzee.correios.ws;

import org.junit.Before;
import org.junit.Test;

import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoPacote;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;
import junit.framework.TestCase;

public class ConsultaCorreiosTest extends TestCase {

	private String cepOrigem;
	private String cepDestino;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		this.cepOrigem = "06053040";
		this.cepDestino = "80540220";
	}

	@Test
	public void testPrecoPrazo() throws Exception {

		CorreiosPrecoPrazo[] response = new ConsultaCorreios().calcularPrecoPrazo(cepOrigem, cepDestino);

		assertNotNull(response);
		assertEquals(1, response.length);
		assertEquals(IndicadorSN.SIM, response[0].getFlagEntregaDomiciliar());

		response = new ConsultaCorreios()
										.servicos(CorreiosTipoServico.PAC_VAREJO, CorreiosTipoServico.SEDEX_10_VAREJO)
										.entregarEmMaos(IndicadorSN.SIM)
										.calcularPrecoPrazo(cepOrigem, cepDestino);
		assertNotNull(response);
		assertEquals(2, response.length);
	}

	@Test
	public void testPrecoPrazoEnvelope() throws Exception {

		CorreiosPrecoPrazo[] response = new ConsultaCorreios()
													.formato(CorreiosTipoPacote.ENVELOPE)
													.calcularPrecoPrazo(cepOrigem, cepDestino);

		assertNotNull(response);
		assertEquals(1, response.length);
		assertEquals(IndicadorSN.SIM, response[0].getFlagEntregaDomiciliar());
	}

}
