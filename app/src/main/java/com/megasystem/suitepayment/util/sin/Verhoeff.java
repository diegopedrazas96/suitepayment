package com.megasystem.suitepayment.util.sin;

import java.io.Serializable;

public class Verhoeff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6336928073930981840L;

	public static int[][] multiplicador = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 0, 6, 7, 8, 9, 5 }, { 2, 3, 4, 0, 1, 7, 8, 9, 5, 6 }, { 3, 4, 0, 1, 2, 8, 9, 5, 6, 7 }, { 4, 0, 1, 2, 3, 9, 5, 6, 7, 8 }, { 5, 9, 8, 7, 6, 0, 4, 3, 2, 1 }, { 6, 5, 9, 8, 7, 1, 0, 4, 3, 2 }, { 7, 6, 5, 9, 8, 2, 1, 0, 4, 3 }, { 8, 7, 6, 5, 9, 3, 2, 1, 0, 4 }, { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 } };
	public static int[][] permutaciones = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 1, 5, 7, 6, 2, 8, 3, 0, 9, 4 }, { 5, 8, 0, 3, 7, 9, 6, 1, 4, 2 }, { 8, 9, 1, 6, 0, 4, 3, 5, 2, 7 }, { 9, 4, 5, 3, 1, 2, 6, 8, 7, 0 }, { 4, 2, 8, 6, 5, 7, 3, 9, 0, 1 }, { 2, 7, 9, 3, 8, 0, 6, 4, 1, 5 }, { 7, 0, 4, 6, 9, 1, 3, 2, 5, 8 } };
	public static int[] inv = { 0, 4, 3, 2, 1, 5, 6, 7, 8, 9 };

	public static int getNumber(String numero) {
		String invertido = "";
		int check = 0;
		int seg;
		int number;
		invertido = invertirNumero(numero);
		for (int i = 0; i < invertido.length(); i++) {
			seg = Integer.parseInt(Character.toString(invertido.charAt(i)));
			number = permutaciones[((i + 1) % 8)][seg];
			check = multiplicador[check][number];
		}
		return inv[check];
	}

	private static String invertirNumero(String numero) {
		String invertido = "";
		long longitud = numero.length() - 1;
		while (longitud >= 0) {
			invertido = invertido + numero.charAt((int) longitud);
			longitud -= 1;
		}
		return invertido;
	}
}
