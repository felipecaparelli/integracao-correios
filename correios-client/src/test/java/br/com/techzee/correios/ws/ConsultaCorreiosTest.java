package br.com.techzee.correios.ws;

import org.junit.Before;
import org.junit.Test;

import br.com.techzee.correios.ws.dto.CorreiosPrecoPrazo;
import br.com.techzee.correios.ws.enumeration.CorreiosFormatoEmbalagem;
import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;
import br.com.techzee.correios.ws.enumeration.IndicadorSN;
import junit.framework.TestCase;

public class ConsultaCorreiosTest extends TestCase {

	private String cepOrigem;
	private String cepDestino;
	private ConsultaCorreios consultaCorreios = new ConsultaCorreios();

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		this.cepOrigem = "06053040";
		this.cepDestino = "80540220";
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTipoServicoInvalido() {
		CorreiosTipoServico tipoServico = CorreiosTipoServico.getByCodigo(1);
		assertNull(tipoServico);
		try {
			consultaCorreios.servicos(CorreiosTipoServico.getByCodigo(null));
		} catch (Exception e) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFormatoInvalido() {
		CorreiosFormatoEmbalagem formato = null;
		assertNull(formato);
		try {
			consultaCorreios.formato(formato);
		} catch (Exception e) {
		}
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
													.formato(CorreiosFormatoEmbalagem.ENVELOPE)
													.calcularPrecoPrazo(cepOrigem, cepDestino);

		assertNotNull(response);
		assertEquals(1, response.length);
		assertEquals(IndicadorSN.SIM, response[0].getFlagEntregaDomiciliar());
	}

	@Test
	public void testPrecoPrazoSEDEX10() throws Exception {

		CorreiosPrecoPrazo[] response = new ConsultaCorreios()
													.servicos(CorreiosTipoServico.SEDEX_10_VAREJO)
													.calcularPrecoPrazo(cepOrigem, cepDestino);

		assertNotNull(response);
		assertEquals(1, response.length);
		assertEquals(IndicadorSN.SIM, response[0].getFlagEntregaDomiciliar());
	}

}
