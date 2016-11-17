package com.megasystem.suitepayment.util.sin;

import java.io.Serializable;
import java.util.Locale;

public class AllegedRc4 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -64150787036316624L;

	public static int[] state = new int[256];

	public static String cifrarMensajeRC4(String mensaje, String llave) {
		int x = 0;
		int y = 0;
		int index1 = 0;
		int index2 = 0;
		int nmen = 0;
		int i = 0;
		String mensajeCifrado = "";
		for (i = 0; i < 256; i++) {
			state[i] = i;
		}
		for (i = 0; i < 256; i++) {
			index2 = (llave.charAt(index1) + state[i] + index2) % 256;
			intercambiarValor(i, index2);
			index1 = (index1 + 1) % llave.length();
		}

		for (i = 0; i < mensaje.length(); i++) {
			x = (x + 1) % 256;
			y = (state[x] + y) % 256;
			intercambiarValor(x, y);
			nmen = mensaje.charAt(i) ^ state[(state[x] + state[y]) % 256];
			mensajeCifrado = mensajeCifrado + "-" + rellenarCero(Integer.toHexString(nmen).toUpperCase(Locale.getDefault()));
		}

		return mensajeCifrado.substring(1, mensajeCifrado.length());
	}

	private static void intercambiarValor(int pos1, int pos2) {
		int temp = state[pos1];
		state[pos1] = state[pos2];
		state[pos2] = temp;
	}

	private static String rellenarCero(String numero) {
		if (numero.length() <= 1) {
			numero = "0" + numero;
		}
		return numero;
	}

}
