package com.megasystem.suitepayment.util.sin;

import java.io.Serializable;

public class Base64 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6660987381849464819L;

	public static char[] diccionario = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/' };

	public static String getBase64(long numero) {
		long cociente = 1;
		int resto = 0;
		String palabra = "";
		while (cociente > 0) {
			cociente = (int) (numero / 64);
			resto = (int) (numero % 64);
			palabra = Character.toString(diccionario[resto]) + palabra;
			numero = cociente;
		}

		return palabra;
	}
}
