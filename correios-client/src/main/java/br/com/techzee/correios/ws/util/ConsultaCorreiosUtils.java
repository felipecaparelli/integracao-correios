package br.com.techzee.correios.ws.util;

import java.text.DecimalFormat;
import java.util.Locale;

public class ConsultaCorreiosUtils {

	private static final DecimalFormat df;

	static {
		//parametrizando com locale Brasil para garantir formato 0,00 mesmo em servidores com locale diferente
		df = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		//df.applyPattern("#0.00"); //milhar sem separados
		df.applyPattern("#,##0.00");
	}

	public static String doubleToString(Double price) {
		return df.format(price);
	}
}
